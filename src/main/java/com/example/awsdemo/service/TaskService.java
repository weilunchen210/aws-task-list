package com.example.awsdemo.service;

import com.example.awsdemo.dto.TaskListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    public TaskListResponse getTaskList() {
        TaskListResponse response = new TaskListResponse();
        // Here you would typically fetch the task list from a repository or database
        // For now, we will just return an empty list
        response.setTaskList(null); // Set to null or an empty list as a placeholder
        return response;
    }

}
