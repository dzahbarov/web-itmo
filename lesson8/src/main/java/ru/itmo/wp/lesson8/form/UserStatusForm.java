package ru.itmo.wp.lesson8.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author dzahbarov
 */

@Getter
@Setter
public class UserStatusForm {

    @NotNull
    private Long userId;

    @NotNull
    private boolean userStatus;
}
