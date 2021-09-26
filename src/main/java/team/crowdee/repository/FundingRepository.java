package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Status;

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
        String query = "select f from Funding f " +
                "left join fetch f.orders " +
                "where f." + target + "=:param ";
        return em.createQuery(query, Funding.class)
                .setParameter("param", param)
                .getResultList();
    }

    public List<Funding> findByLikeParam(String target,String param) {
        String query = "select f from Funding f " +
                "left join fetch f.orders " +
                "where f."+target+" "+
                "like '%" + param + "%' " +
                "and f.status='progress'";
        return em.createQuery(query, Funding.class).getResultList();
    }

    public List<Funding> findBySearchValue(String searchValue) {
        String query = "select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "and (f.title like '%"+searchValue+"%' " +
                "or f.category like '%"+searchValue+"%' " +
                "or f.tag like  '%"+searchValue+"%'  )" +
                "order by f.startDate desc";
        return em.createQuery(query, Funding.class).getResultList();
    }

    public List<Funding> findByUrl(String projectUrl) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.creator o " +
                "left join fetch o.member " +
//                "left join fetch o.fundingList " +
                "left join fetch f.orders " +
                "where f.projectUrl =:projectUrl", Funding.class)
                .setParameter("projectUrl", projectUrl)
                .getResultList();
    }

    public List<Funding> findNewFunding(int max) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "order by f.startDate " +
                "desc", Funding.class)
                .setFirstResult(0)
                .setMaxResults(max)
                .getResultList();
    }

    public List<Funding> findVergeOfSuccess(int max) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "and f.rateOfAchievement < 100 " +
                "order by f.rateOfAchievement " +
                "desc", Funding.class)
                .setFirstResult(0)
                .setMaxResults(max)
                .getResultList();
    }

    public List<Funding> findExcessFunding(int max) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "and f.rateOfAchievement >= 100 " +
                "and f.restTicket > 0 " +
                "order by f.rateOfAchievement " +
                "desc", Funding.class)
                .setFirstResult(0)
                .setMaxResults(max)
                .getResultList();
    }

    public List<Funding> findPopularFunding(int max) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "order by f.visitCount " +
                "desc", Funding.class)
                .setFirstResult(0)
                .setMaxResults(max)
                .getResultList();
    }

    public List<Funding> findOutOfStock(int max) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders " +
                "where f.status='progress' " +
                "and f.restTicket < 5 " +
                "and f.restTicket > 0 " +
                "order by f.orders.size " +
                "desc", Funding.class)
                .setFirstResult(0)
                .setMaxResults(max)
                .getResultList();
    }

    public List<Funding> findByStatus(Status status) {
        return em.createQuery("select f from Funding f where f.status=:status", Funding.class)
                .setParameter("status", status)
                .getResultList();
    }
    public List<Funding> findConfirmOrProgress(Status confirm,Status progress) {
        return em.createQuery("select distinct f from Funding f " +
                "left join fetch f.orders o " +
                "left join fetch o.member m " +
                "where f.status in(:confirm, :progress) ", Funding.class)
                .setParameter("confirm", confirm)
                .setParameter("progress",progress)
                .getResultList();
    }

    public List<Funding> findEarlySuccess(boolean sendMail, Status end) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders o " +
                "left join fetch o.member m " +
                "where f.status =:end " +
                "and f.sendMail =:sendMail ", Funding.class)
                .setParameter("end", end)
                .setParameter("sendMail",sendMail)
                .getResultList();
    }


    public List<Funding> findByCreatorForIntroduce(Long creatorId) {
        return em.createQuery("select f from Funding f " +
                "where f.status='progress' " +
                "and f.creator.creatorId=:creatorId " +
                "order by f.rateOfAchievement desc", Funding.class)
                .setParameter("creatorId", creatorId)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }
    public List<Funding> findByCreatorForPreview(Long creatorId) {
        return em.createQuery("select f from Funding f " +
                "where f.status='progress' " +
                "and f.creator.creatorId=:creatorId " +
                "order by f.rateOfAchievement desc", Funding.class)
                .setParameter("creatorId", creatorId)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }

    public List<Funding> findByCreatorForEditing(Creator creator) {
       return em.createQuery("select f from Funding f " +

                "where f.status='editing' " +
                "and f.creator=:creator " +
                "order by f.postDate desc", Funding.class)
                .setParameter("creator",creator)
                .getResultList();
    }

    public List<Funding> findWithOrdersAndMember(Long fundingId) {
        return em.createQuery("select f from Funding f " +
                "left join fetch f.orders o " +
                "left join fetch o.member " +
                "where f.fundingId=:fundingId", Funding.class)
                .setParameter("fundingId", fundingId)
                .getResultList();

    }



}