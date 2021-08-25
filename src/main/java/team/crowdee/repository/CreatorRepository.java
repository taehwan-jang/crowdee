package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Creator;

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
    public Creator findById(Long id) {
        return em.find(Creator.class, id);
    }

    public List<Creator> findByEmail(String email) {
        return em.createQuery("select c from Creator c join fetch c.member where c.member.email=:email", Creator.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Creator> findAll() {
        return em.createQuery("select c from Creator c", Creator.class).getResultList();
    }



}
