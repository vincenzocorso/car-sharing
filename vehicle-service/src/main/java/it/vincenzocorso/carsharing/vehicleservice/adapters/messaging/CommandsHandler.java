package it.vincenzocorso.carsharing.vehicleservice.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.common.messaging.commands.Command;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandHeaders;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandReply;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.RentVehicleUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Slf4j
@AllArgsConstructor
public class CommandsHandler {
    ObjectMapper objectMapper;
    RentVehicleUseCase rentVehicleUseCase;
    CommandProducer commandProducer;

    @Incoming("vehicle-service-commands")
    public CompletionStage<Void> dispatchMessage(Message<String> message) throws Exception {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String type = new String(metadata.getHeaders().lastHeader(CommandHeaders.TYPE).value());
        log.info("Received message of type: " + type);

        switch (type) {
            case "BOOK_VEHICLE_COMMAND" -> {
                BookVehicleCommand command = this.objectMapper.readValue(message.getPayload(), BookVehicleCommand.class);
                this.processBookVehicleCommand(message.withPayload(command));
            }
            default -> throw new InternalServerException("Unknown command type: " + type);
        }

        return message.ack();
    }

    private void processBookVehicleCommand(Message<BookVehicleCommand> message) {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String responseChannel = new String(metadata.getHeaders().lastHeader(CommandHeaders.RESPONSE_CHANNEL).value());
        String messageId = new String(metadata.getHeaders().lastHeader(CommandHeaders.MESSAGE_ID).value());
        BookVehicleCommand command = message.getPayload();
        log.info("Processing command: " + command);

        boolean booked = false;
        try {
            this.rentVehicleUseCase.bookVehicle(command.vehicleId());
            booked = true;
        } catch (Exception ex) {
            log.error("Error while booking vehicle {}: {}", command.vehicleId(), ex);
        }

        BookVehicleCommandReply reply = new BookVehicleCommandReply(booked);
        this.commandProducer.publishReply(responseChannel, messageId, command.vehicleId(), reply);
    }
}

record BookVehicleCommand(String vehicleId) implements Command {
    @Override
    public String getType() {
        return "BOOK_VEHICLE_COMMAND";
    }
}

record BookVehicleCommandReply(boolean booked) implements CommandReply {
    @Override
    public String getType() {
        return "BOOK_VEHICLE_COMMAND_REPLY";
    }
}
