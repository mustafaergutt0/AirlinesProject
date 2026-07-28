# ✈️ Ergutlar Airlines - Distributed Microservice Reservation System

**Ergutlar Airlines** is an enterprise-grade, highly scalable flight reservation and airline management platform designed from the ground up using a distributed microservices architecture. The system incorporates robust service discovery, event-driven messaging, centralized log management, and real-time search capabilities to simulate a production-ready aviation ecosystem.

---

## 🏗️ System Architecture & Communication Flow

The platform relies on independent, domain-driven microservices. All inter-service communication and client requests are managed through secure routing and service discovery mechanisms.

```text
                      [ Client Applications ]
                               │
                               ▼
                       [ api-gateway ] ──────────┐
                               │                 │
            ┌──────────────────┼──────────────┐  │
            ▼                  ▼              ▼  ▼
   [ eureka-service ]   [ auth-service ]  [ airlines-main-service ]
  (Service Registry)     (JWT & Redis)    (Flights & Reservations)
            │                  │              │
            └──────────┬───────┴──────────────┘
                       ▼
         [ Message Broker & Search Layer ]
         ├── Apache Kafka (Event Streams)
         ├── Elasticsearch (Search & Indexing)
         └── Redis (Distributed Caching / UUID Sessions)
                       │
                       ▼
        [ Operations & Support Services ]
        ├── pilot-service (Crew Management)
        └── log-service (Centralized Logging)






📦 Detailed Module Breakdown
1. Infrastructure & Core Services
eureka-service: Acts as the Spring Cloud Netflix Eureka Server registry. It enables dynamic service registration and discovery, allowing microservices to locate and communicate with each other seamlessly without hardcoded IP configurations.

api-gateway: The single entry point for all client traffic. It handles request routing, load balancing, and cross-cutting concerns before dispatching calls to internal microservices.

2. Business Domain Services
airlines-main-service: The core operational hub of the platform. It manages flight scheduling, seat inventory, route configurations, and processes ticket booking/reservation lifecycles.

pilot-service: Dedicated domain service responsible for managing pilot profiles, crew rosters, duty schedules, and flight assignments.





3. Security, Caching & Data Management
auth-service: Handles identity management, user authentication, and role-based authorization. It utilizes JSON Web Tokens (JWT) integrated with Redis to manage session states, token invalidation, and UUID-based mappings securely.

search-service: High-performance search and analytics module integrated with Apache Kafka and Elasticsearch, enabling real-time event indexing, lightning-fast flight filtering, and advanced query execution.

log-service: Centralized logging architecture designed to aggregate and monitor system-wide application logs, tracking distributed traces and error handling across all microservices.




🚀 Tech Stack & Design Patterns
Core Framework: Java, Spring Boot, Spring Cloud (Netflix Eureka, Gateway)

Architecture Style: Microservices, Event-Driven Architecture (EDA), Database-per-service pattern

Messaging & Search: Apache Kafka, Elasticsearch

Caching & State: Redis (UUID token management & distributed caching)

Containerization: Docker, Modular Docker Compose (infra, kafka-elastic, redis, main orchestration)

Database: Relational databases per service (PostgreSQL / MySQL)







🐳 Docker Infrastructure Setup
The project uses a structured, modular containerization strategy to isolate infrastructure dependencies from application runtimes:

docker-compose-infra.yml: Provisions core databases, networking, and service discovery registries.

docker-compose-kfkelastic.yml: Deploys Apache Kafka, Zookeeper, and Elasticsearch clusters for messaging and indexing.

docker-compose-redis.yml: Spins up the Redis caching and session store layer.

docker-compose.yml: Orchestrates the deployment of all Spring Boot microservices together with the infrastructure stac
