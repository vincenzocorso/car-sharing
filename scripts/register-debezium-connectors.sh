curl -i -X POST -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8083/connectors -d @../debezium-connectors/postgres-debezium-connector.json
curl -i -X POST -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8083/connectors -d @../debezium-connectors/mongodb-debezium-connector.json
