package ru.itmo.wp.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.*;

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

    @Pattern(regexp = "[a-zA-Z\s]+")
    @NotBlank
    private String tags;
}
