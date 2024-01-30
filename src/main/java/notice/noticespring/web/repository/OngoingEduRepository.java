package notice.noticespring.web.repository;

import notice.noticespring.web.domain.OngoingEdu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OngoingEduRepository extends JpaRepository<OngoingEdu, Long> {

    OngoingEdu save(OngoingEdu ongoingEdu);

    //Optional<OngoingEdu> findByeduid(long eduid);

    Optional<OngoingEdu> findByeduid(long eduid);
}
