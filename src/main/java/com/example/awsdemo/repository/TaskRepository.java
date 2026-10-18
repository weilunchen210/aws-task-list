package com.example.awsdemo.repository;

import com.example.awsdemo.dto.TaskListRequest;
import com.example.awsdemo.dto.UpdateTaskStatusRequest;
import com.example.awsdemo.model.Task;
import com.example.awsdemo.model.TaskStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

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

        return table.index("createdBy-createdDate-index").query(request).stream().flatMap(page -> page.items().stream()).toList();
    }

    public void saveTask(Task task) {
        var table = dynamoDbEnhancedClient.table(taskTableName, TableSchema.fromBean(Task.class));
        table.putItem(task);
    }

    public boolean updateTask(Task task) {
        var table = dynamoDbEnhancedClient.table(taskTableName, TableSchema.fromBean(Task.class));

        Expression condition = Expression.builder()
                .expression("attribute_exists(taskId) AND createdBy = :createdBy")
                .putExpressionValue(":createdBy", AttributeValue.fromN(task.getCreatedBy().toString()))
                .build();

        UpdateItemEnhancedRequest<Task> request = UpdateItemEnhancedRequest.builder(Task.class)
                .item(task)
                .conditionExpression(condition)
                .ignoreNulls(true)
                .build();

        try {
            table.updateItem(request);
            return true;
        } catch (ConditionalCheckFailedException e) {
            return false;
        }
    }

    public boolean updateTaskStatus(UpdateTaskStatusRequest updateTaskStatusRequest) {
        var table = dynamoDbEnhancedClient.table(taskTableName, TableSchema.fromBean(Task.class));

        Task updatedTask = new Task();
        updatedTask.setTaskId(updateTaskStatusRequest.getTaskId());
        updatedTask.setTaskStatus(updateTaskStatusRequest.getStatus());

        Expression condition = Expression.builder()
                .expression("attribute_exists(taskId) AND createdBy = :createdBy")
                .putExpressionValue(":createdBy", AttributeValue.fromN(String.valueOf(updateTaskStatusRequest.getCreatedBy())))
                .build();

        UpdateItemEnhancedRequest<Task> request = UpdateItemEnhancedRequest.builder(Task.class)
                .item(updatedTask)
                .conditionExpression(condition)
                .ignoreNulls(true)
                .build();

        try {
            table.updateItem(request);
            return true;
        } catch (ConditionalCheckFailedException e) {
            return false;
        }
    }

    public boolean deleteTask(Long taskId, Long createdBy) {
        var table = dynamoDbEnhancedClient.table(taskTableName, TableSchema.fromBean(Task.class));

        Expression condition = Expression.builder()
                .expression("attribute_exists(taskId) AND createdBy = :createdBy")
                .putExpressionValue(":createdBy", AttributeValue.fromN(createdBy.toString()))
                .build();

        DeleteItemEnhancedRequest request = DeleteItemEnhancedRequest.builder()
                .key(k -> k.partitionValue(taskId))
                .conditionExpression(condition)
                .build();

        try {
            table.deleteItem(request);
            return true;
        } catch (ConditionalCheckFailedException e) {
            return false;
        }
    }
}
