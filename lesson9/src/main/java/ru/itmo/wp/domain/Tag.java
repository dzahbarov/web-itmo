package ru.itmo.wp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author dzahbarov
 */

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;
}
