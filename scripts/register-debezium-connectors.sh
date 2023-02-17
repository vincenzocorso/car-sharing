curl -i -X POST -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8083/connectors -d @../rent-service/rent-service-debezium-connector.json
curl -i -X POST -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8083/connectors -d @../customer-service/customer-service-debezium-connector.json
