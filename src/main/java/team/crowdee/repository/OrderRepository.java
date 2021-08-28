package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Payment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {

    @PersistenceContext
    private final EntityManager em;

    public void paymentSave(Payment payment) {
        em.persist(payment);

    }
}
