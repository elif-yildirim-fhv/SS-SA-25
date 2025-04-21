package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.models.PaymentQueryModel;
import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentService {

    @Transactional
    public void createPayment(PaymentQueryPanacheModel payment) {
        payment.persist();
    }

    public List<PaymentQueryPanacheModel> getAllPayments() {
        return PaymentQueryPanacheModel.listAll();
    }

    public PaymentQueryPanacheModel getPaymentById(String paymentId) {
        return PaymentQueryPanacheModel.find("paymentId", paymentId).firstResult();
    }

    public List<PaymentQueryPanacheModel> getPaymentsByBookingId(String bookingId) {
        return PaymentQueryPanacheModel.find("bookingId", bookingId).list();
    }

    public List<PaymentQueryPanacheModel> getPaymentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return PaymentQueryPanacheModel.find("paymentDate >= ?1 and paymentDate <= ?2", start, end).list();
    }

    @Transactional
    public void updatePayment(PaymentQueryPanacheModel payment) {
        payment.persist();
    }

    @Transactional
    public void deletePayment(String paymentId) {
        PaymentQueryPanacheModel.find("paymentId", paymentId).firstResult().delete();
    }

    @Transactional
    public boolean paymentExists(String paymentId) {
        return PaymentQueryPanacheModel.find("paymentId", paymentId).count() > 0;
    }
} 