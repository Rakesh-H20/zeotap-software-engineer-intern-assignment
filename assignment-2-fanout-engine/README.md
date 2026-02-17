# Assignment 2 – High Throughput Fan-Out Engine

## Overview
This project implements a high-throughput fan-out and transformation engine in Java.
It reads large input files using streaming and distributes records concurrently
to multiple downstream sinks such as REST, gRPC, Queue, and Database systems.

## Architecture
- Streaming file reader (BufferedReader)
- Strategy pattern for transformations
- Fan-out dispatcher using thread pool
- Retry mechanism for fault tolerance

## How to Run

```bash
mvn clean package
java -cp target/fanout-engine-1.0.jar app.Main
