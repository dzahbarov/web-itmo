package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends BasicRepositoryImpl implements UserRepository {

    @Override
    public void save(User user, String passwordSha) {
        String[] keys = {"login", "passwordSha", "email", "creationTime"};
        String[] values = {user.getLogin(), passwordSha, user.getEmail()};
        save("User", keys, values);
    }

    @Override
    public User findByEmail(String email) {
        return findBy("email", email);
    }

    @Override
    public User find(long id) {
        return findBy("id", Long.toString(id));
    }

    @Override
    public User findByLogin(String login) {
        return findBy("login", login);
    }

    @Override
    public User findByLoginAndPasswordSha(String email, String passwordSha) {
        return findBy("login", "passwordSha", email, passwordSha);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return findBy("email", "passwordSha", email, passwordSha);
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        User user = findByLoginAndPasswordSha(loginOrEmail, passwordSha);
        if (user == null) {
            user = findByEmailAndPasswordSha(loginOrEmail, passwordSha);
        }
        return user;
    }

    @Override
    public Long findCount() {
        return findCount("User");
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    User user;
                    while ((user = toUser(statement.getMetaData(), resultSet)) != null) {
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return users;
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    private User findBy(String fieldName, String fieldValue) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + "User" + " WHERE " + fieldName + "=?")) {
                statement.setString(1, fieldValue);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toUser(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + "User" + ".", e);
        }
    }

    private User findBy(String fieldName1, String fieldName2, String fieldValue1, String fieldValue2) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + "User" + " WHERE " + fieldName1 + "=? AND " + fieldName2 + "=?")) {
                statement.setString(1, fieldValue1);
                statement.setString(2, fieldValue2);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return toUser(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + "User" + ".", e);
        }
    }
}
