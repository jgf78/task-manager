package com.julian.task_manager.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.julian.task_manager.application.service.CompleteTaskService;
import com.julian.task_manager.application.service.CreateTaskService;
import com.julian.task_manager.application.service.DeleteTaskService;
import com.julian.task_manager.application.service.GetAllTasksService;
import com.julian.task_manager.application.service.GetTaskService;
import com.julian.task_manager.application.service.UpdateTaskService;
import com.julian.task_manager.domain.port.in.CompleteTaskUseCase;
import com.julian.task_manager.domain.port.in.CreateTaskUseCase;
import com.julian.task_manager.domain.port.in.DeleteTaskUseCase;
import com.julian.task_manager.domain.port.in.GetAllTasksUseCase;
import com.julian.task_manager.domain.port.in.GetTaskUseCase;
import com.julian.task_manager.domain.port.in.UpdateTaskUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;
import com.julian.task_manager.infrastructure.adapter.out.persistence.TaskJpaRepository;
import com.julian.task_manager.infrastructure.adapter.out.persistence.TaskPersistenceAdapter;

@Configuration
public class BeanConfiguration {

    @Bean
    TaskRepository taskRepository(TaskJpaRepository taskJpaRepository) {
        return new TaskPersistenceAdapter(taskJpaRepository);
    }

    @Bean
    CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository) {
        return new CreateTaskService(taskRepository);
    }
    
    @Bean
    GetTaskUseCase getTaskUseCase(TaskRepository taskRepository) {
        return new GetTaskService(taskRepository);
    }
    
    @Bean
    GetAllTasksUseCase getAllTasksUseCase(TaskRepository taskRepository) {
        return new GetAllTasksService(taskRepository);
    }
    
    @Bean
    DeleteTaskUseCase deleteTaskUseCase(TaskRepository taskRepository) {
        return new DeleteTaskService(taskRepository);
    }
    
    @Bean
    UpdateTaskUseCase updateTaskUseCase(TaskRepository taskRepository) {
        return new UpdateTaskService(taskRepository);
    }
    
    @Bean
    CompleteTaskUseCase completeTaskUseCase(TaskRepository taskRepository) {
        return new CompleteTaskService(taskRepository);
    }
}