package com.example.awsdemo.service;

import com.example.awsdemo.dto.*;
import com.example.awsdemo.model.Task;
import com.example.awsdemo.model.TaskStatus;
import com.example.awsdemo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    // Largest integer JavaScript can hold exactly (2^53 - 1), so ids survive a JSON round trip to a browser
    private static final long JS_SAFE_ID_MASK = (1L << 53) - 1;

    private final TaskRepository taskRepository;

    public TaskListResponse getTaskList(TaskListRequest taskListRequest) {
        TaskListResponse response = new TaskListResponse();
        List<Task> taskList = taskRepository.getAllTasks(taskListRequest);
        response.setTaskList(taskList);
        return response;
    }

    public boolean createTask(CreateTaskRequest createTaskRequest) {
        Task task = new Task();
        // DynamoDB has no auto-increment, so the id is a random 53-bit number
        task.setTaskId(UUID.randomUUID().getMostSignificantBits() & JS_SAFE_ID_MASK);
        task.setTitle(createTaskRequest.getTitle());
        task.setDescription(createTaskRequest.getDescription());
        task.setTaskStatus(TaskStatus.TODO);
        task.setCreatedBy(createTaskRequest.getCreatedBy());
        // ISO-8601 sorts correctly as text; it is also the GSI sort key, so without it the task never shows in the list
        task.setCreatedDate(Instant.now().toString());

        taskRepository.saveTask(task);
        return true;
    }

    public boolean updateTask(UpdateTaskRequest updateTaskRequest) {
        Task task = new Task();
        task.setTaskId(updateTaskRequest.getTaskId());
        task.setTitle(updateTaskRequest.getTitle());
        task.setDescription(updateTaskRequest.getDescription());
        task.setCreatedBy(updateTaskRequest.getCreatedBy());

        return taskRepository.updateTask(task);
    }

    public boolean updateTaskStatus(UpdateTaskStatusRequest updateTaskStatusRequest) {
        return taskRepository.updateTaskStatus(updateTaskStatusRequest);
    }

    public boolean deleteTask(long taskId, long createdBy) {
        return taskRepository.deleteTask(taskId, createdBy);
    }
}
