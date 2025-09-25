package co.com.bancolombia.model.email.gateways;

import co.com.bancolombia.model.email.Email;
import reactor.core.publisher.Mono;

public interface EmailRepository {
    Mono<Email> getAdminEmails();
}
