package ru.itmo.wp.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;


import static ru.itmo.wp.util.ImageUtils.toPng;

/**
 * @author dzahbarov
 */
public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (req.getMethod().equals("GET") &&
                session.getAttribute("user") == null &&
                session.getAttribute("status") != "accepted") {
            if (req.getRequestURI().equals("/favicon.ico")) {
                // Тут вообще бы иконку отдать
                res.setStatus(HttpServletResponse.SC_OK);
            } else if (req.getRequestURI().equals("/png/captcha.png")) {
                String magicNumber = String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000));
                session.setAttribute("magicNumber", magicNumber);
                res.getOutputStream().write(toPng(magicNumber));
                res.getOutputStream().flush();
                res.setContentType("img/png");
                res.setStatus(HttpServletResponse.SC_OK);
            } else if (req.getSession().getAttribute("magicNumber") != null &&
                    req.getSession().getAttribute("magicNumber").equals(req.getParameter("magicNumber"))) {
                session.setAttribute("status", "accepted");
                req.removeAttribute("magicNumber");
                chain.doFilter(req, res);
            } else {
                File file = new File(getServletContext().getRealPath("static/captcha.html"));
                Files.copy(file.toPath(), res.getOutputStream());
                res.getOutputStream().flush();
                res.setContentType("text/html");
                res.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}
