package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * @author dzahbarov
 */
abstract class BasicRepositoryImpl<T extends Entity> {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    protected String table;
    
    abstract T getEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;

    protected void save(Map<String, String> args) {
        StringBuilder stringStatement = new StringBuilder();
        stringStatement.append("INSERT INTO ").append(table);
        String stringOfKeys = String.join(", ", args.keySet());
        stringOfKeys = stringOfKeys + ", creationTime";
        stringStatement.append(" (").append(stringOfKeys).append(") VALUES");

        String[] tmp = new String[args.size()];
        Arrays.fill(tmp, "?");
        String stringOfValues = String.join(", ", tmp);
        stringStatement.append(" (").append(stringOfValues).append(", NOW())");

        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stringStatement.toString(), Statement.RETURN_GENERATED_KEYS)) {
                fillPreparedStatement(args, statement);

                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + table + ".");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + table + ".", e);
        }
    }

    protected Long findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM "  + table)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new RepositoryException("Can't count number of " + table.toLowerCase(Locale.ROOT) + "s.");

            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't count number of " + table.toLowerCase(Locale.ROOT) + "s.", e);
        }
    }

    protected List<T> findAll() {
        List<T> objects = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    T obj;
                    while ((obj = getEntity(statement.getMetaData(), resultSet)) != null) {
                        objects.add(obj);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find entity.", e);
        }
        return objects;
    }

    protected T findByConjunction(Map<String, String> args) {
        StringBuilder stringStatement = getPreparedStatement(args, "AND");
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stringStatement.toString())) {
                fillPreparedStatement(args, statement);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return getEntity(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + table + ".", e);
        }
    }

    protected List<T> findListByDisjunction(Map<String, String> args) {
        StringBuilder stringStatement = getPreparedStatement(args, "OR");
        List<T> res = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stringStatement.toString())) {
                fillPreparedStatement(args, statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    T entity;
                    while ((entity = getEntity(statement.getMetaData(), resultSet)) != null) {
                        res.add(entity);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + table + ".", e);
        }
        return res;
    }

    private StringBuilder getPreparedStatement(Map<String, String> args, String delimiter) {
        StringBuilder stringStatement = new StringBuilder();
        stringStatement.append("SELECT * FROM ").append(table).append(" WHERE (");
        ArrayList<String> tmp = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : args.entrySet()) {
            tmp.add(stringStringEntry.getKey() + "=?");
        }
        stringStatement.append(String.join(" " + delimiter + " ", tmp));
        stringStatement.append(") ");
        return stringStatement;
    }

    private void fillPreparedStatement(Map<String, String> args, PreparedStatement statement) throws SQLException {
        int index = 1;
        for (String value : args.values()) {
            statement.setString(index++, value);
        }
    }

}
