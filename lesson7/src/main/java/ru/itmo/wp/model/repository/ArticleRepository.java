package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

/**
 * @author dzahbarov
 */
public interface ArticleRepository {
    void save(Article article);
    List<Article> findAll();
}
