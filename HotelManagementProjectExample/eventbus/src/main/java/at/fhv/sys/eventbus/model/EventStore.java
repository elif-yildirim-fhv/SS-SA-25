package at.fhv.sys.eventbus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_store")
public class EventStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String stream;
	private String eventType;
	private LocalDateTime timestamp;

	@Lob
	@Column(length = 10000)
	private String eventData;

	// Default constructor
	public EventStore() {
		this.timestamp = LocalDateTime.now();
	}

	public EventStore(String stream, String eventType, String eventData) {
		this();
		this.stream = stream;
		this.eventType = eventType;
		this.eventData = eventData;
	}

	public Long getId() { return id; }
	public String getStream() { return stream; }
	public void setStream(String stream) { this.stream = stream; }
	public String getEventType() { return eventType; }
	public void setEventType(String eventType) { this.eventType = eventType; }
	public LocalDateTime getTimestamp() { return timestamp; }
	public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
	public String getEventData() { return eventData; }
	public void setEventData(String eventData) { this.eventData = eventData; }
}