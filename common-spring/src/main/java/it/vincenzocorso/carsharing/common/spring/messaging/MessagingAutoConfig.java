package it.vincenzocorso.carsharing.common.spring.messaging;

import it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa.OutboxJpaMessagingConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(OutboxJpaMessagingConfig.class)
public class MessagingAutoConfig {
}
