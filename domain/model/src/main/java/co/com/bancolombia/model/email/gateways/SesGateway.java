package co.com.bancolombia.model.email.gateways;

import reactor.core.publisher.Mono;

import java.util.List;

public interface SesGateway {
    Mono<Void> sendEmail (List<String> to, String subject, String body);
}
