package at.fhv.sys.eventbus.store;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EventStore {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveEvent(String streamId, String eventType, String eventData, int version) {
        StoredEvent event = new StoredEvent(
            streamId,
            eventType,
            eventData,
            version,
            LocalDateTime.now()
        );
        entityManager.persist(event);
    }

    public List<StoredEvent> getEvents(String streamId) {
        return entityManager.createQuery(
            "SELECT e FROM StoredEvent e WHERE e.streamId = :streamId ORDER BY e.version",
            StoredEvent.class
        )
        .setParameter("streamId", streamId)
        .getResultList();
    }

    public int getLastEventVersion(String streamId) {
        try {
            return entityManager.createQuery(
                "SELECT MAX(e.version) FROM StoredEvent e WHERE e.streamId = :streamId",
                Integer.class
            )
            .setParameter("streamId", streamId)
            .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }
} 