package com.example.awsdemo.dto;

import com.example.awsdemo.model.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskListResponse {
    private List<Task> taskList;
}
