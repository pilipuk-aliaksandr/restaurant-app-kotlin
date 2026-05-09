# **🍴 Restaurant Application**

The Restaurant App is a microservices-based system for automating ordering and food preparation for restaurants
Application responsible for managing the lifecycle of customer's orders and cooking process.

## **Architecture & Features**

**Microservices:** Spring Cloud API Gateway was implemented to the application as a single entrypoint. Also in the API Gateway level was configured centralized security-layer (JWT-authentication).

**Event-Driven Communication:** Order-service publishes OrderCreatedEvent to Kafka. Kitchen-service consumes the event and initiates the cooking process. Once prepared, Kitchen-service publishes an OrderReadyEvent. Order-service listens for completion to update the final order status.

**Transactional Outbox Pattern:** ensures reliable message delivery by saving events in the database before publishing to Kafka.

## **Prerequisites:**

Kotlin 2.3.10, Maven 3+, Docker

## **Running the Service**
1. Clone the repository:
   `git clone https://github.com/pilipuk-aliaksandr/restaurant-app-kotlin.git`

3. Start infrastructure via Docker:

`docker-compose up -d`

3. Build maven and run all microservices:
   `mvn clean install`

#### api-gateway (_Port:8082_)

`mvn spring-boot:run -pl api-gateway`

#### kitchen-service (_Port:8081_)

`mvn spring-boot:run -pl kitchen-service`

#### order-service (_Port:8080_)

`mvn spring-boot:run -pl order-service`

## **Documentation**

When the services are running, you can access the Swagger UI for each service:
http://localhost:8082/swagger-ui/index.html

To switch between documentation of different microservices, you need to select definition between gateway-service, order-service or kitchen-service.