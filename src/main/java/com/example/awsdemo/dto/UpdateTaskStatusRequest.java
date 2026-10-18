package com.example.awsdemo.dto;

import com.example.awsdemo.model.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTaskStatusRequest {
    private long taskId;
    private TaskStatus status;
    private long createdBy;
}
