package notice.noticespring.web.controller;

import notice.noticespring.web.component.PDFGeneratorUtil;
import notice.noticespring.web.domain.Admin;
import notice.noticespring.web.domain.CompletedMember;
import notice.noticespring.web.domain.Education;
import notice.noticespring.web.domain.Member;
import notice.noticespring.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;



@Controller
public class CompleteController {


    private final CompleteService completeService;
    private final Eduservice eduservice;
    private final MemberService memberService;
    private final PDFGeneratorUtil pdfGeneratorUtil;

    private final AdminService adminService;

    private final PDFService pdfService;


    @Autowired
    public CompleteController(CompleteService completeService, Eduservice eduservice, MemberService memberService, PDFGeneratorUtil pdfGeneratorUtil, AdminService adminService, PDFService pdfService) {
        this.completeService = completeService;
        this.eduservice = eduservice;
        this.memberService = memberService;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
        this.adminService = adminService;
        this.pdfService = pdfService;
    }


    //교육 수료 완료 버튼(monthedu.html)
    @GetMapping("/complete/sign")
    public ModelAndView educationSign(long eduid, Member member){
        CompletedMember comMember = new CompletedMember();
        ModelAndView mv = new ModelAndView();
        try{
            comMember.setEduid(eduid);
            comMember.setMemberid(member.getId());
            completeService.join(comMember);
            mv.addObject("result","완료");
            mv.setViewName("forward:/education/list");
        }catch  (IllegalStateException e) {
            mv.addObject("alert","에러발생 혹은 이미수료한 교육입니다.");
            mv.setViewName("forward:/education/monthedulist");
        }

        mv.addObject("member", member);

        return mv;
    }



    //관리자 메인화면에서 출력 리스트 클릭 시 (admin.html)
    @GetMapping("/complete/signpaperlist")
    public String signpaperlist(Model model, Admin admin) {
        List<Education> edulist = eduservice.findBystate("end");

        model.addAttribute("list", edulist);
        model.addAttribute("adminNow", admin);
        return "complete/completeedulist";
    }



    //출력 리스트 중 하나 선택 시 (completeedulist.html)
    @GetMapping("/complete/signpaper")
    public String printExcel(Model model, long eduid, String name) throws Exception {



        Education education = eduservice.findByeduid(eduid).get();

        List<CompletedMember> completedMembers = completeService.findByeduid(eduid);

        String outputFilePath = "/webserver/itec/result/"+ education.getYear()+"년/"+education.getMonth()
                +"월_"+ education.getTitle() + ".pdf";

        int count=1;

        List<Member> members = new ArrayList<>();

        for(CompletedMember member : completedMembers) {
            try {
                model.addAttribute("member"+ Integer.toString(count), memberService.findOne(member.getMemberid()).get());
                members.add(memberService.findOne(member.getMemberid()).get());
                count ++;

            } catch (IllegalStateException e) {

            }
        }

        model.addAttribute("edu",education);

        //추가 기능 구현 시 업데이트할 부분 - 현재는 그냥 고정형으로 사용중
        //강사명과 서명위치
        Member instructor = memberService.findByname(name);
        model.addAttribute("instructor", instructor.getName());
        model.addAttribute("instructorSignPath", instructor.getSignpath());

        //미참석인원수와 미참석사유내용
        model.addAttribute("nomembernumber", "-");
        model.addAttribute("nomembernumberreason", "-");

        ///파일 출력하려고 했던 흔적
//        if(!members.isEmpty())
//        {
//            pdfService.generateSignPdf(education, members,instructor, outputFilePath);
//
//            File file = new File(outputFilePath);
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.pdf");
//        }


        return "complete/printexcel";
    }

    @GetMapping("/generatePdf")
    public String printExceltoPDF(Model model) {


        String templeteName = "pdf/pdfTest";


        try {
            String title = "Sample Title";
            String content = "Sample Content";
            String outputFilePath = "C:/pdfs/output.pdf";
//            String imagePath ="file://"+"C:/pdfs/star.png";
            String imagePath ="C:/pdfs/star.png";



            model.addAttribute("title", title);

            model.addAttribute("content", content);
            model.addAttribute("path", imagePath);

            pdfService.test(title, content, imagePath,templeteName, outputFilePath);

            return templeteName;  // Thymeleaf 템플릿 이름
        } catch (Exception e) {
            model.addAttribute("content", "Error generating PDF: " + e.getMessage());
            return templeteName;  // Thymeleaf 템플릿 이름
        }


    }



    //pdf 변환을 위한 기능
    public ResponseEntity getStudentInfoPdf() {

        long eduId = 68;

        CompletedMember member = completeService.findByeduid(eduId).get(0);


        Map<String, Object> studentMap = new HashMap<>();


        studentMap.put("ID", member.getId());
        studentMap.put("eduID", member.getEduid());
        studentMap.put("memberID", member.getMemberid());


        Resource resource = null;

        try {

            String property = "java.io.tmpdir";

            String tempDir = System.getProperty(property);

            String fileNameUrl = pdfGeneratorUtil.createPdf("Student", studentMap);

            Path path = Paths.get(tempDir + "/" + fileNameUrl);

            resource = new UrlResource(path.toUri());

        } catch (Exception e) {

            e.printStackTrace();

        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")

                .body(resource);

    }
}
