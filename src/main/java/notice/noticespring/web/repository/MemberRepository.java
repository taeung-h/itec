package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByid(Long id);
    Optional<Member> findByname(String name);
    Optional<Member> findByphonenum(int phonenum);
    List<Member> findAll();

}