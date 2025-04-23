package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventProcessingService {
    private static final Logger LOG = Logger.getLogger(EventProcessingService.class.getName());

    @Inject
    @RestClient
    QueryClient queryClient;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void processEvent(String stream, Object eventObject) {
        LOG.info("Processing event: " + eventObject.getClass().getSimpleName());

        // Forward event to query side
        if (eventObject instanceof CustomerCreated) {
            queryClient.forwardCustomerCreatedEvent((CustomerCreated) eventObject);
        } else if (eventObject instanceof CustomerUpdated) {
            queryClient.forwardCustomerUpdatedEvent((CustomerUpdated) eventObject);
        } else if (eventObject instanceof BookingCreated) {
            queryClient.forwardRoomBookedEvent((BookingCreated) eventObject);
        } else if (eventObject instanceof BookingCancelled) {
            queryClient.forwardBookingCancelledEvent((BookingCancelled) eventObject);
        } else if (eventObject instanceof PaymentReceived) {
            queryClient.processPaymentCreatedEvent((PaymentReceived) eventObject);
        } else if (eventObject instanceof RoomCreated) {
            // Forward room created event
            // Implementation needed in QueryClient
        } else {
            LOG.warning("Unknown event type: " + eventObject.getClass().getName());
        }

        // Store event in EventStore
        storeEvent(stream, eventObject);
    }

    @Transactional
    private void storeEvent(String stream, Object eventObject) {
        EventEntity event = new EventEntity();
        event.setId(UUID.randomUUID().toString());
        event.setStreamId(stream);
        event.setType(eventObject.getClass().getSimpleName());
        event.setData(serializeEvent(eventObject));
        event.setTimestamp(LocalDateTime.now());

        entityManager.persist(event);
        LOG.info("Event stored in EventStore: " + event.getId());
    }

    private String serializeEvent(Object eventObject) {
        // Simple serialization - in a real application, use JSON serialization
        return eventObject.toString();
    }

    public List<EventEntity> getAllEvents() {
        return entityManager.createQuery("SELECT e FROM EventEntity e ORDER BY e.timestamp", EventEntity.class)
                .getResultList();
    }

    public List<EventEntity> getEventsByStream(String streamId) {
        return entityManager.createQuery("SELECT e FROM EventEntity e WHERE e.streamId = :streamId ORDER BY e.timestamp", EventEntity.class)
                .setParameter("streamId", streamId)
                .getResultList();
    }
}