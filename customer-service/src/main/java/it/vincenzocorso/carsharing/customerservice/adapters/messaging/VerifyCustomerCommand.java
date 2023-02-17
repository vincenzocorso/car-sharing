package it.vincenzocorso.carsharing.customerservice.adapters.messaging;

import it.vincenzocorso.carsharing.common.messaging.commands.Command;

public record VerifyCustomerCommand(String customerId) implements Command {
    @Override
    public String getType() {
        return "VERIFY_CUSTOMER_COMMAND";
    }
}
