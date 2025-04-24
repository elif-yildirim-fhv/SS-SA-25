package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.PaymentQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PaymentService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void createPayment(PaymentQueryModel payment) {
        entityManager.persist(payment);
    }


    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM PaymentQueryModel").executeUpdate();
    }
}