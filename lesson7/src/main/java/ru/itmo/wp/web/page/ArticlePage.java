package ru.itmo.wp.web.page;

import org.checkerframework.checker.units.qual.A;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author dzahbarov
 */
public class ArticlePage {
    ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        checkPermission(request);
    }

    private void publish(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        checkPermission(request);
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        User user = (User) session.getAttribute("user");
        Article article = new Article();
        article.setTitle(title);
        article.setText(text);
        article.setUserId(user.getId());

        articleService.validate(article);
        articleService.save(article);
        request.getSession().setAttribute("message", "Article were successfully published");
        throw new RedirectException("/index");
    }

    private void checkPermission(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Publish articles available only for auth users");
            throw new RedirectException("/index");
        }
    }

}
