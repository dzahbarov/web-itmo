package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author dzahbarov
 */

public class MessageServlet extends HttpServlet {

    private final ArrayList<Message> messages = new ArrayList<>();

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
        if (req.getRequestURI().equals("/message/auth")) {
            HttpSession session = req.getSession();
            String user = req.getParameter("user");

            if (user == null) {
                user = (String) session.getAttribute("user");
            }
            if (user == null) user = "";

            session.setAttribute("user", user);
            resp.setContentType("application/json");
            resp.getWriter().print(new Gson().toJson(user));
            resp.getWriter().flush();

        } else if (req.getRequestURI().equals("/message/findAll")) {
            resp.setContentType("application/json");
            resp.getWriter().print( new Gson().toJson(messages));
            resp.getWriter().flush();

        } else if (req.getRequestURI().equals("/message/add")) {
            HttpSession session = req.getSession();
            String user = (String) session.getAttribute("user");
            Message message = new Message(user, req.getParameter("text"));
            messages.add(message);
        }
    }

}
