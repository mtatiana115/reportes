package co.com.bancolombia.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;


@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEntity {

    private String metricKey;
    private Long approvedApplicationsCount;
    private BigDecimal approvedApplicationAmount;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("metricKey")
    public String getMetricKey() {
        return metricKey;
    }

    @DynamoDbAttribute("approvedApplicationsCount")
    public Long getApprovedApplicationsCount() {
        return approvedApplicationsCount;
    }

    @DynamoDbAttribute("approvedApplicationAmount")
    public BigDecimal getApprovedApplicationAmount(){return approvedApplicationAmount;}
}
