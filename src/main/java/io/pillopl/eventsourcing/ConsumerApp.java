package io.pillopl.eventsourcing;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pillopl.eventsourcing.model.DomainEvent;
import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@SpringBootApplication
@EnableBinding(Channels.class)
public class ConsumerApp {

    private final Channels channels;

    private final Logger log = LoggerFactory.getLogger(ConsumerApp.class.getSimpleName());

    public ConsumerApp(Channels channels) {
        this.channels = channels;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @StreamListener(target = "cards", condition = "headers['type'] == 'card-withdrawn'")
    public void handle(DomainEvent event) {
        log.info(event.toString());
        channels.risk().send(new GenericMessage<>("DO RISK"));
        channels.payments().send(new GenericMessage<>("DO PAYMENTS"));
        channels.notification().send(new GenericMessage<>("DO EMAIL"));
    }


}
@RestController
class WithdrawalsController {

    private final Channels channels;
    private final ObjectMapper objectMapper;

    WithdrawalsController(Channels channels, ObjectMapper objectMapper) {
        this.channels = channels;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/withdrawals", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    Flux<?> withdrawals() {
        return Flux.create(sink -> {
            MessageHandler messageHandler = msg ->  sink.next(convert(msg));
            channels.cards().subscribe(messageHandler);
            sink.onCancel(() -> channels.cards().unsubscribe(messageHandler));
        });
    }

    private DomainEvent convert(Message<?> msg) {
        try {
            return objectMapper.readValue((byte[]) msg.getPayload(), DomainEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}