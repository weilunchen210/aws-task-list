package com.example.awsdemo.controller;

import com.example.awsdemo.dto.TaskListResponse;
import com.example.awsdemo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(value="/task/list")
    public TaskListResponse getTaskList() {
        return taskService.getTaskList();
    }
}
