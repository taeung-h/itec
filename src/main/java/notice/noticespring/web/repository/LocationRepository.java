package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location save(Location location);

    Optional<Location> findByname(String name);

    Location findByid(Long id);
}
