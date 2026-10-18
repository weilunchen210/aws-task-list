package com.example.awsdemo.service;

import com.example.awsdemo.dto.TaskListResponse;
import com.example.awsdemo.model.Task;
import com.example.awsdemo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskListResponse getTaskList() {
        TaskListResponse response = new TaskListResponse();
        // Here you would typically fetch the task list from a repository or database
        // For now, we will just return an empty list
        List<Task> taskList = taskRepository.getAllTasks(); // This line is just to show where you would call the repository
        response.setTaskList(taskList); // Set to null or an empty list as a placeholder
        return response;
    }

}
