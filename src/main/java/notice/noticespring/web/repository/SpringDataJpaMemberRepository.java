package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    Member save(Member member);

    @Override
    Optional<Member> findByname(String name);

    Optional<Member> findByposition(String position);
    @Override
    Optional<Member> findByphonenum(int phonenum);


}
