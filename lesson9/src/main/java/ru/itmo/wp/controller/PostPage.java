package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author dzahbarov
 */

@Controller
@RequestMapping("post")
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping(value = {"{id}", "/"})
    public String showPost(@PathVariable(required = false) String id, Model model) {

        long validId = parseId(id);
        model.addAttribute("post", postService.find(validId));
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @PostMapping("{id}")
    public String addComment(@PathVariable String id, Model model, @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        Post post = postService.find(parseId(id));

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }

        if (post == null) {
            return "PostPage";
        }

        postService.addComment(post.getId(), comment, getUser(httpSession));
        putMessage(httpSession, "You published new comment");

        return "redirect:/post/" + id;
    }

    private long parseId(@PathVariable(required = false) String id) {
        try {
            return Long.parseLong(id);
        } catch (Exception ignored) {
            return 0L;
        }
    }
}
