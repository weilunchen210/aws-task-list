package com.example.awsdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String taskId;

    private String title;

    private String description;

    private String taskStatus;

    private String createdBy;

    private String createdDate;
}
