package io.pillopl.eventsourcing.model;

import java.math.BigDecimal;
import java.util.UUID;

public class CardWithdrawn implements DomainEvent {

    private UUID cardNo;
    private BigDecimal amount;

    public CardWithdrawn(UUID cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }

    CardWithdrawn() {

    }

    public UUID getCardNo() {
        return cardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    @Override
    public String getType() {
        return "card-withdrawn";
    }
}
