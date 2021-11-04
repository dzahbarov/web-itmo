package ru.itmo.wp.model.service;


import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Objects;

/**
 * @author dzahbarov
 */
public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateTalk(Talk talk) throws ValidationException {
        if (Strings.isNullOrEmpty(talk.getText())) {
            throw new ValidationException("Text must be not empty");
        }

        if (userRepository.find(talk.getSourceUserId()) ==null) {
            throw new ValidationException("Id of source user doesn't exist");
        }
        if (userRepository.find(talk.getTargetUserId()) ==null) {
            throw new ValidationException("Id of target user doesn't exist");
        }
        if (talk.getSourceUserId().equals(talk.getTargetUserId())) {
            throw new ValidationException("Target user can't be source user");
        }
    }

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> getUserTalks(User user) {
        return talkRepository.findTalks(user);
    }

}
