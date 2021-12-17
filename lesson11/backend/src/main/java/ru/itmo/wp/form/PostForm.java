package ru.itmo.wp.form;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.wp.domain.User;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author dzahbarov
 */
@Getter
@Setter
public class PostForm {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @NotNull
    private User user;

}