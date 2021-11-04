package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author dzahbarov
 */
abstract class BasicRepositoryImpl {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected void save(String table, String[] keys, String[] values) {

        StringBuilder stringStatement = new StringBuilder();
        stringStatement.append("INSERT INTO ").append(table);
        String stringOfKeys = String.join(", ", keys);
        stringStatement.append(" (").append(stringOfKeys).append(") VALUES");
        String stringOfValues = Arrays.stream(values).collect(Collectors.joining("','", "'", "'"));
        stringStatement.append(" (").append(stringOfValues).append(", NOW())");

        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stringStatement.toString(), Statement.RETURN_GENERATED_KEYS)) {
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + table + ".");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + table + ".", e);
        }
    }


    protected Long findCount(String table) {
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

}
