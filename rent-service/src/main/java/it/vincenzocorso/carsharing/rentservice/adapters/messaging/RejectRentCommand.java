package it.vincenzocorso.carsharing.rentservice.adapters.messaging;

import it.vincenzocorso.carsharing.common.messaging.commands.Command;

public record RejectRentCommand(String rentId) implements Command {
    @Override
    public String getType() {
        return "REJECT_RENT_COMMAND";
    }
}
