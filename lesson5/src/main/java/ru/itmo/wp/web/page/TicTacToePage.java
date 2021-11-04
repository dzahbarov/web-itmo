package ru.itmo.wp.web.page;

import ru.itmo.wp.model.tictactoe.State;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private State state = new State();

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

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String param : parameterMap.keySet()) {
            if (param.matches("cell_[0-2]{2}")) {
                int row = param.charAt(5) - '0';
                int col = param.charAt(6) - '0';

                if (state.isValid(row, col)) {
                    state.makeMove(row, col);
                }
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
        view.put("state", state);
    }

    public State getState() {
        return state;
    }
}
