package notice.noticespring.web.service;


import notice.noticespring.web.domain.Oils;
import notice.noticespring.web.repository.OilsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class OilsService {
    private final OilsRepository oilsRepository;

    @Autowired
    public OilsService(OilsRepository oilsRepository){
        this.oilsRepository = oilsRepository;}

    public Long join(Oils oils) {

        validateDuplicateMember(oils);
        oilsRepository.save(oils);
        return oils.getId();

    }

    public Long modify(Oils oils)
    {

        oilsRepository.findById(oils.getId())
                .ifPresent(m -> {
                    oilsRepository.save(oils);
                });
        return oils.getId();

    }

    public Oils findOne(Long id)
    {
        return oilsRepository.findByid(id);
    }

    public List<Oils> findOillist(){return oilsRepository.findAll();}


    /**
     * 중복 테스트
     * @param oils
     */
    private void validateDuplicateMember(Oils oils) {
        oilsRepository.findByname(oils.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 기름명입니다.");
                });
    }


}
