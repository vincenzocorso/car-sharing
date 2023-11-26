workspace {
    !identifiers hierarchical

    model {
        // Actors
        customer = person "Customer"

        // System
        system = softwareSystem "Car Sharing System" {
            // Frontend
            group "Frontend" {
                mobileApp = container "Mobile App" "Flutter" "Mobile"
            }

            group "Backend" {
                // Gateway
                apiGateway = container "API Gateway"
                mobileApp -> apiGateway "Redirect requests to" "JSON/HTTPS"

                // Kafka
                kafka = container "Kafka"

                // Customer Service
                customerService = container "Customer Service" {
                    technology "Quarkus"
                }
                customerServiceDb = container "Customer Service DB" {
                    technology "MongoDB"
                }
                apiGateway -> customerService "A" "JSON/HTTPS"
                customerService -> customerServiceDb "Reads from and writes to"
                kafka -> customerService "Publish event to"

                // Rent Service
                rentService = container "Rent Service" {
                    technology "Spring"
                }
                rentServiceDb = container "Rent Service DB" {
                    technology "Postgres"
                }
                apiGateway -> rentService "A" "JSON/HTTPS"
                rentService -> rentServiceDb "Reads from and writes to" "SQL/TCP"
                kafka -> rentService "Publish event to"

                // Rent Orchestrator Service
                rentOrchestratorService = container "Rent Orchestrator Service" {
                    technology "Quarkus"
                }
                rentOrchestratorServiceDb = container "Rent Orchestrator Service DB" {
                    technology "Postgres"
                }
                apiGateway -> rentOrchestratorService "A" "JSON/HTTPS"
                rentOrchestratorService -> rentOrchestratorServiceDb "Reads from and writes to" "SQL/TCP"
                kafka -> rentOrchestratorService "Publish event to"

                // Vehicle Service
                vehicleService = container "Vehicle Service" {
                    technology "Quarkus"
                }
                vehicleServiceDb = container "Vehicle Service DB" {
                    technology "MongoDB"
                }
                apiGateway -> vehicleService "A" "JSON/HTTPS"
                vehicleService -> vehicleServiceDb "Reads from and writes to"
                kafka -> vehicleService "Publish event to"

                // Payment Service
                paymentService = container "Payment Service" {
                    technology "Quarkus"
                }
                paymentServiceDb = container "Payment Service DB" {
                    technology "Postgres"
                }
                apiGateway -> paymentService "A" "JSON/HTTPS"
                paymentService -> paymentServiceDb "Reads from and writes to" "SQL/TCP"
                kafka -> paymentService "Publish event to"

                // Notification Service
                notificationService = container "Notification Service" {
                    technology "Quarkus"
                }
                notificationServiceDb = container "Notification Service DB" {
                    technology "Postgres"
                }
                notificationService -> notificationServiceDb "Reads from and writes to" "SQL/TCP"
                kafka -> notificationService "Listen events from"
            }
        }

        customer -> system.mobileApp "Uses"
    }

    views {
        mainView = systemContext system "MainView" {
            include *
            autolayout lr
        }

        highLevelView = container system "HighLevelView" {
            include *
            autolayout lr
        }

        theme default

        styles {
            element "Mobile" {
                shape MobileDevicePortrait
            }
        }
    }
}
