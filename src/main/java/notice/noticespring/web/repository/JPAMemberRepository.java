package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JPAMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JPAMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findByid(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByname(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByphonenum(int phonenum) {
        List<Member> result = em.createQuery("select m from Member m where m.phonenum = :phonenum", Member.class).setParameter("phonenum", phonenum).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member M", Member.class).getResultList();
    }
}
