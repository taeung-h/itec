package notice.noticespring.web.service;

import notice.noticespring.web.domain.OngoingEdu;
import notice.noticespring.web.repository.OngoingEduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class OngoingEduservice {
    private final OngoingEduRepository ongoingEduRepository;

    @Autowired
    public OngoingEduservice(OngoingEduRepository ongoingEduRepository) {
        this.ongoingEduRepository = ongoingEduRepository;
    }

    public void join(OngoingEdu ongoionedu)
    {
        //같은 폰번호 있으면 안된다
        validateDuplicateMember(ongoionedu); //중복회원 검증
        ongoingEduRepository.save(ongoionedu);

    }

    private void validateDuplicateMember(OngoingEdu ongoionedu) {
        ongoingEduRepository.findByeduid(ongoionedu.getEduid())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 진행중인 교육입니다.");
                });
    }


    public List<OngoingEdu> findOngoingEdulist(){
        return ongoingEduRepository.findAll();
    }


    public Optional<OngoingEdu> findByeduid(Long eduid)
    {

        return ongoingEduRepository.findByeduid(eduid);
    }


    public void delete(long id){
        ongoingEduRepository.deleteById(id);
    }

}
