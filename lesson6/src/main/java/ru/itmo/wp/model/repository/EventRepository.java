package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;

/**
 * @author dzahbarov
 */
public interface EventRepository {
    void save(Event event);
}
