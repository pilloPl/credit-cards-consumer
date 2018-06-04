package io.pillopl.eventsourcing;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    @Input("cards")
    SubscribableChannel cards();

    @Output("risk")
    MessageChannel risk();

    @Output("payments")
    MessageChannel payments();

    @Output("notification")
    MessageChannel notification();


}
