package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import at.fhv.sys.hotel.service.PaymentServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PaymentProjection {

    @Inject
    PaymentServicePanache paymentServicePanache;

    public void processPaymentCreatedEvent(PaymentReceived paymentCreatedEvent) {
        Logger.getAnonymousLogger().info("Processing event: " + paymentCreatedEvent);
        
        PaymentQueryPanacheModel payment = new PaymentQueryPanacheModel();
        payment.paymentId = paymentCreatedEvent.getPaymentId();
        payment.bookingId = paymentCreatedEvent.getBookingId();
        payment.amount = paymentCreatedEvent.getAmount();
        payment.paymentDate = paymentCreatedEvent.getPaymentDate();
        payment.paymentMethod = paymentCreatedEvent.getPaymentMethod();
        payment.isCompleted = false;
        
        paymentServicePanache.createPayment(payment);
    }

    public PaymentQueryPanacheModel getPaymentById(String paymentId) {
        return PaymentQueryPanacheModel.findByPaymentId(paymentId);
    }

    public List<PaymentQueryPanacheModel> getPaymentsByBookingId(String bookingId) {
        return PaymentQueryPanacheModel.findByBookingId(bookingId);
    }

    public List<PaymentQueryPanacheModel> getAllPayments() {
        return PaymentQueryPanacheModel.listAll();
    }

    public List<PaymentQueryPanacheModel> getPaymentsByMethod(String paymentMethod) {
        return PaymentQueryPanacheModel.findByPaymentMethod(paymentMethod);
    }
}
