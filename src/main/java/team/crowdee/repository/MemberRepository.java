package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Authority;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Member;
import team.crowdee.domain.Status;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getMemberId();
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findByParam(String target,String param) {
        String query = "select m from Member m where m."+ target +"=:param";
        return em.createQuery(query, Member.class)
                .setParameter("param", param)
                .getResultList();

    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Member> findByStatus(Status status) {
        return em.createQuery("select m from Member m where m.status=:status", Member.class)
                .setParameter("status", status)
                .getResultList();
    }

    public Long saveCreator(Creator creator) {
        em.persist(creator);
        return creator.getCreatorId();
    }


    @EntityGraph(attributePaths = "authorities")
    public List<Member> findOneWithAuthoritiesByEmail(String email) {
        return em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Member> findByEmailWithFunding(String email) {
        return em.createQuery("select m from Member m " +
                "left join fetch m.orders o " +
                "left join fetch o.funding f " +
                "where m.email=:param ", Member.class)
                .setParameter("param",email)
                .getResultList();
    }


    //어드민로그인을위해 로직 추가
    public Member findAdmin(String email) {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setAuthorityName("admin");
        authorities.add(authority);
        List<Member> findAdmin = em.createQuery("select m from Member m where m.email=:param", Member.class)
                .setParameter("param", email)
                .getResultList();
        if (findAdmin.isEmpty()) {
            return null;
        }
        return findAdmin.get(0);
    }

    public List<Member> findByEmailWithOrderAndFunding(String email) {
        return em.createQuery("select m from Member m " +
                "join fetch m.orders o " +
                "join fetch o.funding f " +
                "join fetch o.payment p " +
                "where m.email=:param " +
                "and f.status='end' " +
                "and f.result=true " +
                "and o.payment.imp_uid='none' ", Member.class)
                .setParameter("param", email)
                .getResultList();

    }
}