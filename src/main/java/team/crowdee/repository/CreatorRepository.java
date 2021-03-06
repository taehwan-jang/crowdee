package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CreatorRepository {
    @PersistenceContext
    private final EntityManager em;

    public Long save(Creator creator) {
        em.persist(creator);
        return creator.getCreatorId();
    }
    public List<Creator> findByIdMember(Long id) {
        return em.createQuery("select c from Creator c join fetch c.member where c.creatorId=:param", Creator.class)
                .setParameter("param",id)
                .getResultList();
    }

    public List<Creator> findByEmail(String email) {
        return em.createQuery("select c from Creator c join fetch c.member where c.member.email=:email", Creator.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Creator> findByNickName(String nickName) {
        return em.createQuery("select c from Creator c where c.creatorNickName=:param", Creator.class)
                .setParameter("param",nickName)
                .getResultList();
    }

    public List<Creator> findByStatus(Status status) {
        return em.createQuery("select c from Creator c where c.status=:status", Creator.class)
                .setParameter("status", status)
                .getResultList();
    }

}