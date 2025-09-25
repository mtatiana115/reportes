package co.com.bancolombia.usecase.dailyreport;

import co.com.bancolombia.model.email.gateways.EmailRepository;
import co.com.bancolombia.model.email.gateways.SesGateway;
import co.com.bancolombia.usecase.report.ReportUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DailyReportUseCase {

    private final EmailRepository emailRepository;
    private final SesGateway sesGateway;
    private final ReportUseCase reportUseCase;

    public Mono<Void> sendDailyReport(){
        return emailRepository.getAdminEmails()
                .flatMap(email -> reportUseCase.getReport()
                        .flatMap(report -> sesGateway.sendEmail(
                                email.getAdminEmails(),
                                "CrediYa - Daily Report",
                                """ 
                                 Hello,
                                 Daily report:
                                    Loans approved: %d
                                    Total amount: %s
                                 Regards,
                                 CrediYa Platform""".formatted(report.getApprovedApplicationsCount(), report.getApprovedApplicationAmount())
                        )));
    }
}
