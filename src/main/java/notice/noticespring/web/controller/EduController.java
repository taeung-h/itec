package notice.noticespring.web.controller;

import notice.noticespring.web.component.FileHandler;
import notice.noticespring.web.domain.*;
import notice.noticespring.web.service.CompleteService;
import notice.noticespring.web.service.Eduservice;
import notice.noticespring.web.service.Fileservice;
import notice.noticespring.web.service.OngoingEduservice;
import notice.noticespring.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EduController {
    private final Eduservice eduservice;
    private final Fileservice fileservice;

    private final OngoingEduservice ongoingEduservice;

    private final CompleteService completeService;
    FileHandler fileHandler = new FileHandler();

    @Autowired
    public EduController(Eduservice eduservice, Fileservice fileservice, OngoingEduservice ongoingEduservice, CompleteService completeService) {
        this.fileservice = fileservice;
        this.eduservice = eduservice;
        this.ongoingEduservice = ongoingEduservice;
        this.completeService = completeService;
    }

    //사용자 권한 로그인 후 교육 리스트 버튼 클릭 시 실행(loginMember.html)
    @GetMapping("/education/list")
    public String edulist(Model model, Member member) {


        List<OngoingEdu> tempOnEdulist = ongoingEduservice.findOngoingEdulist();

        List<Education> list = new ArrayList<>();
        List<String> result = new ArrayList<>();

        for(OngoingEdu OnEdu : tempOnEdulist) {
            list.add(eduservice.findByeduid(OnEdu.getEduid()).get());
        }

        model.addAttribute("list", list);


        model.addAttribute("member",member);



        return "education/edulist";
    }

    //사용자 권한으로 세부 교육 선택 시(edulist.html 또는 )
    @GetMapping("/education/monthedulist") // localhost:8080/education/monthedulist?id=1
    public String monthedu(Model model,Member member, long eduid, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<UserFile> tempfilelist = fileservice.findFilesbyeduid(eduid);

        ArrayList<UserFile> filelist = new ArrayList<>();
        ArrayList<UserFile> downfilelist = new ArrayList<>();


        for(UserFile temp : tempfilelist) {
            if (temp.getState().equals("D")) {
                downfilelist.add(temp);
            }
            else{
                filelist.add(temp);
            }
        }



        model.addAttribute("filelist", filelist);
        model.addAttribute("downfilelist", downfilelist);


        model.addAttribute("education", eduservice.eduView(eduid));
        model.addAttribute("member", member);


        return "education/monthedu";
    }





    //관리자 권한으로 교육 리스트 버튼 클릭 시
    @GetMapping("/educations")
    public String adminEdulist(Model model, Admin admin, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Education> list = eduservice.findEdulist(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("adminNow", admin);
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/adminedulist";
    }

    //관리자 권한으로 세부 교육 선택 시
    @GetMapping("/education/adminmonthedulist") // localhost:8080/education/adminmonthedulist?id=1
    public String adminMonthedu(Model model, Admin admin,long id, String deleteAlert) {

        List<UserFile> tempfilelist = fileservice.findFilesbyeduid(eduservice.eduView(id).getId());

        ArrayList<UserFile> filelist = new ArrayList<>();
        ArrayList<UserFile> downfilelist = new ArrayList<>();

        for(UserFile temp : tempfilelist) {
            if (temp.getState().equals("D")) {
                downfilelist.add(temp);
            }
            else{
                filelist.add(temp);
            }
        }


        model.addAttribute("adminNow", admin);
        model.addAttribute("filelist", filelist);
        model.addAttribute("downfilelist", downfilelist);

        model.addAttribute("deleteAlert", deleteAlert);

        model.addAttribute("education", eduservice.eduView(id));
        return "admin/adminmonthedu";
    }


    //관리자 권한으로 교육 삭제 버튼 클릭 시
    @GetMapping("/education/delete")
    public String educationDelete(RedirectAttributes redirectAttributes, long id, Admin admin){
        String temp = "";

        redirectAttributes.addAttribute("name", admin.getName());


        if(ongoingEduservice.findByeduid(id).isEmpty())
        {
            eduservice.delete(id);

            temp = "redirect:/educations";
        }
        else {
            redirectAttributes.addAttribute("deleteAlert","오픈되어있는 강의입니다.");
            redirectAttributes.addAttribute("id", id);

            temp = "redirect:/education/adminmonthedulist";
        }


        return temp;

    }


    //관리자 권한으로 교육 수정 버튼 클릭 시
    @PostMapping("/education/modify")
    public ModelAndView modify(Admin admin, @ModelAttribute Education geteducation, @RequestPart(value="getfiles", required = false) List<MultipartFile> files
    ) throws IOException {

        Education educationTemp = eduservice.eduView(geteducation.getId());
        educationTemp.setTitle(geteducation.getTitle());
        educationTemp.setContent(geteducation.getContent());

        ModelAndView mv = new ModelAndView();


        eduservice.modify(educationTemp);
        mv.addObject("adminNow", admin);
        mv.setViewName("admin/admin");

        if (files.get(0).getOriginalFilename().equals("")) {
        }
        else {


            List<UserFile> filesTemp = fileHandler.UserFileUpload(files,educationTemp.getYear()
                    ,educationTemp.getMonth(),educationTemp,geteducation.getId(), "V");

            try {

                if (filesTemp != null) {
                    fileservice.join(filesTemp);
                    mv.setViewName("admin/admin");
                } else {
                    mv.setViewName("files/uploadFile");
                    mv.addObject("joinfilealert", "파일 업로드 실패.");
                }

            } catch (IllegalStateException e) {

                mv.addObject("joinfilealert", "중복된 파일명입니다.");
                mv.setViewName("files/uploadFile");
            }
        }



        return mv;
    }


    //관리자, 사용자 모두 취소 버튼 클릭 시
    @GetMapping("/education/cancel")
    public String cancel(Model model, Admin admin)
    {
        model.addAttribute("adminNow", admin);

        return "redirect:/education/adminmonthedulist";
    }


}
