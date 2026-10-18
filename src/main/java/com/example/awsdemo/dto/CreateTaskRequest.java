package com.example.awsdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTaskRequest {
    private String title;
    private String description;
    private long createdBy;
}
