package com.scoreboard.app.comment.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "COMMENTS")
public class Comment {

    public static final int MAXIMUM_TEXT_LENGTH = 500;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "TEXT", length = MAXIMUM_TEXT_LENGTH)
    private String text;

    @Column(name = "MODIFIED_DATE")
    private ZonedDateTime modifiedDate;

    @Column(name = "GAME_ID")
    private Long gameId;

}
