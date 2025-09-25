package co.com.bancolombia.dynamodb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.aws.dynamodb")
public record DynamoDBConnectionProperties(
        String region,
        String accessKeyId,
        String secretAccessKey
) {
}
