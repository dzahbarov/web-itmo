package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author dzahbarov
 */
public class MyArticlesPage {

    ArticleService articleService = new ArticleService();


    private void action(HttpServletRequest request, Map<String, Object> view) {
        checkPermission(request);
        User user = (User) request.getSession().getAttribute("user");
        List<Article> all = articleService.findAllByUser(user);
        view.put("articles", all);
        putMessage(request, view);
    }


    private void changeStatus(HttpServletRequest request, Map<String, Object> view) {
        //validate();
//        Long id  = Long.valueOf(request.getParameter("id"));
//
//        String password = request.getParameter("");
//
//        User user = userService.validateAndFindByLoginAndPassword(login, password);
//        request.getSession().setAttribute("user", user);
//        request.getSession().setAttribute("message", "Hello, " + user.getLogin());
//
//        throw new RedirectException("/index");
        Long id  = Long.valueOf(request.getParameter("artId"));
        articleService.changeStatus(id);

    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        checkPermission(request);
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    private void checkPermission(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Publish articles available only for auth users");
            throw new RedirectException("/index");
        }
    }
}
