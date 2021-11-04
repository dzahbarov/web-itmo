package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;

import java.util.List;

/**
 * @author dzahbarov
 */
public interface TalkRepository {
    void save(Talk talk);
    List<Talk> findTalks(User user);
}
