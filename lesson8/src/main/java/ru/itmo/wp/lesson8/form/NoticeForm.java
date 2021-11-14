package ru.itmo.wp.lesson8.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author dzahbarov
 */

@Getter
@Setter
public class NoticeForm {
    @NotBlank
    @Size(min = 5, max = 50)
    private String content;
}
