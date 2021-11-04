package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dzahbarov
 */
public class TalkRepositoryImpl extends BasicRepositoryImpl implements TalkRepository {

    @Override
    public void save(Talk talk) {
        String[] keys = {"sourceUserId", "targetUserId", "text", "creationTime"};
        String[] values = {String.valueOf(talk.getSourceUserId()),
                String.valueOf(talk.getTargetUserId()),
                talk.getText()};
        save("Talk", keys, values);
    }


    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }

    @Override
    public List<Talk> findTalks(User user) {
        long userId = user.getId();

        List<Talk> talks = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Talk WHERE (sourceUserId = ? OR targetUserId = ?) ORDER BY `creationTime` DESC")) {
                statement.setLong(1, userId);
                statement.setLong(2, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    Talk talk;
                    while ((talk = toTalk(statement.getMetaData(), resultSet)) != null) {
                        talks.add(talk);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk.", e);
        }
        return talks;

    }
}
