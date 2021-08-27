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


    public List<Funding> findByParam(String target, String param) {
        String query = "select f from Funding f where f."+target+"=:param";
        return em.createQuery(query, Funding.class)
                .setParameter("param", param)
                .getResultList();
    }

    public List<Funding> findByTag(String tag) {
        String query = "select f from Funding f " +
                "left join fetch f.orders " +
                "where f.tag " +
                "like '%"+tag+"%' " +
                "and f.status='progress'";
        return em.createQuery(query, Funding.class).getResultList();
    }

    public List<Funding> findNewFunding() {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "order by f.startDate " +
                "desc",Funding.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }
    public List<Funding> findUnderFunding() {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "and f.rateOfAchievement < 100 " +
                "order by f.rateOfAchievement " +
                "desc",Funding.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }

    public List<Funding> findOverFunding() {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "and f.rateOfAchievement >= 100 " +
                "and f.orders.size < f.maxBacker " +
                "order by f.rateOfAchievement " +
                "desc",Funding.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }
    public List<Funding> findPopularFunding() {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "order by f.visitCount " +
                "desc",Funding.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }

    public List<Funding> findToInspection() {
        return em.createQuery("select f from Funding f " +
                "join fetch f.creator " +
                "where f.status='insepction'",
                Funding.class)
                .getResultList();
    }



}