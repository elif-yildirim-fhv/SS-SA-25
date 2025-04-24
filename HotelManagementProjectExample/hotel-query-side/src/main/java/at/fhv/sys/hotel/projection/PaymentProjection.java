package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import at.fhv.sys.hotel.models.PaymentQueryModel;
import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import at.fhv.sys.hotel.service.PaymentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logmanager.Logger;


@ApplicationScoped
public class PaymentProjection implements Projection {
    private static final Logger logger = Logger.getLogger(PaymentProjection.class.getName());

    @Inject
    PaymentService paymentService;

    @Override
    public void clearState() {
        try {
            paymentService.deleteAll();
        } catch (Exception e) {
            logger.severe("Error clearing payment state: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void processEvent(Object event) {
        if (event instanceof PaymentReceived) {
            processPaymentReceivedEvent((PaymentReceived) event);
        }
    }

    @Transactional
    public void processPaymentReceivedEvent(PaymentReceived event) {
        try {
            logger.info("Processing PaymentReceived event: " + event);

            PaymentQueryModel payment = new PaymentQueryModel(
                    event.getPaymentId(),
                    event.getBookingId(),
                    event.getAmount(),
                    event.getPaymentDate(),
                    event.getPaymentMethod()
            );
            payment.setCompleted(true);

            paymentService.createPayment(payment);
            logger.info("Successfully processed PaymentReceived event for payment: " + event.getPaymentId());
        } catch (Exception e) {
            logger.severe("Error processing PaymentReceived event: " + e.getMessage());
            throw e;
        }
    }
}