package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Detail;
import team.crowdee.domain.FundingPlan;
import team.crowdee.domain.ThumbNail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundingCompRepository {

    @PersistenceContext
    private final EntityManager em;

    public void saveThumbNail(ThumbNail thumbNail) {
        em.persist(thumbNail);
    }
    public void saveFundingPlan(FundingPlan fundingPlan) {
        em.persist(fundingPlan);
    }
    public void saveDetail(Detail detail) {
        em.persist(detail);
    }

    public List<ThumbNail> findAllThumbNail() {
        return em.createQuery
                ("select t from ThumbNail t join fetch t.funding f join fetch f.fundingStatus", ThumbNail.class)
                .getResultList();
    }
}
