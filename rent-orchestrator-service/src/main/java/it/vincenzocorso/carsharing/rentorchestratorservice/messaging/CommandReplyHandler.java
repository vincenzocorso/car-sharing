package it.vincenzocorso.carsharing.rentorchestratorservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import io.temporal.client.WorkflowClient;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandReplyHeaders;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.CreateRentSagaWorkflow;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.WorkflowCorrelation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class CommandReplyHandler {
    WorkflowClient workflowClient;
    ObjectMapper objectMapper;

    @Incoming("rent-orchestrator-service-response-channel")
    @Transactional
    public CompletionStage<Void> consume(Message<String> message) throws Exception {
        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String correlationId = new String(metadata.getHeaders().lastHeader(CommandReplyHeaders.CORRELATION_ID).value());

        VerifyCustomerCommandReply reply = this.objectMapper.readValue(message.getPayload(), VerifyCustomerCommandReply.class);
        log.info("VerifyCustomerCommandReply (canRent: {}, rejectReason: {})", reply.canRent(), reply.rejectReason());

        String workflowId = WorkflowCorrelation.<WorkflowCorrelation>findById(correlationId).workflowId;
        this.workflowClient.newWorkflowStub(CreateRentSagaWorkflow.class, workflowId).handleVerifyCustomerResponse(reply.canRent(), reply.rejectReason());

        return message.ack();
    }
}

record VerifyCustomerCommandReply(Boolean canRent, String rejectReason) {
}
