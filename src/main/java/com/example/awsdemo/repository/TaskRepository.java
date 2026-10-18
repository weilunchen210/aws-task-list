package com.example.awsdemo.repository;

import com.example.awsdemo.dto.TaskListRequest;
import com.example.awsdemo.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Repository
public class TaskRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Value("${aws.dynamodb.tableName.task}")
    private String taskTableName;

    public TaskRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    public List<Task> getAllTasks(TaskListRequest taskListRequest) {
        var table = dynamoDbEnhancedClient.table(taskTableName, TableSchema.fromBean(Task.class));

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(kv -> kv.partitionValue(taskListRequest.getCreatedBy())))
                .scanIndexForward(false)
                .build();

        return table.index("createdBy-index").query(request).stream().flatMap(page -> page.items().stream()).toList();
    }
}
