package notice.noticespring.web.service;


import notice.noticespring.web.domain.Education;
import notice.noticespring.web.repository.EduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class Eduservice {

    private final EduRepository eduRepository;

    @Autowired
    public Eduservice(EduRepository eduRepository) {
        this.eduRepository = eduRepository;
    }


    /**
     * 파일업로드
     */
    public Long join(Education edu)
    {
        //같은 폰번호 있으면 안된다
        validateDuplicateMember(edu); //중복회원 검증
        eduRepository.save(edu);
        return edu.getId();

    }

    public Long modify(Education edu)
    {

        eduRepository.findById(edu.getId())
                .ifPresent(m -> {
                    eduRepository.save(edu);
                });
        return edu.getId();

    }



    /**
     * 중복 테스트
     * @param edu
     */
    private void validateDuplicateMember(Education edu) {
        eduRepository.findBytitle(edu.getTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 교육명입니다.");
                });
    }


    public Optional<Education> findBytitle(String title){
        return eduRepository.findBytitle(title);
    }

    public List<Education> findBystate(String state){
        return eduRepository.findBystate(state);
    }

    public List<Education> findEdulist(){
        return eduRepository.findAll();
    }

    public Page<Education> findEdulist(Pageable pageable){
        return eduRepository.findAll(pageable);
    }

    /**
     * id별 회원 조회
     */
    public Optional<Education> findByeduid(Long fileId)
    {
        return eduRepository.findById(fileId);
    }



    public Education eduView(long id){
        return eduRepository.findById(id).get();
    }


    public void delete(long id){
        eduRepository.deleteById(id);
    }

}
