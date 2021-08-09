package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Follow;
import team.crowdee.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.management.LockInfo;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);//hhghg
        return member.getMemberId();
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findMemberWithFollow(Long id) {
        return em.createQuery("select m from Member m join fetch m.following", Member.class).getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m join fetch m.orders",Member.class).getResultList();
    }

    public Long saveCreator(Creator creator) {
        em.persist(creator);
        return creator.getCreatorId();
    }

    public Creator findCreator(Long id) {
        return em.find(Creator.class, id);
    }

    public Long saveFollow(Follow follow) {
        em.persist(follow);
        return follow.getFollowId();
    }

}
