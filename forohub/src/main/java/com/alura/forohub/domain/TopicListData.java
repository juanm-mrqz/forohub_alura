package com.alura.forohub.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record TopicListData (

        String title,
        String message,
        LocalDateTime createdAt,
        String status,

        String author,

        String course

)
{
    //Constructor
    public TopicListData(Topic topic) {
        this(
                topic.getTitle(), topic.getMessage(),
                topic.getCreatedAt(), topic.getStatus(),
                topic.getAuthor(), topic.getCourse()
        );
    }
}

