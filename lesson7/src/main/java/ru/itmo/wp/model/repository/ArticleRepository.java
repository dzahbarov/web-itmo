package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;

import java.util.List;

/**
 * @author dzahbarov
 */
public interface ArticleRepository {
    void save(Article article);
    List<Article> findAll();
    List<Article> findAllNoHidden();
    List<Article> findAllByUser(User user);

    void setStatus(long id, boolean status);
}
