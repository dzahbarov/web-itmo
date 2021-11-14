package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.form.UserCredentials;
import ru.itmo.wp.lesson8.form.UserStatusForm;
import ru.itmo.wp.lesson8.service.UserService;

@Component
public class UserStatusFormValidator implements Validator {
    private final UserService userService;

    public UserStatusFormValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return UserStatusForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserStatusForm userStatusForm = (UserStatusForm) target;
            if (userService.findById(userStatusForm.getUserId()) == null) {
                errors.rejectValue("userId", "userId.doesntExist", "User ID doesn't exist");
            }
        }
    }
}
