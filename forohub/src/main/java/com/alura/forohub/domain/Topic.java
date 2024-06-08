package com.alura.forohub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name="Topic")
@Table(name = "topics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Topic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    private String status;

    private String author;

    private String course;
    private String responses;

    public Topic(TopicRegisterData data) {
        this.title = data.title();
        this.message = data.message();
        this.author = data.author();
        this.course = data.course();
        this.createdAt=LocalDateTime.now();
    }

    public void updateTopic(TopicUpdateData data) {
        if (data.status() != null)
            this.status = data.status();
        if (data.responses() != null) {
            this.responses = data.responses();
        }
    }
}
