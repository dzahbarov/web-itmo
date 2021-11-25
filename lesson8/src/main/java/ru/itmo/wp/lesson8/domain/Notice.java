package ru.itmo.wp.lesson8.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 5, max = 65000)
    private String content;

    @CreationTimestamp
    private Date creationTime;
}
