package ru.itmo.wp.lesson8.controller;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wp.lesson8.service.UserService;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

/**
 * @author dzahbarov
 */

@Controller
@RequestMapping("user")
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"{id}", "/", })
    public String getUserPage(Model model, @PathVariable(required = false) String id) {
        long validId = 0L;
        if (id != null && id.matches("[0-9]+")) {
            validId = Long.parseLong(id);
        }
        model.addAttribute("user", userService.findById(validId));
        return "UserPage";
    }
}
