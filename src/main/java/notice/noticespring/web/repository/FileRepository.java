package notice.noticespring.web.repository;

import notice.noticespring.web.domain.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<UserFile, Long>{

    UserFile save(UserFile file);

    Optional<UserFile> findByfilename(String filename);

    List<UserFile> findByeduid(long eduid);

    UserFile findByid(long id);


}
