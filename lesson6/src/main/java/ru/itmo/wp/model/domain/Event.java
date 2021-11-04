package ru.itmo.wp.model.domain;

import java.util.Date;

/**
 * @author dzahbarov
 */
public class Event {
    private Long id;
    private Long userId;
    private Event.Type type;
    private Date creationTime;

    public Event(Long userId, Event.Type type) {
        this.userId = userId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Event.Type getType() {
        return type;
    }

    public void setType(Event.Type type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public enum Type {
        ENTER, LOGOUT
    }
}
