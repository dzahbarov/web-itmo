package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author dzahbarov
 */
public class TalksPage extends Page {
    TalkService talkService = new TalkService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        checkPermission();
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
        checkPermission();
        view.put("userService", userService);
        view.put("talks", talkService.getUserTalks(getUser()));
    }

    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        checkPermission();
        if (Strings.isNullOrEmpty(request.getParameter("sendTo"))) {
            throw new ValidationException("Invalid Person");
        }
        User targetUser  = userService.findByLogin(request.getParameter("sendTo"));
        if (targetUser == null) {
            throw new ValidationException("Invalid Person");
        }
        String text = request.getParameter("text");
        Talk talk = new Talk(getUser().getId(), targetUser.getId(), text);
        talkService.validateTalk(talk);
        talkService.save(talk);
    }

    private void checkPermission() {
        if (getUser() == null) {
            setMessage("Page Talks available only for auth users");
            throw new RedirectException("/index");
        }
    }
}
