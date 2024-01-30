package notice.noticespring.app.repository;

import java.util.List;
import notice.noticespring.app.domain.Report;
import notice.noticespring.app.dto.ReportDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report save(Report report);

    Optional<Report> findBytitle(String title);

    Optional<Report> findBydate(String date);

    List<Report> findBystate(String state);


}
