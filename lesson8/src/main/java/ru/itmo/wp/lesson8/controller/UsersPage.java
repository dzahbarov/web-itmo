package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.lesson8.form.UserStatusForm;
import ru.itmo.wp.lesson8.form.validator.UserStatusFormValidator;
import ru.itmo.wp.lesson8.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("users/all")
public class UsersPage extends Page {
    private final UserService userService;
    private final UserStatusFormValidator userStatusFormValidator;

    public UsersPage(UserService userService, UserStatusFormValidator userStatusFormValidator) {
        this.userService = userService;
        this.userStatusFormValidator = userStatusFormValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (!(binder.getTarget() instanceof UserStatusForm)) {
            return;
        }
        binder.addValidators(userStatusFormValidator);

    }

    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping
    public String setUserStatus(@Valid @ModelAttribute("userStatusForm") UserStatusForm userStatusForm, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "redirect:/users/all";
        }
        userService.setStatus(userStatusForm);
        setMessage(httpSession, "Status changed!");
        return "redirect:/users/all";
    }
}
