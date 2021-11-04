package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

/**
 * @author dzahbarov
 */
public class EventRepositoryImpl extends BasicRepositoryImpl implements EventRepository {

    @Override
    public void save(Event event) {
        String[] keys = {"userId", "type", "creationTime"};
        String[] values = {String.valueOf(event.getUserId()), event.getType().name()};
        save("Event", keys, values);
    }
}
