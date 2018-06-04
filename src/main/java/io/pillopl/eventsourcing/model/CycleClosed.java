package io.pillopl.eventsourcing.model;

import java.util.UUID;

public class CycleClosed implements DomainEvent {

    private UUID cardNo;

    public CycleClosed(UUID cardNo) {
        this.cardNo = cardNo;
    }

    public UUID getCardNo() {
        return cardNo;
    }


    @Override
    public String getType() {
        return "cycle-closed";
    }
}
