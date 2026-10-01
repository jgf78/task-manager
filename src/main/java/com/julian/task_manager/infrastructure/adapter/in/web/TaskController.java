package com.julian.task_manager.infrastructure.adapter.in.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julian.task_manager.application.dto.CreateTaskRequest;
import com.julian.task_manager.application.dto.ErrorResponse;
import com.julian.task_manager.application.dto.TaskPageResponse;
import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.application.dto.UpdateTaskRequest;
import com.julian.task_manager.domain.model.SortDirection;
import com.julian.task_manager.domain.model.TaskFilter;
import com.julian.task_manager.domain.model.TaskPageRequest;
import com.julian.task_manager.domain.model.TaskSort;
import com.julian.task_manager.domain.model.TaskSortField;
import com.julian.task_manager.domain.model.TaskStatus;
import com.julian.task_manager.domain.port.in.CompleteTaskUseCase;
import com.julian.task_manager.domain.port.in.CreateTaskUseCase;
import com.julian.task_manager.domain.port.in.DeleteTaskUseCase;
import com.julian.task_manager.domain.port.in.GetAllTasksUseCase;
import com.julian.task_manager.domain.port.in.GetTaskUseCase;
import com.julian.task_manager.domain.port.in.UpdateTaskUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/tasks")
@Tag(
        name = "Tasks",
        description = "Operations for managing tasks"
)
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            GetTaskUseCase getTaskUseCase,
            GetAllTasksUseCase getAllTasksUseCase,
            DeleteTaskUseCase deleteTaskUseCase,
            UpdateTaskUseCase updateTaskUseCase,
            CompleteTaskUseCase completeTaskUseCase) {

        this.createTaskUseCase = createTaskUseCase;
        this.getTaskUseCase = getTaskUseCase;
        this.getAllTasksUseCase = getAllTasksUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
    }

    @Operation(
            summary = "Create a task",
            description = "Creates a new task with PENDING status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task created successfully",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @PostMapping
    public TaskResponse create(
            @Valid @RequestBody CreateTaskRequest request) {

        return createTaskUseCase.create(request);
    }

    @Operation(
            summary = "Get a task by ID",
            description = "Returns a task using its unique identifier"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task found",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid task ID",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @GetMapping("/{id}")
    public TaskResponse getById(
            @Parameter(
                    description = "Task ID",
                    example = "1"
            )
            @PathVariable @Positive Long id) {

        return getTaskUseCase.getById(id);
    }

    @Operation(
            summary = "Get tasks",
            description = "Returns a paginated list of tasks with optional sorting and filtering"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tasks retrieved successfully",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskPageResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid pagination, sorting or filtering parameters",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @GetMapping
    public TaskPageResponse getAll(

            @Parameter(
                    description = "Page number (zero-based)",
                    example = "0"
            )
            @RequestParam(defaultValue = "0")
            @Min(
                    value = 0,
                    message = "Page must be greater than or equal to 0"
            )
            int page,

            @Parameter(
                    description = "Number of tasks per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10")
            @Min(
                    value = 1,
                    message = "Size must be greater than 0"
            )
            @Max(
                    value = 100,
                    message = "Size cannot exceed 100"
            )
            int size,

            @Parameter(
                    description = "Field used to sort the tasks",
                    example = "CREATED_AT"
            )
            @RequestParam(defaultValue = "CREATED_AT")
            TaskSortField sortBy,

            @Parameter(
                    description = "Sorting direction",
                    example = "DESC"
            )
            @RequestParam(defaultValue = "DESC")
            SortDirection direction,

            @Parameter(
                    description = "Filter tasks by status",
                    example = "PENDING"
            )
            @RequestParam(required = false)
            TaskStatus status,

            @Parameter(
                    description = "Filter tasks whose title contains this text",
                    example = "Docker"
            )
            @RequestParam(required = false)
            String title) {

        TaskSort sort = new TaskSort(
                sortBy,
                direction
        );

        TaskFilter filter = new TaskFilter(
                status,
                title
        );

        TaskPageRequest pageRequest = new TaskPageRequest(
                page,
                size,
                sort,
                filter
        );

        return getAllTasksUseCase.getAll(pageRequest);
    }

    @Operation(
            summary = "Delete a task",
            description = "Deletes a task using its unique identifier"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task deleted successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid task ID",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(
                    description = "Task ID",
                    example = "1"
            )
            @PathVariable @Positive Long id) {

        deleteTaskUseCase.deleteById(id);
    }

    @Operation(
            summary = "Update a task",
            description = "Updates the title and description of an existing task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task updated successfully",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @PutMapping("/{id}")
    public TaskResponse update(

            @Parameter(
                    description = "Task ID",
                    example = "1"
            )
            @PathVariable @Positive Long id,

            @Valid @RequestBody UpdateTaskRequest request) {

        return updateTaskUseCase.update(id, request);
    }

    @Operation(
            summary = "Complete a task",
            description = "Marks a pending task as completed"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task completed successfully",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid task ID",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Task is already completed",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @PatchMapping("/{id}/complete")
    public TaskResponse complete(
            @Parameter(
                    description = "Task ID",
                    example = "1"
            )
            @PathVariable @Positive Long id) {

        return completeTaskUseCase.complete(id);
    }
}