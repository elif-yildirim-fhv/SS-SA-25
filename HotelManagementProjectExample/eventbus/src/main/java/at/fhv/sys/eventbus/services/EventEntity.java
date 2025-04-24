package at.fhv.sys.eventbus.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EventEntity extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String streamId;
	private String type;
	@Lob
	private String data;
	private LocalDateTime timestamp;



	public EventEntity( String streamId, String type, String data) {
		this.timestamp = LocalDateTime.now();
		this.streamId = streamId;
		this.type = type;
		this.data = data;
	}

	public EventEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreamId() {
		return streamId;
	}

	public String getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "EventEntity{" +
				"id=" + id +
				", streamId='" + streamId + '\'' +
				", type='" + type + '\'' +
				", data='" + data + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}