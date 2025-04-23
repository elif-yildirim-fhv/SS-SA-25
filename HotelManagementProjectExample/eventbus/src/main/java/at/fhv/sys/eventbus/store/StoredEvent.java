package at.fhv.sys.eventbus.store;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stored_events")
public class StoredEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String streamId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String eventData;

    @Column(nullable = false)
    private int version;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public StoredEvent() {}

    public StoredEvent(String streamId, String eventType, String eventData, int version, LocalDateTime timestamp) {
        this.streamId = streamId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.version = version;
        this.timestamp = timestamp;
    }

    // Getters
    public Long getId() { return id; }
    public String getStreamId() { return streamId; }
    public String getEventType() { return eventType; }
    public String getEventData() { return eventData; }
    public int getVersion() { return version; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setStreamId(String streamId) { this.streamId = streamId; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public void setEventData(String eventData) { this.eventData = eventData; }
    public void setVersion(int version) { this.version = version; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
} 