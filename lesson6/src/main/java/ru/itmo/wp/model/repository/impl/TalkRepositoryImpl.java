package ru.itmo.wp.model.repository.impl;


import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author dzahbarov
 */
public class TalkRepositoryImpl extends BasicRepositoryImpl<Talk> implements TalkRepository {

    public TalkRepositoryImpl() {
        table = "Talk";
    }

    @Override
    public void save(Talk talk) {
        save(Map.of("sourceUserId", String.valueOf(talk.getSourceUserId()),
                        "targetUserId", String.valueOf(talk.getTargetUserId()),
                        "text", talk.getText()));
    }

    @Override
    public List<Talk> findTalks(User user) {
        return findListByDisjunction(
                Map.of("sourceUserId", Long.toString(user.getId()),
                        "targetUserId", Long.toString(user.getId())));
    }

    @Override
    Talk getEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
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
}
