package com.example.awsdemo.repository;

import com.example.awsdemo.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

@Repository
public class TaskRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Value("${aws.dynamodb.tableName.task}")
    private String taskTableName;

    public TaskRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    public List<Task> getAllTasks() {
        var table = dynamoDbEnhancedClient.table(taskTableName, TableSchema.fromBean(Task.class));
        return table.scan().items().stream().toList();
    }
}
