package ru.itmo.wp.web.page;

import ru.itmo.wp.model.TicTacToe.State;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    State state = new State();

    private void newGame(Map<String, Object> view, HttpServletRequest request) {
        state = new State();
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void onMove(Map<String, Object> view, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("state") != null) {
            state = (State) session.getAttribute("state");
        }
        session.setAttribute("state", state);

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String param : parameterMap.keySet()) {
            if (param.startsWith("cell_")) {
                int row = param.charAt(5)-'0';
                int col = param.charAt(6)-'0';
                state.makeMove(row, col);
            }
        }
        session.setAttribute("state", state);
        view.put("state", state);
    }

    private void action(Map<String, Object> view, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("state") != null) {
            state = (State) session.getAttribute("state");
        }
        if (request.getMethod().equals("GET")) {
            view.put("state", state);
        }
    }

    public State getState() {
        return state;
    }
}
