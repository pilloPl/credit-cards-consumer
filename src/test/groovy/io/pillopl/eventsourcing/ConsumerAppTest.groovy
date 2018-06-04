package io.pillopl.eventsourcing

import io.pillopl.eventsourcing.model.CardWithdrawn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.test.binder.MessageCollector
import org.springframework.messaging.support.GenericMessage
import spock.lang.Specification

import java.util.concurrent.BlockingQueue

@SpringBootTest
class ConsumerAppTest extends Specification {

    @Autowired MessageCollector messageCollector
    @Autowired Channels channels

    BlockingQueue<GenericMessage> risk
    BlockingQueue<GenericMessage> payments
    BlockingQueue<GenericMessage> notification

    def setup() {
        risk = messageCollector.forChannel(channels.risk())
        payments = messageCollector.forChannel(channels.payments())
        notification = messageCollector.forChannel(channels.notification())

    }

    def 'should emit commands to risk, payments, notification when card is withdrawn'() {
        when:
            channels.cards().send(new GenericMessage<Object>(new CardWithdrawn(UUID.randomUUID(), 10.00), ["type" : "card-withdrawn"]))
        then:
            (risk.poll().payload as String) == "DO RISK"
            (payments.poll().payload as String) == "DO PAYMENTS"
            (notification.poll().payload as String) == "DO EMAIL"

    }
}
