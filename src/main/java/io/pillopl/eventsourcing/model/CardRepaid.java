package io.pillopl.eventsourcing.model;

import java.math.BigDecimal;
import java.util.UUID;

public class CardRepaid implements DomainEvent {

    private final UUID cardNo;
    private final BigDecimal amount;

    public CardRepaid(UUID cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }

    public UUID getCardNo() {
        return cardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getType() {
        return "card-repaid";
    }
}
