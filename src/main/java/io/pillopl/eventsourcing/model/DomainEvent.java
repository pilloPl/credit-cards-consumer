package io.pillopl.eventsourcing.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "card-repaid", value = CardRepaid.class),
        @JsonSubTypes.Type(name = "card-withdrawn", value = CardWithdrawn.class),
        @JsonSubTypes.Type(name = "limit-assigned", value = LimitAssigned.class),
        @JsonSubTypes.Type(name = "cycle-closed", value = CycleClosed.class)
})
public interface DomainEvent {

    String getType();
}
