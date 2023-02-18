package it.vincenzocorso.carsharing.customerservice.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandHeaders;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RentVehicleUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Slf4j
@AllArgsConstructor
public class CommandsHandler {
    ObjectMapper objectMapper;
    RentVehicleUseCase rentVehicleUseCase;
    CommandProducer commandProducer;

    @Incoming("customer-service-commands")
    public CompletionStage<Void> dispatchMessage(Message<String> message) throws Exception {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String type = new String(metadata.getHeaders().lastHeader(CommandHeaders.TYPE).value());
        log.info("Received message of type: " + type);

        switch (type) {
            case "VERIFY_CUSTOMER_COMMAND" -> {
                VerifyCustomerCommand command = this.objectMapper.readValue(message.getPayload(), VerifyCustomerCommand.class);
                this.processVerifyCustomerCommand(message.withPayload(command));
            }
            default -> throw new InternalServerException("Unknown command type: " + type);
        }

        return message.ack();
    }

    private void processVerifyCustomerCommand(Message<VerifyCustomerCommand> message) {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String responseChannel = new String(metadata.getHeaders().lastHeader(CommandHeaders.RESPONSE_CHANNEL).value());
        String messageId = new String(metadata.getHeaders().lastHeader(CommandHeaders.MESSAGE_ID).value());
        VerifyCustomerCommand command = message.getPayload();
        log.info("Processing command: " + command);

        boolean canRent = this.rentVehicleUseCase.verifyCustomer(command.customerId());

        VerifyCustomerCommandReply reply = new VerifyCustomerCommandReply(canRent);
        this.commandProducer.publishReply(responseChannel, messageId, command.customerId(), reply);
    }
}
