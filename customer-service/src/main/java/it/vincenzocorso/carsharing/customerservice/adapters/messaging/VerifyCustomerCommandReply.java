package it.vincenzocorso.carsharing.customerservice.adapters.messaging;

import it.vincenzocorso.carsharing.common.messaging.commands.CommandReply;

public record VerifyCustomerCommandReply(boolean canRent) implements CommandReply {
    @Override
    public String getType() {
        return "VERIFY_CUSTOMER_COMMAND_REPLY";
    }
}
