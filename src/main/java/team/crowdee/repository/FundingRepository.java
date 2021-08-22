package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Funding;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundingRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Funding funding) {
        em.persist(funding);
        return funding.getFundingId();
    }

    public Funding findById(Long fundingId) {
        return em.find(Funding.class, fundingId);
    }

    public List<Funding> findAll() {
        return em.createQuery("select f from Funding f", Funding.class).getResultList();
    }

    public List<Funding> findToThumbNail() {
        return em.createQuery("select f from Funding f join fetch f.status", Funding.class).getResultList();
    }

    public List<Funding> findByParam(String target, String param) {
        String query = "select f from Funding f where f."+target+"=:param";
        return em.createQuery(query, Funding.class)
                .setParameter("param", param)
                .getResultList();
    }

    public List<Funding> findByTag(String tag) {
        String query = "select f from Funding f where f.tag like %"+tag+"%";
        return em.createQuery(query, Funding.class).getResultList();
    }
}
