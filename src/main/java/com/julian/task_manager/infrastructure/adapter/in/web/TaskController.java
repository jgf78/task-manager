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

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase, GetTaskUseCase getTaskUseCase,
            GetAllTasksUseCase getAllTasksUseCase, DeleteTaskUseCase deleteTaskUseCase,
            UpdateTaskUseCase updateTaskUseCase, CompleteTaskUseCase completeTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.getTaskUseCase = getTaskUseCase;
        this.getAllTasksUseCase = getAllTasksUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody(required = true) CreateTaskRequest request) {

        return createTaskUseCase.create(request);
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable @Positive Long id) {

        return getTaskUseCase.getById(id);
    }

    @GetMapping
    public TaskPageResponse getAll(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page must be greater than or equal to 0")
            int page,

            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Size must be greater than 0")
            @Max(value = 100, message = "Size cannot exceed 100")
            int size,

            @RequestParam(defaultValue = "CREATED_AT")
            TaskSortField sortBy,

            @RequestParam(defaultValue = "DESC")
            SortDirection direction,

            @RequestParam(required = false)
            TaskStatus status) {

        TaskSort sort = new TaskSort(
                sortBy,
                direction
        );

        TaskFilter filter = new TaskFilter(status);

        TaskPageRequest pageRequest = new TaskPageRequest(
                page,
                size,
                sort,
                status, 
                filter
        );

        return getAllTasksUseCase.getAll(pageRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        deleteTaskUseCase.deleteById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable @Positive Long id, @Valid @RequestBody UpdateTaskRequest request) {

        return updateTaskUseCase.update(id, request);
    }

    @PatchMapping("/{id}/complete")
    public TaskResponse complete(@PathVariable @Positive Long id) {

        return completeTaskUseCase.complete(id);
    }
}
