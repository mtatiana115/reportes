package co.com.bancolombia.usecase.report;

import co.com.bancolombia.model.report.Report;
import co.com.bancolombia.model.report.gateways.ReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ReportUseCase {
    private final ReportRepository reportRepository;

    public Mono<Void> incrementApprovedApplicationCountAmount (BigDecimal amount){
        return reportRepository.incrementApprovedApplicationCountAmount(amount);
    }
    public Mono<Report> getReport (){
        return reportRepository.getReport();
    }
}
