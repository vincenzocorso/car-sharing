FROM debezium/connect:1.9.7.Final
ENV DEBEZIUM_DIR=$KAFKA_CONNECT_PLUGINS_DIR/debezium-transformer

RUN mkdir $DEBEZIUM_DIR
COPY build/libs/*.jar $DEBEZIUM_DIR
