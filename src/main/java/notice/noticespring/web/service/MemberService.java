package notice.noticespring.web.service;


import notice.noticespring.web.domain.Member;
import notice.noticespring.web.repository.SpringDataJpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class MemberService {

    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;

    @Autowired
    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
        this.springDataJpaMemberRepository = springDataJpaMemberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member)
    {
        //같은 폰번호 있으면 안된다
        validateDuplicateMember(member); //중복회원 검증


        springDataJpaMemberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        springDataJpaMemberRepository.findByphonenum(member.getPhonenum())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 폰번호입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findmembers(){
        return springDataJpaMemberRepository.findAll();
    }



    public List<Member> findFiles(){
        return springDataJpaMemberRepository.findAll();
    }
    /**
     * id별 회원 조회
     */
    public Optional<Member> findOne(Long memberId)
    {
        return springDataJpaMemberRepository.findByid(memberId);
    }

    public Member findByphonenum(int phonenum)
    {
        return springDataJpaMemberRepository.findByphonenum(phonenum).get();
    }

    public Member findByname(String name){
        return springDataJpaMemberRepository.findByname(name).get();
    }
    public Boolean matchingName(Member member)
    {
        Boolean temp = false;


        temp = springDataJpaMemberRepository.findByname(member.getName())
                .isPresent() == true;


        return temp;
    }

    public Boolean matchingName(String name)
    {
        Boolean temp = false;


        temp = springDataJpaMemberRepository.findByname(name)
                .isPresent() == true;


        return temp;
    }

    public Boolean matchingPhonenum(Member member)
    {
        Boolean temp = false;


        temp = springDataJpaMemberRepository.findByphonenum(member.getPhonenum())
                .isPresent() == true;


        return temp;
    }

    public Boolean matchingPhonenum(String getPhonenum)
    {
        Boolean temp = false;


        try {
            temp = springDataJpaMemberRepository.findByphonenum(Integer.parseInt(getPhonenum))
                    .isPresent() == true;
        } catch (Exception e) {

        }



        return temp;
    }

    public void delete(long id){
        springDataJpaMemberRepository.deleteById(id);
    }
}