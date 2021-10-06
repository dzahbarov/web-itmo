package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author dzahbarov
 */

public class MessageServlet extends HttpServlet {

    private final ArrayList<Message> messages = new ArrayList<>();
    private final static String UTF_8 = StandardCharsets.UTF_8.name();

    static class Message {
        final String user;
        final String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(UTF_8);
        resp.setCharacterEncoding(UTF_8);

        if (req.getRequestURI().equals("/message/auth")) {

            HttpSession session = req.getSession();
            String user = req.getParameter("user");

            if (user == null) user = (String) session.getAttribute("user");
            if (user == null) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            session.setAttribute("user", user);
            resp.setContentType("application/json");

            resp.getWriter().print(new Gson().toJson(user));
            resp.getWriter().flush();

        } else if (req.getRequestURI().equals("/message/findAll")) {

            if (req.getSession().getAttribute("user") == null) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            resp.setContentType("application/json");
            resp.getWriter().print(new Gson().toJson(messages));
            resp.getWriter().flush();

        } else if (req.getRequestURI().equals("/message/add")) {
            HttpSession session = req.getSession();

            if (session.getAttribute("user") == null) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if (req.getParameter("text") == null || isBlank(req.getParameter("text"))) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Message message = new Message((String) session.getAttribute("user"),
                    req.getParameter("text"));
            messages.add(message);
        }
    }

    // since 11
    private boolean isBlank(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
}
