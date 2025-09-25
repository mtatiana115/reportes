package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.TemplateAdapterOperations;
import co.com.bancolombia.model.report.Report;
import co.com.bancolombia.model.report.gateways.ReportRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

import java.math.BigDecimal;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Report, String, ReportEntity> implements ReportRepository {

    private final String metricKey;

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory,
                                   ObjectMapper mapper,
                                   @Value("${adapters.aws.dynamodb.metricKey}")
                                   String metricKey,
                                   @Value("${adapters.aws.dynamodb.tableName}")
                                   String tableName) {
        super(connectionFactory, mapper, d -> mapper.map(d,Report.class), tableName);
        this.metricKey = metricKey;
    }

    @Override
    public Mono<Void> incrementApprovedApplicationCountAmount(BigDecimal amount) {
        return getReport().flatMap(report -> {
            report.setApprovedApplicationsCount(report.getApprovedApplicationsCount()+1);
            report.setApprovedApplicationAmount(report.getApprovedApplicationAmount().add(amount));
            return this.save(report);
        }).then();
    }

    @Override
    public Mono<Report> getReport() {
        return this.getById(metricKey);
    }
}
