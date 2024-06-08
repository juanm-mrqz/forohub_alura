package com.alura.forohub.domain;

import jakarta.validation.constraints.NotNull;

public record TopicUpdateData(
        @NotNull Long id,
        String status,
        String responses

) {
}
