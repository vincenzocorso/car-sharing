package it.vincenzocorso.carsharing.rentservice.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.common.messaging.commands.Command;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandHeaders;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicleUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CommandsHandler {
    private final ObjectMapper objectMapper;
    private final RentVehicleUseCase rentVehicleUseCase;

    @KafkaListener(topics = "rent-service-commands")
    @Transactional
    public void dispatchMessage(
            @Payload String payload,
            @Header(CommandHeaders.TYPE) String type,
            @Header(CommandHeaders.MESSAGE_ID) String messageId,
            @Header(CommandHeaders.RESPONSE_CHANNEL) String responseChannel) throws Exception {
        log.info("Received message of type: " + type);

        switch(type) {
            case "REJECT_RENT_COMMAND" -> {
                RejectRentCommand command = this.objectMapper.readValue(payload, RejectRentCommand.class);
                this.processRejectRentCommand(command);
            }
            case "ACCEPT_RENT_COMMAND" -> {
                AcceptRentCommand command = this.objectMapper.readValue(payload, AcceptRentCommand.class);
                this.processAcceptRentCommand(command);
            }
            default -> throw new InternalServerException("Unknown command type: " + type);
        }
    }

    private void processRejectRentCommand(RejectRentCommand command) {
        log.info("Processing command: " + command);

        this.rentVehicleUseCase.rejectRent(command.rentId());
    }

    private void processAcceptRentCommand(AcceptRentCommand command) {
        log.info("Processing command: " + command);

        this.rentVehicleUseCase.acceptRent(command.rentId());
    }
}

record RejectRentCommand(String rentId) implements Command {
    @Override
    public String getType() {
        return "REJECT_RENT_COMMAND";
    }
}

record AcceptRentCommand(String rentId) implements Command {
    @Override
    public String getType() {
        return "ACCEPT_RENT_COMMAND";
    }
}
