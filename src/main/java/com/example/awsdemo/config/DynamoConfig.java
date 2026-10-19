package com.example.awsdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

@Configuration
public class DynamoConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.dynamodb.endpoint:}")
    private String dynamoDbEndpoint;

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbClientBuilder builder = DynamoDbClient.builder()
                .region(Region.of(region));

        // Only override for DynamoDB Local; when empty (on ECS) use real DynamoDB with the task role's credentials
        if (dynamoDbEndpoint != null && !dynamoDbEndpoint.isBlank()) {
            builder.endpointOverride(URI.create(dynamoDbEndpoint));
        }

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(builder.build())
                .build();
    }
}