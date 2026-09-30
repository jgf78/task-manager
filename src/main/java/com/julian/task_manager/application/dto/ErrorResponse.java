package com.julian.task_manager.application.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        int status,
        List<String> errors,
        String path
) {
}