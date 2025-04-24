CREATE SCHEMA IF NOT EXISTS event_schema;

CREATE TABLE event_schema.event_store (
                                          id SERIAL PRIMARY KEY,
                                          stream VARCHAR(255),
                                          eventType VARCHAR(255),
                                          timestamp TIMESTAMP,
                                          eventData TEXT
);
CREATE TABLE event_schema.event(
                                   id VARCHAR(255) PRIMARY KEY,
                                   streamId VARCHAR(255),
                                   type VARCHAR(255),
                                   data TEXT,
                                   timestamp TIMESTAMP

);




CREATE SCHEMA IF NOT EXISTS event_schema;

CREATE TABLE event_schema.event_store (
                                          id SERIAL PRIMARY KEY,
                                          stream VARCHAR(255),
                                          eventType VARCHAR(255),
                                          timestamp TIMESTAMP,
                                          eventData TEXT
);
CREATE TABLE event_schema.event(
                                   id VARCHAR(255) PRIMARY KEY,
                                   streamId VARCHAR(255),
                                   type VARCHAR(255),
                                   data TEXT,
                                   timestamp TIMESTAMP
);




INSERT INTO event_schema.event_store (id, stream, eventType, timestamp, eventData)
VALUES (1, 'hotel', 'ROOM_CREATED', CURRENT_TIMESTAMP, '{"room": 101, "type": "single"}');
