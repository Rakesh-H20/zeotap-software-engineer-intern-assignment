ZEOTAP ASSIGNMENT

```md
Zeotap Software Engineer Intern Assignment

This repository contains my solutions for the Zeotap Software Engineer Intern technical assignment.
The implementations focus on durability, fault tolerance, concurrency, and scalable backend system design.Repository Structure

zeotap-software-engineer-intern-assignment
├── assignment-1-durable-engine
│   └── Native Durable Execution Engine (Java)
└── assignment-2-fanout-engine
└── High-Throughput Fan-Out Engine (Java)

```

## Assignment 1: Native Durable Execution Engine

### Overview
A Java-based **durable workflow execution engine** inspired by systems such as
Temporal and Azure Durable Functions.

### Key Characteristics
- Step-level durability
- Idempotent execution
- Safe recovery after JVM crashes
- SQLite-based persistence

### How to Run
```bash
mvn clean package
java -jar target/durable-engine-1.0.jar

To simulate a crash:
java -jar target/durable-engine-1.0.jar crash

To resume:
java -cp target/durable-engine-1.0.jar app.Main
```

Restarting the application resumes execution from the last completed step.

---

## Assignment 2: High-Throughput Fan-Out Engine

### Overview

A high-throughput fan-out processing engine that reads streaming input data,
applies sink-specific transformations, and dispatches records concurrently.

### Key Characteristics

* Streaming input processing
* Concurrent fan-out using thread pools
* Per-sink transformations
* Retry handling for fault tolerance

### How to Run

```bash
mvn clean package
java -cp target/fanout-engine-1.0.jar app.Main
```

---

## Notes

* Each assignment is implemented as an independent Maven project
* All dependencies are bundled for ease of execution
* Detailed design explanations are provided in the submitted PDF

