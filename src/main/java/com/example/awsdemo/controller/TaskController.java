package com.example.awsdemo.controller;

import com.example.awsdemo.service.TaskService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class TaskController {

    private final TaskService taskService;

    @GetMapping(value="/task/list")
    public String getTaskList() {
        return "task/list";
    }
}
