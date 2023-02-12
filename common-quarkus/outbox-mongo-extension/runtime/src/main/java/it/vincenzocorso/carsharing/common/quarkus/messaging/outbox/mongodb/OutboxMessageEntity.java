package it.vincenzocorso.carsharing.common.quarkus.messaging.outbox.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import it.vincenzocorso.carsharing.common.messaging.MessageFields;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection = "outbox")
@NoArgsConstructor
public class OutboxMessageEntity extends PanacheMongoEntityBase {
	@BsonId
	@BsonProperty(MessageFields.MESSAGE_ID)
	public String messageId;

	@BsonProperty(MessageFields.CHANNEL)
	public String channel;

	@BsonProperty(MessageFields.MESSAGE_KEY)
	public String messageKey;

	@BsonProperty(MessageFields.PAYLOAD)
	public String payload;

	@BsonProperty(MessageFields.HEADERS)
	public String headers;
}
