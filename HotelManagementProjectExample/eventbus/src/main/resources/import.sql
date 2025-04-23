
CREATE SCHEMA IF NOT EXISTS event_schema;

CREATE TABLE event_schema.event_store (
                                          id SERIAL PRIMARY KEY,
                                          stream VARCHAR(255),
                                          eventType VARCHAR(255),
                                          timestamp TIMESTAMP,
                                          eventData VARCHAR(255)
);



INSERT INTO event_schema.event_store (id, stream, eventType, timestamp, eventData)
VALUES (1, 'hotel', 'ROOM_CREATED', CURRENT_TIMESTAMP, '{"room": 101, "type": "single"}');
