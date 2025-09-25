package co.com.bancolombia.sqs.listener;

import co.com.bancolombia.usecase.report.ReportUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSProcessor {
    private final ReportUseCase reportUseCase;

    @SqsListener("${entrypoint.sqs.queueReport}")
    public void onMessage (BigDecimal amount){
        reportUseCase.incrementApprovedApplicationCountAmount(amount)
                .doOnError(ex -> log.error("error processing event {}, message will be retried", amount , ex))
                .subscribe();
    }
}
