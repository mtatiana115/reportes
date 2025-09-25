package co.com.bancolombia.consumer;

import co.com.bancolombia.model.email.Email;
import co.com.bancolombia.model.email.gateways.EmailRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements EmailRepository {
    private final WebClient client;
    private static final String PATH_ADMIN_EMAILS = "/api/v1/admin-emails";

    @Value("${adapters.ses.fallbackEmail:fallback-admin@crediya.com}")
    private String fallbackEmail;

    @Override
    @CircuitBreaker(name = "authService", fallbackMethod = "fallbackAdminEmails")
    public Mono<Email> getAdminEmails() {
        return client.get()
                .uri(PATH_ADMIN_EMAILS)
                .retrieve()
                .bodyToMono(Map.class)
                .map(map -> {
                    List<String> adminEmails = (List<String>) map.get("adminEmails");
                    return new Email(List.of(adminEmails.toArray(new String[0])));
                })
                .doOnSuccess(e -> log.info("✅ Admin emails obtenidos desde autenticación: {}", (Object) e.getAdminEmails()))
                .doOnError(err -> log.error("❌ Error obteniendo admin emails", err));
    }

    private Mono<Email> fallbackAdminEmails(Throwable throwable) {
        log.warn("⚠️ Fallback activado: no se pudieron obtener correos de autenticación, error: {}",
                throwable.getMessage());

        return Mono.just(new Email(List.of(fallbackEmail)));
    }
}

