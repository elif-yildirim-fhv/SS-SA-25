package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentServicePanache {

    @Transactional
    public void createPayment(PaymentQueryPanacheModel payment) {
        payment.persist();
    }

    @Transactional
    public void updatePayment(PaymentQueryPanacheModel payment) {
        PaymentQueryPanacheModel existingPayment = PaymentQueryPanacheModel.findById(payment.paymentId);
        if (existingPayment != null) {
            existingPayment.amount = payment.amount;
            existingPayment.paymentMethod = payment.paymentMethod;
            existingPayment.paymentDate = payment.paymentDate;
            existingPayment.isCompleted = payment.isCompleted;
            existingPayment.persist();
        }
    }

    @Transactional
    public void deleteAll() {
        PaymentQueryPanacheModel.deleteAll();
    }

    public List<PaymentQueryPanacheModel> findByBookingId(String bookingId) {
        return PaymentQueryPanacheModel.find("bookingId", bookingId).list();
    }

}