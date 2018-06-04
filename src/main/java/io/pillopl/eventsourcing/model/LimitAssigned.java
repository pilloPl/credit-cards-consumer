package io.pillopl.eventsourcing.model;

import java.math.BigDecimal;
import java.util.UUID;

public class LimitAssigned implements DomainEvent {

    private UUID cardNo;
    private BigDecimal amount;

    LimitAssigned() {
    }

    public LimitAssigned(UUID cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public UUID getCardNo() {
        return cardNo;
    }

    @Override
    public String getType() {
        return "limit-assigned";
    }
}
