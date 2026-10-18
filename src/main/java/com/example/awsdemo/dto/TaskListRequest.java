package com.example.awsdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskListRequest {
    private long createdBy;
}
