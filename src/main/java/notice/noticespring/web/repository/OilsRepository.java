package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Oils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OilsRepository extends JpaRepository<Oils, Long> {

    Oils save(Oils oils);

    Oils findByid(Long id);

    Optional<Oils> findByname(String name);
}
