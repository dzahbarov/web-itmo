package ru.itmo.wp.model.repository.impl;


import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author dzahbarov
 */
public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {

    public EventRepositoryImpl() {
        table = "Event";
    }

    @Override
    public void save(Event event) {
        save(Map.of("userId", String.valueOf(event.getUserId()),
                        "type", event.getType().name()));
    }

    @Override
    Event getEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(Event.Type.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }
}
