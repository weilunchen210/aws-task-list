package com.example.awsdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTaskRequest {
    private long taskId;
    private String title;
    private String description;
}
