package com.julian.task_manager.infrastructure.adapter.in.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.julian.task_manager.application.dto.CreateTaskRequest;
import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.application.dto.UpdateTaskRequest;
import com.julian.task_manager.domain.port.in.CompleteTaskUseCase;
import com.julian.task_manager.domain.port.in.CreateTaskUseCase;
import com.julian.task_manager.domain.port.in.DeleteTaskUseCase;
import com.julian.task_manager.domain.port.in.GetAllTasksUseCase;
import com.julian.task_manager.domain.port.in.GetTaskUseCase;
import com.julian.task_manager.domain.port.in.UpdateTaskUseCase;

import jakarta.validation.Valid;
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
    public List<TaskResponse> getAllRecords() {

        return getAllTasksUseCase.getAll();
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
