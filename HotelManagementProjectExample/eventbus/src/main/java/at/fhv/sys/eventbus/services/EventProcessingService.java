package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.eventbus.repository.EventEntityPanache;
import at.fhv.sys.hotel.commands.shared.events.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Logger;
import jakarta.json.bind.JsonbBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventProcessingService {
    private static final Logger LOG = Logger.getLogger(EventProcessingService.class.getName());

    @Inject
    @RestClient
    QueryClient queryClient;

//    @Inject
//    EventStoreRepository eventStoreRepository;

    @Inject //So wie Autowired, kein Constructor nötig :D kann man aber lieber ned
    ObjectMapper objectMapper;

    @Inject
    EventEntityPanache eventEntityPanache;

    @Transactional
    public void processEvent(String stream, Object eventObject) throws JsonProcessingException {
        String payload = null;
        try{
            payload = objectMapper.writeValueAsString(eventObject);
            EventEntity eventEntity = new EventEntity(
                    stream,
                    eventObject.getClass().getSimpleName(),
                    payload
            );
            Logger.getAnonymousLogger().info(eventEntity.toString());
            eventEntityPanache.createEvent(eventEntity);
        }catch(Exception e){
            Logger.getAnonymousLogger().warning(e.getMessage());
        }



//        LOG.info("Processing event: " + eventObject.getClass().getSimpleName());
//        LOG.fine("Event details: " + eventObject);
//
//        try {
//            // Store event in EventStore first (most important part)
//            LOG.fine("Starting to store event in database");
//            storeEvent(stream, eventObject);
//            LOG.fine("Event stored successfully in database");
//
//            // Then try to forward the event to query side
//            try {
//                LOG.fine("Attempting to forward event to query side");
//                forwardEventToQuerySide(eventObject);
//                LOG.fine("Event forwarded successfully to query side");
//            } catch (Exception e) {
//                // Log error but don't propagate it further
//                LOG.severe("Failed to forward event to query side: " + e.getMessage());
//                e.printStackTrace();
//                LOG.severe("Event was stored in database but query side was not updated.");
//                // Don't rethrow so we don't fail the entire operation
//                // The event is already stored in the database
//            }
//        } catch (Exception e) {
//            LOG.severe("Critical error in processEvent: " + e.getMessage());
//            e.printStackTrace();
//            throw e; // Rethrow critical errors
//        }
    }

    public void forwardEventToQuerySide(Object eventObject) {
        try {
            LOG.info("Forwarding event to query side: " + eventObject.getClass().getSimpleName());

            // Forward event to query side
            if (eventObject instanceof CustomerCreated) {
                LOG.info("Forwarding CustomerCreated event");
                queryClient.forwardCustomerCreatedEvent((CustomerCreated) eventObject);
                LOG.fine("CustomerCreated event forwarded successfully");
            } else if (eventObject instanceof CustomerUpdated) {
                LOG.fine("Forwarding CustomerUpdated event");
                queryClient.forwardCustomerUpdatedEvent((CustomerUpdated) eventObject);
                LOG.fine("CustomerUpdated event forwarded successfully");
            } else if (eventObject instanceof BookingCreated) {
                LOG.fine("Forwarding BookingCreated event");
                queryClient.forwardRoomBookedEvent((BookingCreated) eventObject);
                LOG.fine("BookingCreated event forwarded successfully");
            } else if (eventObject instanceof BookingCancelled) {
                LOG.fine("Forwarding BookingCancelled event");
                queryClient.forwardBookingCancelledEvent((BookingCancelled) eventObject);
                LOG.fine("BookingCancelled event forwarded successfully");
            } else if (eventObject instanceof PaymentReceived) {
                LOG.fine("Forwarding PaymentReceived event");
                queryClient.processPaymentCreatedEvent((PaymentReceived) eventObject);
                LOG.fine("PaymentReceived event forwarded successfully");
            } else if (eventObject instanceof RoomCreated) {
                // Forward room created event - not implemented in query side yet
                LOG.info("No handler for RoomCreated event in query side yet");
            } else {
                LOG.warning("Unknown event type: " + eventObject.getClass().getName());
            }
        } catch (Exception e) {
            LOG.severe("Error in forwarding event: " + e.getMessage());
            LOG.severe("Exception class: " + e.getClass().getName());
            e.printStackTrace();
            // Rethrow to be handled by the caller
            throw e;
        }
    }

//    private void storeEvent(String stream, Object eventObject) {
//        try {
//            LOG.info("Storing event in database: " + eventObject.getClass().getSimpleName());
//
//            EventEntity event = new EventEntity(
//                    stream,
//                    eventObject.getClass().getSimpleName(),
//                    );
//
//            eventStoreRepository.saveEvent(event.getStreamId(), eventObject);
//            LOG.info("Event stored in EventStore: " + event.getId());
//        } catch (Exception e) {
//            LOG.severe("Failed to store event in database: " + e.getMessage());
//            throw e; // This is critical so we rethrow
//        }
//    }

    private String serializeEvent(Object eventObject) {
        // Proper JSON serialization
        return JsonbBuilder.create().toJson(eventObject);
    }

}
