package it.vincenzocorso.carsharing.debezium.transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.HashMap;
import java.util.Map;

import static it.vincenzocorso.carsharing.common.messaging.MessageFields.*;

@Slf4j
public class CustomTransformer<R extends ConnectRecord<R>> implements Transformation<R> {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public R apply(R sourceRecord) {
		Struct struct = (Struct)sourceRecord.value();
		String databaseOperation = struct.getString("op");

		if("c".equalsIgnoreCase(databaseOperation)) {
			Struct after = (Struct)struct.get("after");

			String channel = after.getString(CHANNEL);
			String messageKey = after.getString(MESSAGE_KEY);
			String payload = after.getString(PAYLOAD);
			String encodedHeaders = after.getString(HEADERS);

			Headers headers = sourceRecord.headers();
			try {
				Map<String, String> headersMap = objectMapper.readValue(encodedHeaders, new TypeReference<HashMap<String, String>>(){});
				for(Map.Entry<String, String> entry : headersMap.entrySet())
					headers.addString(entry.getKey(), entry.getValue());
			} catch(Exception ex) {
				log.error("Can't decode headers column: ", ex);
				throw new IllegalArgumentException();
			}

			sourceRecord = sourceRecord.newRecord(
					channel,
					null,
					null,
					messageKey,
					null,
					payload,
					sourceRecord.timestamp(),
					headers);
		}

		return sourceRecord;
	}

	@Override
	public ConfigDef config() {
		return new ConfigDef();
	}

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> configs) {
	}
}
