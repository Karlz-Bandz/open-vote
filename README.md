# OpenVote

**Secure online voting platform built with Java and Spring Boot.**

VoteFlow is a digital voting application that allows users to participate in online elections securely. The project focuses on authentication, authorization, vote integrity, transactional processing, and handling concurrent voting requests.

## 🚀 Features

* User registration and authentication
* JWT-based authentication
* Role-based access control
* Election management
* Candidate management
* Secure vote submission
* One vote per user per election
* Election results
* Transactional vote processing
* Concurrency handling
* Audit logging

## 🛠️ Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* PostgreSQL
* Gradle
* Docker
* JUnit 5
* Mockito

## 🏗️ Architecture

The application is built as a modular monolith using Spring Boot.

```text
Angular
   │
   │ REST API
   ▼
Spring Boot
   ├── Authentication
   ├── Elections
   ├── Candidates
   ├── Voting
   └── Results
          │
          ▼
     PostgreSQL
```

## 🎯 Project Goals

The main goal of the project is to build a reliable online voting system while applying real-world backend development practices such as:

* secure authentication and authorization
* database constraints
* transaction management
* concurrent request handling
* automated testing
* clean architecture
* REST API design

## 🚧 Project Status

**In development**

More features and improvements will be added as the project evolves.
