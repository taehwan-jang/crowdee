package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.FundingPlan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class FundingPlanRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(FundingPlan fundingPlan) {
        em.persist(fundingPlan);
    }

}
