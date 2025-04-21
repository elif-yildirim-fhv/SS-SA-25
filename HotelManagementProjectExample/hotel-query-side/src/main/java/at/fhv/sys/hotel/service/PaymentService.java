package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.PaymentQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentService {

    @Transactional
    public void createPayment(PaymentQueryModel payment) {
        payment.persist();
    }

    public List<PaymentQueryModel> getAllPayments() {
        return PaymentQueryModel.listAll();
    }

    public PaymentQueryModel getPaymentById(String paymentId) {
        return PaymentQueryModel.find("paymentId", paymentId).firstResult();
    }

    public List<PaymentQueryModel> getPaymentsByBookingId(String bookingId) {
        return PaymentQueryModel.find("bookingId", bookingId).list();
    }

    public List<PaymentQueryModel> getPaymentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return PaymentQueryModel.find("paymentDate >= ?1 and paymentDate <= ?2", start, end).list();
    }

    public void updatePayment(PaymentQueryModel payment) {
        payment.persist();
    }

    public void deletePayment(String paymentId) {
        PaymentQueryModel.find("paymentId", paymentId).firstResult().delete();
    }

    public boolean paymentExists(String paymentId) {
        return PaymentQueryModel.find("paymentId", paymentId).count() > 0;
    }
} 