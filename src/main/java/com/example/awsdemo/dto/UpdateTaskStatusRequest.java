package com.example.awsdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTaskStatusRequest {
    private long taskId;
    private String status;
}
