# MIDAS Core — JPMorgan Chase Forage Project

A real-time financial transaction processing system built as part of the **JPMorgan Chase Software Engineering Virtual Experience** on Forage.

---

## About the Project

MIDAS Core is a backend service that processes financial transactions in real time. It listens to a Kafka message queue, validates each transaction, integrates with an external Incentive API, persists records to a database, and exposes a REST API for querying user balances.

---

## Tech Stack

- **Java 25**
- **Spring Boot 3.2.5**
- **Apache Kafka** — real-time message streaming
- **Spring Data JPA** — database access and ORM
- **H2 Database** — in-memory relational database
- **Spring Web (RestTemplate + MVC)** — REST API integration and exposure

---

## Features

- **Kafka Consumer** — listens to the `trader-updates` topic and receives Transaction messages in real time
- **Transaction Validation** — checks that both sender and recipient exist, and that the sender has sufficient balance
- **Incentive API Integration** — posts validated transactions to an external Incentive API and applies the returned incentive amount to the recipient's balance
- **Transaction Persistence** — saves all valid transactions with incentive amounts to the database
- **Balance REST Endpoint** — exposes `GET /balance?userId={id}` on port `33400`

---

## How to Run

**1. Start the Incentive API:**
```bash
java -jar services/*.jar
```

**2. Run the application:**
```bash
./mvnw spring-boot:run
```

**3. Query a user balance:**
```bash
curl http://localhost:33400/balance?userId=5
```

---

## Tasks Completed

| Task | Description |
|------|-------------|
| Task 1 | Fixed application startup and configured H2 database |
| Task 2 | Implemented Kafka consumer to receive Transaction messages |
| Task 3 | Built transaction validation and persistence logic |
| Task 4 | Integrated external Incentive REST API using RestTemplate |
| Task 5 | Exposed a GET /balance endpoint via Spring REST Controller |

---

## Key Concepts Learned

- How Apache Kafka works as a message queue in a microservices architecture
- How to use Spring Data JPA to model and persist relational data
- How REST APIs act as contracts between decoupled services
- How to consume external APIs using RestTemplate
- Architectural trade-offs when adding features to existing services

---

## Certificate

