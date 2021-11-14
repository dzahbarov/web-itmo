package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dzahbarov
 */
public class ArticleRepositoryImpl implements ArticleRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public List<Article> findAllByUser(User user) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article WHERE userId=" + user.getId() + " ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
        return articles;
    }

    @Override
    public List<Article> findAllNoHidden() {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article WHERE hidden=false ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
        return articles;
    }

    @Override
    public void save(Article article) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `Article` (`title`, `text`, `userId`, `hidden`,`creationTime`) VALUES (?, ?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, article.getTitle());
                statement.setString(2, article.getText());
                statement.setLong(3, article.getUserId());
                statement.setBoolean(4, article.isHidden());

                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save Article.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Article.", e);
        }
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
        return articles;
    }

    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }
        return article;
    }

    @Override
    public void changeStatus(long id) {

    }
}