package at.fhv.sys.eventbus.services;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event", schema = "event_schema")
public class EventEntity {
	@Id
	private String id;
	private String streamId;
	private String type;
	@Lob
	private String data;
	private LocalDateTime timestamp;



	public EventEntity( String streamId, String type, String data) {
		this();
		this.streamId = streamId;
		this.type = type;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}

	public EventEntity() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}