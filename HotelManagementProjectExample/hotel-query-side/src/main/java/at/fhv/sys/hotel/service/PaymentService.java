package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.models.PaymentQueryModel;
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
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());

    @Inject
    EntityManager entityManager;

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

    public PaymentQueryModel findById(String paymentId) {
        return entityManager.find(PaymentQueryModel.class, paymentId);
    }

    public List<PaymentQueryModel> findAll() {
        return entityManager.createQuery("SELECT p FROM PaymentQueryModel p", PaymentQueryModel.class)
            .getResultList();
    }

    public List<PaymentQueryModel> findByBookingId(String bookingId) {
        return entityManager.createQuery(
            "SELECT p FROM PaymentQueryModel p WHERE p.bookingId = :bookingId", 
            PaymentQueryModel.class)
            .setParameter("bookingId", bookingId)
            .getResultList();
    }

    public List<PaymentQueryModel> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
            "SELECT p FROM PaymentQueryModel p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate", 
            PaymentQueryModel.class)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getResultList();
    }

    public List<PaymentQueryModel> findByPaymentMethod(String paymentMethod) {
        return entityManager.createQuery(
            "SELECT p FROM PaymentQueryModel p WHERE p.paymentMethod = :paymentMethod", 
            PaymentQueryModel.class)
            .setParameter("paymentMethod", paymentMethod)
            .getResultList();
    }

    public double calculateTotalPaymentsForBookingId(String bookingId) {
        Double total = entityManager.createQuery(
            "SELECT SUM(p.amount) FROM PaymentQueryModel p WHERE p.bookingId = :bookingId", 
            Double.class)
            .setParameter("bookingId", bookingId)
            .getSingleResult();
        return total != null ? total : 0.0;
    }

    public List<PaymentQueryModel> findCompletedPayments() {
        return entityManager.createQuery(
            "SELECT p FROM PaymentQueryModel p WHERE p.completed = true", 
            PaymentQueryModel.class)
            .getResultList();
    }

    public List<PaymentQueryModel> findPendingPayments() {
        return entityManager.createQuery(
            "SELECT p FROM PaymentQueryModel p WHERE p.completed = false", 
            PaymentQueryModel.class)
            .getResultList();
    }

    public double getTotalPaymentsForPeriod(LocalDate startDate, LocalDate endDate) {
        Double total = entityManager.createQuery(
            "SELECT SUM(p.amount) FROM PaymentQueryModel p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate", 
            Double.class)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getSingleResult();
        return total != null ? total : 0.0;
    }

    public List<PaymentQueryModel> findPaymentsAboveAmount(double amount) {
        return entityManager.createQuery(
            "SELECT p FROM PaymentQueryModel p WHERE p.amount > :amount", 
            PaymentQueryModel.class)
            .setParameter("amount", amount)
            .getResultList();
    }

    @Transactional
    public void createPayment(PaymentQueryModel payment) {
        entityManager.persist(payment);
        updatePaymentStatistics(payment);
    }

    @Transactional
    public void updatePayment(PaymentQueryModel payment) {
        entityManager.merge(payment);
        updatePaymentStatistics(payment);
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM PaymentQueryModel").executeUpdate();
        entityManager.createQuery("DELETE FROM PaymentStatistics").executeUpdate();
    }

    @Transactional
    public void updatePaymentStatistics(PaymentQueryModel payment) {
        PaymentStatistics stats = PaymentStatistics.findByBookingId(payment.getBookingId());
        if (stats == null) {
            stats = new PaymentStatistics();
            stats.setBookingId(payment.getBookingId());
        }
        stats.setTotalAmount(stats.getTotalAmount() + payment.getAmount());
        stats.setPaymentCount(stats.getPaymentCount() + 1);
        stats.setLastPaymentDate(payment.getPaymentDate());
        entityManager.merge(stats);
    }
} 