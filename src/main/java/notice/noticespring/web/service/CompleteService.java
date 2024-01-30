package notice.noticespring.web.service;

import notice.noticespring.web.domain.CompletedMember;
import notice.noticespring.web.repository.CompleteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompleteService {
    private final CompleteRepository completeRepository;

    @Autowired
    public CompleteService(CompleteRepository completeRepository) {
        this.completeRepository = completeRepository;
    }

    public void join(CompletedMember completedMember)
    {
        //같은 폰번호 있으면 안된다
        validateDuplicateMember(completedMember); //중복회원 검증

        Date date = new Date();
        SimpleDateFormat SDF = new SimpleDateFormat("yy-MM-dd");
        String dateString = SDF.format(date);

        completedMember.setDate(dateString);
        completeRepository.save(completedMember);

    }

    private void validateDuplicateMember(CompletedMember completedMember) {


        List<CompletedMember> memberList = completeRepository.findByeduid(completedMember.getEduid());
        for (CompletedMember member : memberList) {
            if (member.getMemberid() == completedMember.getMemberid()) {
                throw new IllegalStateException("이미 존재하는 데이터입니다.");
            }
        }






    }


    public List<CompletedMember> findOngoingEdulist(){
        return completeRepository.findAll();
    }


    public List<CompletedMember> findByeduid(Long eduId)
    {
        return completeRepository.findByeduid(eduId);
    }

    public CompletedMember findByeduidAndmemberid(Long eduId, Long memberid)
    {
        CompletedMember completedMember = new CompletedMember();

        if (completeRepository.findByeduid(eduId).size() != 0 )
        {
            List<CompletedMember> memberList = completeRepository.findByeduid(eduId);
            for (CompletedMember member : memberList) {
                if (member.getMemberid() == memberid) {
                    completedMember = member;
                    return completedMember;
                }

            }
        }


        return completedMember;
    }







}
