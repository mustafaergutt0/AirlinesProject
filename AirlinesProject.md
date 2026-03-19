Gemini şunu dedi:
Airlines projenin monolitikten mikroservis mimarisine geçişini, kullandığın teknolojileri (Kafka, Redis, ELK) ve o "giant" yapıyı en profesyonel şekilde yansıtacak bir README.md hazırladım.

Bunu projenin kök dizinine README.md adıyla kaydedebilirsin.

✈️ Airlines Management System (Microservices Architecture)
This project is a high-performance, scalable Airlines Management System developed during my long-term internship at Turkcell. The system has been evolved from a monolithic structure to a modern Microservices Architecture to handle complex flight, passenger, and pilot operations efficiently.

🚀 Key Features
Monolith to Microservices: Complete architectural migration from a single core to independent services.

Centralized Configuration: Managed through Spring Cloud Config and Eureka Discovery.

Event-Driven Communication: Asynchronous messaging using Apache Kafka and RabbitMQ.

Secure Authentication: JWT (JSON Web Token) based security with Redis integration for token management and UUID-like session tracking.

Advanced Search & Logging: Integrated ElasticSearch for high-speed data retrieval and a dedicated LogService.

Inter-Service Communication: Handled via OpenFeign clients for seamless RESTful interaction.


<img width="968" height="533" alt="image" src="https://github.com/user-attachments/assets/9d07f3f6-8536-4ee8-be53-a48bcc3bdb18" />






🛠️ Microservices Overview
API Gateway & Eureka: The entry point and service discovery registry.

Pilot Service: Independent management of pilot records and availability.

Passenger & Ticket Service: Handles booking flows and passenger data using DTO (Record) patterns.

Flight & Airport Service: Manages schedules, aircraft settings, and airport integrations.

Log Service: A dedicated service listening to RabbitMQ/Kafka for centralized logging.




Gemini şunu dedi:
Airlines projenin monolitikten mikroservis mimarisine geçişini, kullandığın teknolojileri (Kafka, Redis, ELK) ve o "giant" yapıyı en profesyonel şekilde yansıtacak bir README.md hazırladım.

Bunu projenin kök dizinine README.md adıyla kaydedebilirsin.

✈️ Airlines Management System (Microservices Architecture)
This project is a high-performance, scalable Airlines Management System developed during my long-term internship at Turkcell. The system has been evolved from a monolithic structure to a modern Microservices Architecture to handle complex flight, passenger, and pilot operations efficiently.

🚀 Key Features
Monolith to Microservices: Complete architectural migration from a single core to independent services.

Centralized Configuration: Managed through Spring Cloud Config and Eureka Discovery.

Event-Driven Communication: Asynchronous messaging using Apache Kafka and RabbitMQ.

Secure Authentication: JWT (JSON Web Token) based security with Redis integration for token management and UUID-like session tracking.

Advanced Search & Logging: Integrated ElasticSearch for high-speed data retrieval and a dedicated LogService.

Inter-Service Communication: Handled via OpenFeign clients for seamless RESTful interaction.

🏗️ Technical Stack
Category	Technologies
Backend	Java 17+, Spring Boot 3.x, Spring Cloud
Database	PostgreSQL, Redis (Caching & Auth)
Messaging	Apache Kafka, RabbitMQ
Search Engine	ElasticSearch
Architecture	Microservices, API Gateway (Eureka), Feign Client
DevOps	Docker, Maven
🛠️ Microservices Overview
API Gateway & Eureka: The entry point and service discovery registry.

Pilot Service: Independent management of pilot records and availability.

Passenger & Ticket Service: Handles booking flows and passenger data using DTO (Record) patterns.

Flight & Airport Service: Manages schedules, aircraft settings, and airport integrations.

Log Service: A dedicated service listening to RabbitMQ/Kafka for centralized logging.

🚦 Getting Started
Prerequisites
Docker & Docker Compose

Java 17 or higher

Maven




git clone https://github.com/mustafaergutt0/AirlinesProject.git







📈 Evolution Timeline
Monolithic Era: Initial setup with Controller-Service-Repository layers and MapStruct integration.

The Great Migration: Separation of Pilot, Passenger, and Flight services into independent modules.

Connectivity: Deployment of Eureka Discovery and API Gateway.

Full Integration: Implementation of Kafka for events and Redis for stateful authentication.

👨‍💻 Author
Mustafa Ergut Computer Engineering Student at AGU | Backend Developer Intern at Turkcell
