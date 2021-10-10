package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Color;
import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataUtil {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", Color.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", Color.BLUE)
    );

    private static final List<Post> POSTS = new ArrayList<>();

    static {
        try {
            POSTS.add(new Post(1, "Kotlin Heroes 8 Announcement", Files.readString(Path.of("/home/vladimir/ITMO/2-course/3-term/Web/hw4/3446b7aab407c5ca/lesson4/src/main/webapp/posts/1")), 1));
            POSTS.add(new Post(2, "Codeforces Round #746 (Div. 2)", Files.readString(Path.of("/home/vladimir/ITMO/2-course/3-term/Web/hw4/3446b7aab407c5ca/lesson4/src/main/webapp/posts/2")), 1));
            POSTS.add(new Post(3, "COMPFEST 13 Finals Mirror (Unrated, ICPC Rules, Teams Preferred)", Files.readString(Path.of("/home/vladimir/ITMO/2-course/3-term/Web/hw4/3446b7aab407c5ca/lesson4/src/main/webapp/posts/3")), 6));
        } catch (Exception ignored) {}
    }


    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("current_menu", request.getRequestURI());
        data.put("posts", POSTS);
        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }


}
