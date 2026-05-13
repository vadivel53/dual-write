package com.example.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.UUID;

public class CassandraCrudApp {

    private static final String CONTACT_POINT_1 = "127.0.0.1";
    private static final String CONTACT_POINT_2 = "127.0.0.2";
    private static final String CONTACT_POINT_3 = "127.0.0.3";

    private static final int PORT = 9042;

    private static final String USERNAME = "CHANGE_ME";
    private static final String PASSWORD = "CHANGE_ME";

    private static final String LOCAL_DATACENTER = "datacenter1";

    private static final String KEYSPACE = "demo_keyspace";
    private static final String TABLE = "users";

    public static void main(String[] args) {

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(CONTACT_POINT_1, PORT))
                .addContactPoint(new InetSocketAddress(CONTACT_POINT_2, PORT))
                .addContactPoint(new InetSocketAddress(CONTACT_POINT_3, PORT))
                .withLocalDatacenter(LOCAL_DATACENTER)
                .withAuthCredentials(USERNAME, PASSWORD)
                .build()) {

            System.out.println("Connected to Cassandra cluster");

            createKeyspace(session);
            createTable(session);

            UUID userId = UUID.randomUUID();

            insertData(session, userId, "sample-user", "sample@example.local");
            readData(session, userId);
            updateData(session, userId, "updated@example.local");
            readData(session, userId);
            deleteData(session, userId);

            System.out.println("CRUD operations completed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createKeyspace(CqlSession session) {
        String cql = "CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE +
                " WITH replication = {'class':'SimpleStrategy', 'replication_factor':3}";

        session.execute(cql);
    }

    private static void createTable(CqlSession session) {
        String cql = "CREATE TABLE IF NOT EXISTS " + KEYSPACE + "." + TABLE + " (" +
                "id UUID PRIMARY KEY," +
                "name TEXT," +
                "email TEXT" +
                ")";

        session.execute(cql);
    }

    private static void insertData(CqlSession session, UUID id, String name, String email) {
        String cql = String.format(
                "INSERT INTO %s.%s (id, name, email) VALUES (%s, '%s', '%s')",
                KEYSPACE,
                TABLE,
                id,
                name,
                email
        );

        session.execute(cql);
    }

    private static void readData(CqlSession session, UUID id) {
        String cql = String.format(
                "SELECT id, name, email FROM %s.%s WHERE id = %s",
                KEYSPACE,
                TABLE,
                id
        );

        ResultSet resultSet = session.execute(cql);
        Row row = resultSet.one();

        if (row != null) {
            System.out.println("ID: " + row.getUuid("id"));
            System.out.println("Name: " + row.getString("name"));
            System.out.println("Email: " + row.getString("email"));
        }
    }

    private static void updateData(CqlSession session, UUID id, String updatedEmail) {
        String cql = String.format(
                "UPDATE %s.%s SET email = '%s' WHERE id = %s",
                KEYSPACE,
                TABLE,
                updatedEmail,
                id
        );

        session.execute(cql);
    }

    private static void deleteData(CqlSession session, UUID id) {
        String cql = String.format(
                "DELETE FROM %s.%s WHERE id = %s",
                KEYSPACE,
                TABLE,
                id
        );

        session.execute(cql);
    }
}
