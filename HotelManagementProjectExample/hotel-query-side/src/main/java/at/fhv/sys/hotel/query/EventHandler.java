package at.fhv.sys.hotel.query;

import at.fhv.sys.hotel.commands.shared.events.*;
import at.fhv.sys.hotel.projection.BookingProjection;
import at.fhv.sys.hotel.projection.CustomerProjection;
import at.fhv.sys.hotel.projection.RoomProjection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logmanager.Logger;

@ApplicationScoped
public class EventHandler {
    private static final Logger LOGGER = Logger.getLogger(EventHandler.class.getName());

    @Inject
    RoomProjection roomProjection;

    @Inject
    BookingProjection bookingProjection;

    @Inject
    CustomerProjection customerProjection;
    


    @Transactional
    public void handleEvent(Object event) {
        LOGGER.info("Handling event: " + event.getClass().getSimpleName());

        if (event instanceof RoomCreated || event instanceof RoomUpdated) {
            roomProjection.processEvent(event);
        } else if (event instanceof BookingCreated || event instanceof BookingCancelled || event instanceof PaymentReceived) {
            bookingProjection.processEvent(event);
        } else if (event instanceof CustomerCreated || event instanceof CustomerUpdated) {
            customerProjection.processEvent(event);
        } else {
            LOGGER.warning("Unknown event type: " + event.getClass().getName());
        }
    }
} 