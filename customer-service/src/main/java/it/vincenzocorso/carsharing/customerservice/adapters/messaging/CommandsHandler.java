package it.vincenzocorso.carsharing.customerservice.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandHeaders;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.VerifyCustomerUseCase;
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
    VerifyCustomerUseCase verifyCustomerUseCase;
    CommandProducer commandProducer;

    @Incoming("customer-service-commands")
    public CompletionStage<Void> consume(Message<String> message) {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String type = new String(metadata.getHeaders().lastHeader(CommandHeaders.TYPE).value());

        // print the headers and payload
        System.out.println("Received message: " + message.getPayload());
        System.out.println("Type: " + type);

        try {
            if("VERIFY_CUSTOMER_COMMAND".equals(type)) {
                VerifyCustomerCommand command = this.objectMapper.readValue(message.getPayload(), VerifyCustomerCommand.class);
                this.handleVerifyCustomerCommand(message.withPayload(command));
            } else {
                throw new RuntimeException("Unknown command type: " + type);
            }
        } catch (Exception ex) {
            log.error("There was an exception during the command handling: ", ex);
            return message.nack(ex);
        }

        return message.ack();
    }

    void handleVerifyCustomerCommand(Message<VerifyCustomerCommand> message) {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String responseChannel = new String(metadata.getHeaders().lastHeader(CommandHeaders.RESPONSE_CHANNEL).value());
        String messageId = new String(metadata.getHeaders().lastHeader(CommandHeaders.MESSAGE_ID).value());

        // print the response channel and the message id
        System.out.println("Response channel: " + responseChannel);
        System.out.println("Message id: " + messageId);

        VerifyCustomerCommand command = message.getPayload();
        boolean canRent = this.verifyCustomerUseCase.verifyCustomer(command.customerId());
        VerifyCustomerCommandReply reply = new VerifyCustomerCommandReply(canRent);
        this.commandProducer.publishReply(responseChannel, messageId, command.customerId(), reply);
    }
}
