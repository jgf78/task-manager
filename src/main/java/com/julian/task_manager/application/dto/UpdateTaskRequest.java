package com.julian.task_manager.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title cannot exceed 100 characters") 
        String title,

        @Size(max = 255, message = "Description cannot exceed 255 characters") 
        String description
) {
}
