package com.example.awsdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task{

    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private Long taskId;

    private String title;

    private String description;

    private TaskStatus taskStatus;

    @Getter(onMethod_ = @DynamoDbSecondaryPartitionKey(indexNames = "createdBy-createdDate-index"))
    private Long createdBy;

    @Getter(onMethod_ = @DynamoDbSecondarySortKey(indexNames = "createdBy-createdDate-index"))
    private String createdDate;

}
