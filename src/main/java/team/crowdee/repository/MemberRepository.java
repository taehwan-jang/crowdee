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

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Member login(String email) {
        List<Member> resultList = em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email",email)
                .getResultList();
        System.out.println(resultList);
        if (resultList.isEmpty()) {

            return null;
        }
        return resultList.get(0);
    }

    public void delete(Member member) {
        /*
        Member deleteMember = findById(memberId);
        String userId = deleteMember.getUserId();
        em.createQuery("delete from Member m where m.userId=:userId")
                .setParameter("userId", userId);
         */
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

    public List<Member> findToConfirm(String email, String emailCert) {
        return em.createQuery("select m from Member m where m.email=:email and m.emailCert=:emailCert", Member.class)
                .setParameter("email",email)
                .setParameter("emailCert",emailCert)
                .getResultList();
    }

//    public List<Member> findMemberWithFollow(Long id) {
//        return em.createQuery("select m from Member m join fetch m.following", Member.class).getResultList();
//    }

//    public List<Member> findSecessionMember(LocalDateTime today) {
//        return em.createQuery("select m from Member m where m.secessionDate=:today", Member.class)
//                .setParameter("today", today)
//                .getResultList();
//    }

    public Long saveCreator(Creator creator) {
        em.persist(creator);
        return creator.getCreatorId();
    }

    public Creator findCreator(Long id) {
        return em.find(Creator.class, id);
    }

    public void flush() {
        em.flush();
    }

    @EntityGraph(attributePaths = "authorities")
    public List<Member> findOneWithAuthoritiesByEmail(String email) {
        return em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Member> findToInspection() {
        return em.createQuery("select m from Member m " +
                        "join fetch m.creator " +
                        "where m.status='inspection'",
                Member.class)
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

    public List<Member> findByEmailWithWishFunding(String email) {
        return em.createQuery("select m from Member m " +
                "left join fetch m.fundingList " +
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
}