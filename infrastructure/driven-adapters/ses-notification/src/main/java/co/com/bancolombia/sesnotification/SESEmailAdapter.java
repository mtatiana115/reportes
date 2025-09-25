package co.com.bancolombia.sesnotification;

import co.com.bancolombia.model.email.gateways.SesGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SESEmailAdapter implements SesGateway {

    private final SesAsyncClient sesAsyncClient;

    @Value("${adapters.ses.email}")
    private String from;

    @Override
    public Mono<Void> sendEmail(List<String> to, String subject, String body) {
        Destination destination = Destination.builder()
                .toAddresses(to)
                .build();

        Content subjectContent = Content.builder()
                .data(subject)
                .charset("UTF-8")
                .build();

        Body bodyEmail = Body.builder()
                .text(Content.builder().data(body).charset("UTF-8").build())
//                .html(Content.builder().data(body).charset("UTF-8").build())
                .build();

        Message message = Message.builder()
                .subject(subjectContent)
                .body(bodyEmail)
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .source(from)
                .destination(destination)
                .message(message)
                .build();

        return Mono.fromFuture(sesAsyncClient.sendEmail(request))
                .doOnSubscribe(sub -> log.debug("Sending daily email through SES to {}", to))
                .doOnSuccess(resp -> log.info("SES email sent: messageId={}", resp.messageId()))
                .doOnError(ex -> log.error("Sending daily email through SES failed", ex))
                .then();
    }
}
