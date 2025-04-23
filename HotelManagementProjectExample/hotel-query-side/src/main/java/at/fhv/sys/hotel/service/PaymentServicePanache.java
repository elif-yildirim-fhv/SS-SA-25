package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import at.fhv.sys.hotel.models.PaymentStatistics;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PaymentServicePanache {
    private static final Logger LOGGER = Logger.getLogger(PaymentServicePanache.class.getName());

    @Inject
    EntityManager entityManager;

    @Transactional
    public void createPayment(PaymentQueryPanacheModel payment) {
        payment.persist();
        updatePaymentStatistics(payment);
    }

    @Transactional
    public void updatePayment(PaymentQueryPanacheModel payment) {
        payment.persist();
        updatePaymentStatistics(payment);
    }

    @Transactional
    public void deleteAll() {
        PaymentQueryPanacheModel.deleteAll();
    }

    public PaymentQueryPanacheModel findById(String paymentId) {
        return PaymentQueryPanacheModel.find("paymentId", paymentId).firstResult();
    }

    public List<PaymentQueryPanacheModel> findAll() {
        return PaymentQueryPanacheModel.listAll();
    }

    public List<PaymentQueryPanacheModel> findByBookingId(String bookingId) {
        return PaymentQueryPanacheModel.find("bookingId", bookingId).list();
    }

    public List<PaymentQueryPanacheModel> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return PaymentQueryPanacheModel.find(
            "paymentDate >= ?1 and paymentDate <= ?2",
            startDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1)
        ).list();
    }

    public List<PaymentQueryPanacheModel> findByPaymentMethod(String paymentMethod) {
        return PaymentQueryPanacheModel.find("paymentMethod", paymentMethod).list();
    }

    public double calculateTotalPaymentsForBooking(String bookingId) {
        return PaymentQueryPanacheModel.<PaymentQueryPanacheModel>find("bookingId", bookingId)
            .stream()
            .mapToDouble(p -> p.amount)
            .sum();
    }

    public List<PaymentQueryPanacheModel> findCompletedPayments() {
        return PaymentQueryPanacheModel.find("isCompleted", true).list();
    }

    public List<PaymentQueryPanacheModel> findPendingPayments() {
        return PaymentQueryPanacheModel.find("isCompleted", false).list();
    }

    @Transactional
    public void updatePaymentStatistics(PaymentQueryPanacheModel payment) {
        PaymentStatistics stats = PaymentStatistics.findByBookingId(payment.bookingId);
        if (stats == null) {
            stats = new PaymentStatistics();
            stats.setBookingId(payment.bookingId);
        }
        stats.setTotalAmount(stats.getTotalAmount() + payment.amount);
        stats.setPaymentCount(stats.getPaymentCount() + 1);
        stats.setLastPaymentDate(payment.paymentDate.toLocalDate());
        stats.persist();
    }

    public double getTotalPaymentsForPeriod(LocalDate startDate, LocalDate endDate) {
        return PaymentQueryPanacheModel.<PaymentQueryPanacheModel>find(
            "paymentDate >= ?1 and paymentDate <= ?2",
            startDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1)
        )
        .stream()
        .mapToDouble(p -> p.amount)
        .sum();
    }

    public List<PaymentQueryPanacheModel> findPaymentsAboveAmount(double amount) {
        return PaymentQueryPanacheModel.find("amount > ?1", amount).list();
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
    public void deletePayment(String paymentId) {
        PaymentQueryPanacheModel.find("paymentId", paymentId).firstResult().delete();
    }

    @Transactional
    public boolean paymentExists(String paymentId) {
        return PaymentQueryPanacheModel.find("paymentId", paymentId).count() > 0;
    }
} 