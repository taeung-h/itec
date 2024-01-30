package notice.noticespring.web.repository;

import notice.noticespring.web.domain.CompletedMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteRepository extends JpaRepository<CompletedMember, Long> {

    CompletedMember save(CompletedMember completedMember);


    List<CompletedMember> findBymemberid(long memberid);
    List<CompletedMember> findByeduid(long eduid);



}

