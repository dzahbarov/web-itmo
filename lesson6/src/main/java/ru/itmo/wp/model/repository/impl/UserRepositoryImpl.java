package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        table = "User";
    }

    @Override
    public void save(User user, String passwordSha) {
        save(Map.of("login", user.getLogin(),
                "passwordSha", passwordSha,
                "email", user.getEmail()));
    }

    @Override
    public User findByEmail(String email) {
        return findByConjunction(Map.of("email", email));
    }

    @Override
    public User find(long id) {
        return findByConjunction(Map.of("id", Long.toString(id)));
    }

    @Override
    public User findByLogin(String login) {
        return findByConjunction( Map.of("login", login));
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return findByConjunction(Map.of("login", login, "passwordSha", passwordSha));
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return findByConjunction( Map.of("email", email, "passwordSha", passwordSha));
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
        return super.findCount();
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    User getEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
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
}
