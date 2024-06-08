package com.alura.forohub.domain;

import jakarta.validation.constraints.NotBlank;

public record TopicRegisterData(
    @NotBlank(message="required title")
    String title,
    @NotBlank(message="required message")
    String message,
    @NotBlank(message="required author")
    String author,
    @NotBlank(message="required course")
    String course

) {
}
