# Durable Execution Engine (Java)

## Overview
This project implements a native durable execution engine inspired by Temporal and Durable Functions.

## How Durability Works
Each side-effecting function is wrapped in a `step()` call. Before execution, the engine checks SQLite.
If the step already ran, its result is returned instead of re-executing.

## Sequence Tracking
A logical clock (AtomicInteger) generates a unique sequence ID per step to support loops and conditionals.

## Concurrency
Parallel steps use CompletableFuture. SQLite writes are synchronized to avoid SQLITE_BUSY errors.

## Zombie Step Handling
If a crash occurs after execution but before commit, the step is marked IN_PROGRESS.
On restart, all IN_PROGRESS steps are cleaned.

## How to Run
```bash
mvn clean package
java -cp target/durable-engine-1.0.jar app.Main

### Simulate Crash
```bash
java -jar target/durable-engine-1.0.jar crash
