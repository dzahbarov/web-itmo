package ru.itmo.wp.model.service;

import com.google.common.base.Strings;

import org.checkerframework.checker.units.qual.A;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dzahbarov
 */
public class ArticleService {
    ArticleRepository articleRepository = new ArticleRepositoryImpl();


    public void validate(Article article) throws ValidationException {
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title must be not empty");
        }
        if (article.getTitle().length() > 255) {
            throw new ValidationException("Title must less or equal 255 symbols");
        }

        if (article.getTitle().length() < 5) {
            throw new ValidationException("Title must longer than 4 symbols");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text must be not empty");
        }
        if (article.getText().length() <= 5000) {
            throw new ValidationException("Text must less or equal 5000 symbols");
        }
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllNoHidden() {
        return articleRepository.findAllNoHidden();
    }

    public List<Article> findAllByUser(User user) {
        return articleRepository.findAllByUser(user);
    }

    public void setStatus(long id, boolean status) {
        articleRepository.setStatus(id, status);
    }

    public boolean isAuthor(User user, Long articleId) {
        List<Article> articles = findAllByUser(user);
        for (Article article : articles) {
            if (article.getId() == articleId) {
                return true;
            }
        }
        return false;
    }

    public void validateChange(HttpServletRequest request, User user) throws ValidationException {
        String status = request.getParameter("status");

        if (Strings.isNullOrEmpty(status) || (!status.equals("true") && !status.equals("false"))) {
            throw new ValidationException("Invalid status");
        }

        String artId = request.getParameter("artId");
        if (Strings.isNullOrEmpty(artId) || !artId.matches("[0-9]+")) {
            throw new ValidationException("Invalid artId");
        }

        Long artIdNumber = Long.parseLong(artId);

        if (!isAuthor(user, artIdNumber)) {
            throw new ValidationException("Only author can change access to the article");
        }
    }
}
