package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wp.lesson8.domain.Notice;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author dzahbarov
 */

@Controller
@RequestMapping("notice")
public class NoticePage extends Page {
    NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public String getNoticePage(Model model) {
        model.addAttribute("noticeForm", new Notice());
        return "NoticePage";
    }

    @PostMapping
    public String addNotice(@Valid @ModelAttribute("noticeForm") Notice noticeForm, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(noticeForm);
        setMessage(httpSession, "Notice added!");
        return "redirect:";
    }
}
