package team.crowdee.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Follow;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


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

    public List<Member> findToConfirm(String email, String emailCert) {
        return em.createQuery("select m from Member m where m.email=:email and m.emailCert=:emailCert", Member.class)
                .setParameter("email",email)
                .setParameter("emailCert",emailCert)
                .getResultList();
    }

    public List<Member> findMemberWithFollow(Long id) {
        return em.createQuery("select m from Member m join fetch m.following", Member.class).getResultList();
    }

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

    public Long saveFollow(Follow follow) {
        em.persist(follow);
        return follow.getFollowId();
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


}
