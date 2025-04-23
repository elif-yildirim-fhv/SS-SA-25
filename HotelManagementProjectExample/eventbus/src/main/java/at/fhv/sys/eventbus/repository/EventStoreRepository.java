package at.fhv.sys.eventbus.repository;

import at.fhv.sys.eventbus.model.EventStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EventStoreRepository implements PanacheRepository<EventStore> {

	public List<EventStore> findByStream(String stream) {
		return list("stream", stream);
	}

	public List<EventStore> findByEventType(String eventType) {
		return list("eventType", eventType);
	}

	@Transactional
	public void saveEvent(String stream, Object event) {
		String eventData = JsonbBuilder.create().toJson(event);
		EventStore eventStore = new EventStore(
				stream,
				event.getClass().getSimpleName(),
				eventData
		);
		persist(eventStore);
	}
}