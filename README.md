# Cassandra CRUD Application

Java 17 Maven application using the DataStax Java Driver for Apache Cassandra 4.1.x.

## Features

- Connect to 3-node Cassandra cluster
- Authentication enabled
- No TLS configuration
- Create Keyspace
- Create Table
- Insert record
- Read record
- Update record
- Delete record

## Technologies

- Java 17
- Maven
- Apache Cassandra 4.1.10
- DataStax Java Driver 4.17.0

## Project Structure

```text
src/main/java/com/example/cassandra/
 └── CassandraCrudApp.java
```

## Update Connection Details

Modify the following placeholders in:

```text
src/main/java/com/example/cassandra/CassandraCrudApp.java
```

Update:

- Contact points
- Username
- Password
- Local datacenter

## Build Project

```bash
mvn clean package
```

## Run Application

```bash
java -jar target/cassandra-crud-app-1.0.0.jar
```

## Example Operations

The application performs:

1. Keyspace creation
2. Table creation
3. Insert operation
4. Read operation
5. Update operation
6. Delete operation

## Cassandra Requirements

Ensure:

- Cassandra nodes are reachable
- Port 9042 is open
- Authentication is enabled correctly
- Local DC name matches cluster configuration
