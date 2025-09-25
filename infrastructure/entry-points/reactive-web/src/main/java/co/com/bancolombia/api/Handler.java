package co.com.bancolombia.api;

import co.com.bancolombia.usecase.report.ReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ReportUseCase reportUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return reportUseCase.getReport()
                .flatMap(report ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(report));
    }
}
