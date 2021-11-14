package ru.itmo.wp.lesson8.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author dzahbarov
 */

@Getter
@Setter
@Entity
@Table
public class Notice {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @NotBlank private String content;

    @CreationTimestamp
    private Date creationTime;
}
