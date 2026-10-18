package com.example.awsdemo.controller;

import com.example.awsdemo.dto.*;
import com.example.awsdemo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.model.Update;

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

    @PostMapping(value="/v1/update/{taskId}")
    public void updateTask(
            @PathVariable long taskId,
            @RequestBody UpdateTaskRequest updateTaskRequest
    ) {
        updateTaskRequest.setTaskId(taskId);
        taskService.updateTask(updateTaskRequest);
    }

    @PutMapping(value="/v1/update-status/{taskId}")
    public void updateTaskStatus(
            @PathVariable long taskId,
            @RequestBody UpdateTaskStatusRequest updateTaskStatusRequest
    ) {
        updateTaskStatusRequest.setTaskId(taskId);
        taskService.updateTaskStatus(updateTaskStatusRequest);
    }

    @DeleteMapping(value="/v1/delete/{taskId}")
    public void deleteTask(
            @PathVariable long taskId
    ) {
        taskService.deleteTask(updateTaskStatusRequest);
    }
}
