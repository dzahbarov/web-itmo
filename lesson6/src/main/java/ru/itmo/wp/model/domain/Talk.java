package ru.itmo.wp.model.domain;

import java.util.Date;

/**
 * @author dzahbarov
 */
public class Talk implements Entity {
    Long id;
    Long sourceUserId;
    Long targetUserId;
    String text;
    Date creationTime;

    public Talk() {}

    public Talk(Long sourceUserId, Long targetUserId, String text) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


}
