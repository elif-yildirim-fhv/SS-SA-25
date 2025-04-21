package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentServicePanache {

    @Inject
    EntityManager entityManager;

    public List<PaymentQueryPanacheModel> getAllPayments() {
        return PaymentQueryPanacheModel.listAll();
    }

    @Transactional
    public void createPayment(PaymentQueryPanacheModel payment) {
        payment.persist();
    }

    public PaymentQueryPanacheModel getPaymentById(String paymentId) {
        return PaymentQueryPanacheModel.findByPaymentId(paymentId);
    }

    public List<PaymentQueryPanacheModel> getPaymentsByBookingId(String bookingId) {
        return PaymentQueryPanacheModel.findByBookingId(bookingId);
    }

    public List<PaymentQueryPanacheModel> getPaymentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return PaymentQueryPanacheModel.findByDateRange(start, end);
    }

    public List<PaymentQueryPanacheModel> getPaymentsByMethod(String paymentMethod) {
        return PaymentQueryPanacheModel.findByPaymentMethod(paymentMethod);
    }

    @Transactional
    public void updatePayment(PaymentQueryPanacheModel payment) {
        PaymentQueryPanacheModel existingPayment = PaymentQueryPanacheModel.findByPaymentId(payment.paymentId);
        if (payment != null) {
            existingPayment.paymentMethod = payment.paymentMethod;
            existingPayment.paymentDate = payment.paymentDate;
            existingPayment.amount = payment.amount;
            existingPayment.isCompleted = payment.isCompleted;
            existingPayment.persist();
        }
    }

    @Transactional
    public void deletePayment(String paymentId) {
        PaymentQueryPanacheModel payment = PaymentQueryPanacheModel.findByPaymentId(paymentId);
        if (payment != null) {
            payment.delete();
        }
    }

    public boolean paymentExists(String paymentId) {
        return PaymentQueryPanacheModel.findByPaymentId(paymentId) != null;
    }
} 