package it.vincenzocorso.carsharing.debezium.transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.HashMap;
import java.util.Map;

import static it.vincenzocorso.carsharing.common.messaging.MessageFields.*;

@Log
public class CustomMongoTransformer<R extends ConnectRecord<R>> implements Transformation<R> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public R apply(R sourceRecord) {
        Struct struct = (Struct)sourceRecord.value();
        String databaseOperation = struct.getString("op");

        if("c".equalsIgnoreCase(databaseOperation)) {
            String encodedAfter = struct.getString("after");
            try {
                JsonNode after = objectMapper.readTree(encodedAfter);

                String channel = after.get(CHANNEL).textValue();
                String messageKey = after.get(MESSAGE_KEY).textValue();
                String payload = after.get(PAYLOAD).textValue();
                String encodedHeaders = after.get(HEADERS).textValue();

                Headers headers = this.getHeaders(sourceRecord, encodedHeaders);

                sourceRecord = sourceRecord.newRecord(
                        channel,
                        null,
                        Schema.STRING_SCHEMA,
                        messageKey,
                        null,
                        payload,
                        sourceRecord.timestamp(),
                        headers);
            } catch (Exception ex) {
                log.severe("Can't decode the message payload: " + ex);
            }
        }

        return sourceRecord;
    }

    private Headers getHeaders(R sourceRecord, String encodedHeaders) {
        Headers headers = sourceRecord.headers();
        try {
            Map<String, String> headersMap = objectMapper.readValue(encodedHeaders, new TypeReference<HashMap<String, String>>(){});
            for(Map.Entry<String, String> entry : headersMap.entrySet())
                headers.addString(entry.getKey(), entry.getValue());
        } catch(Exception ex) {
            log.severe("Can't decode headers column: " + ex);
        }
        return headers;
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

