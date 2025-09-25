package co.com.bancolombia.model.report.gateways;

import co.com.bancolombia.model.report.Report;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ReportRepository {
    Mono<Void> incrementApprovedApplicationCountAmount (BigDecimal amount);
    Mono<Report> getReport ();
}
