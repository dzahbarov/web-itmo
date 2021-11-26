package ru.itmo.wp.domain;

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
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Size(min = 5, max = 5000)
    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @CreationTimestamp
    private Date creationTime;
}
