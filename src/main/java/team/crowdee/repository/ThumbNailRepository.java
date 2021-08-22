package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.ThumbNail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThumbNailRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(ThumbNail thumbNail) {
        em.persist(thumbNail);
        return thumbNail.getThumbNailId();
    }

    public List<ThumbNail> findAll() {
        return em.createQuery("select t from ThumbNail t join fetch t.funding", ThumbNail.class)
                .getResultList();
    }







}
