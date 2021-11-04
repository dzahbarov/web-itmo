package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author dzahbarov
 */
public abstract class Page {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();

    private HttpSession session;

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        session = request.getSession();
        view.put("userCount", userService.findCount());
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }

        String message = (String) session.getAttribute("message");

        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            session.removeAttribute("message");
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    public void setMessage(String message) {
        session.setAttribute("message", message);
    }

    public void setUser(User user) {
        session.setAttribute("user", user);
    }

    public User getUser() {
        return (User) session.getAttribute("user");
    }

}
