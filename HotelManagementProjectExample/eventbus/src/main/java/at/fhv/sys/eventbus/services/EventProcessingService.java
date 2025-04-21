package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Logger;

@ApplicationScoped
public class EventProcessingService {
    private static final Logger LOG = Logger.getLogger(EventProcessingService.class.getName());

    @Inject
    @RestClient
    QueryClient queryClient;

    public void processEvent(String stream, Object eventObject) {
        LOG.info("Processing event: " + eventObject.getClass().getSimpleName());
        
        if (eventObject instanceof CustomerCreated) {
            queryClient.forwardCustomerCreatedEvent((CustomerCreated) eventObject);
        } else if (eventObject instanceof CustomerUpdated) {
            queryClient.forwardCustomerUpdatedEvent((CustomerUpdated) eventObject);
        } else if (eventObject instanceof BookingCreated) {
            queryClient.forwardRoomBookedEvent((BookingCreated) eventObject);
        } else if (eventObject instanceof BookingCancelled) {
            queryClient.forwardBookingCancelledEvent((BookingCancelled) eventObject);
        } else {
            LOG.warning("Unknown event type: " + eventObject.getClass().getName());
        }
        
        // TODO: Store event in EventStore
    }
}