package notice.noticespring.app.service;

import notice.noticespring.app.domain.Report;
import notice.noticespring.app.dto.ReportDetailDto;
import notice.noticespring.app.repository.ReportRepository;
import notice.noticespring.web.domain.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Printable;
import java.util.List;
import java.util.Optional;

@Transactional
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * 파일업로드
     */
    public Report join(Report report)
    {
        //같은 폰번호 있으면 안된다
        //validateDuplicateMember(report); //중복회원 검증
        reportRepository.save(report);


//        print(report.getTitle());
//        String temp = report.getTitle();
        return report;

    }

    /**
     * 중복 테스트
     * @param edu
     */
    private void validateDuplicateMember(Education edu) {
        reportRepository.findBytitle(edu.getTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 보고서 명입니다.");
                });
    }

    public Optional<Report> findBytitle(String title){
        return reportRepository.findBytitle(title);
    }

    public List<Report> findBystate(String state){
        return reportRepository.findBystate(state);
    }

    public List<Report> findReportlist(){
        return reportRepository.findAll();
    }

    public Optional<Report> findByid(long id){ return reportRepository.findById(id);}


}
