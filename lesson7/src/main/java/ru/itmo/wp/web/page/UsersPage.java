package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        updateRights(request);
        boolean admin = false;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            admin = user.isAdmin();
        }
        view.put("admin", admin);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeStatus(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        updateRights(request);
        checkPermission(request);
        User user = (User) request.getSession().getAttribute("user");
        userService.validateChange(request, user);

        long id = Long.parseLong(request.getParameter("userId"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        userService.setStatus(id, status);
    }

    private void updateRights(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            user = userService.find(user.getId());
            request.getSession().setAttribute("user", user);
        }
    }

    private void checkPermission(HttpServletRequest request) {

        if (request.getSession().getAttribute("user") == null || !((User) request.getSession().getAttribute("user")).isAdmin()) {
            request.getSession().setAttribute("message", "Changing rights available only for admin");
            throw new RedirectException("/index");
        }
    }
}
