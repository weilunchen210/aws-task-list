package com.example.awsdemo.controller;

import com.example.awsdemo.dto.*;
import com.example.awsdemo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping(value="/v1/list/{createdBy}")
    public TaskListResponse getTaskList(
            @PathVariable long createdBy
    ) {
        TaskListRequest request = new TaskListRequest();
        request.setCreatedBy(createdBy);
        return taskService.getTaskList(request);
    }

    @PostMapping(value="/v1/create")
    public void createTask(
            @RequestBody CreateTaskRequest createTaskRequest,
            @RequestParam long createdBy
    ) {
        createTaskRequest.setCreatedBy(createdBy);
        taskService.createTask(createTaskRequest);
    }

    @PutMapping(value="/v1/update/{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable long taskId,
            @RequestParam long createdBy,
            @RequestBody UpdateTaskRequest updateTaskRequest
    ) {
        updateTaskRequest.setTaskId(taskId);
        updateTaskRequest.setCreatedBy(createdBy);
        return toResponse(taskService.updateTask(updateTaskRequest));
    }

    @PatchMapping(value="/v1/update-status/{taskId}")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable long taskId,
            @RequestParam long createdBy,
            @RequestBody UpdateTaskStatusRequest updateTaskStatusRequest
    ) {
        updateTaskStatusRequest.setTaskId(taskId);
        updateTaskStatusRequest.setCreatedBy(createdBy);
        return toResponse(taskService.updateTaskStatus(updateTaskStatusRequest));
    }

    @DeleteMapping(value="/v1/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable long taskId,
            @RequestParam long createdBy
    ) {
        return toResponse(taskService.deleteTask(taskId, createdBy));
    }

    private ResponseEntity<Void> toResponse(boolean found) {
        return found ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
