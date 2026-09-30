package com.julian.task_manager.application.dto;

import java.time.Instant;

public record ErrorResponse(Instant now, int status, String error, String path) {

}
