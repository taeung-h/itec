package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EduRepository extends JpaRepository<Education, Long>{

    Education save(Education edu);

    Optional<Education> findBytitle(String title);

    Optional<Education> findBymonth(String month);

    Optional<Education> findByyear(String year);

    List<Education> findBystate(String state);



}
