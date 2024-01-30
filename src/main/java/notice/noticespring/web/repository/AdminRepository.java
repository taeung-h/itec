package notice.noticespring.web.repository;

import notice.noticespring.web.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin save(Admin admin);

    Optional<Admin> findByname(String name);
}
