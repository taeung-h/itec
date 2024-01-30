package notice.noticespring.web.controller;

import notice.noticespring.web.domain.Admin;
import notice.noticespring.web.domain.EduCheckBox;
import notice.noticespring.web.domain.Education;
import notice.noticespring.web.domain.OngoingEdu;
import notice.noticespring.web.service.Eduservice;
import notice.noticespring.web.service.OngoingEduservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OngoingEduController {

    private final OngoingEduservice ongoingEduservice;
    private final Eduservice eduservice;

    @Autowired
    public OngoingEduController(OngoingEduservice ongoingEduservice, Eduservice eduservice) {
        this.ongoingEduservice = ongoingEduservice;
        this.eduservice = eduservice;
    }


//관리자 메인화면에서 교육관리 클릭 시(admin.html)
    @GetMapping("/ongoingedu")
    public String ongoingEdu(Model model, Admin admin) {

        List<Education> edulist = eduservice.findEdulist();
        model.addAttribute("edulist",edulist);
        model.addAttribute("adminNow", admin);


        EduCheckBox eduCheckBox = new EduCheckBox();
        model.addAttribute("educheckbox", eduCheckBox);

        return "admin/selectedu";
    }

    //교육 관리 창에서 체크박스 선택 이후 확인 버튼 누를 시(education/selectedu.html)
    @PostMapping("/ongoingedu/select")
    public ModelAndView selectedu(Model model, Admin admin, @ModelAttribute("educheckbox") EduCheckBox eduCheckBox) {

        ModelAndView mv = new ModelAndView();

        mv.addObject("adminNow", admin);

        OngoingEdu ongoingEdu = new OngoingEdu();

        //교육 오픈 위한 부분
        if (eduCheckBox.getOpenedEduList()!=null && eduCheckBox.getOpenedEduList().size()!=0 )
        {
            for(long eduid : eduCheckBox.getOpenedEduList()) {
                ongoingEdu.setEduid(eduid);
                ongoingEdu.setPeriod(30);

                try {
                    ongoingEduservice.join(ongoingEdu);
                    Education educationTemp = eduservice.findByeduid(eduid).get();
                    educationTemp.setState("open");
                    eduservice.modify(educationTemp);
                    mv.setViewName("admin/admin");

                }catch  (IllegalStateException e) {

                    mv.addObject("alert","이미 진행중인 교육입니다.");


                    mv.setViewName("forward:/ongoingedu");
                }

            }
        } else if (eduCheckBox.getCloseEduList() != null && eduCheckBox.getCloseEduList().size()!=0) {
            for(long eduid : eduCheckBox.getCloseEduList()) {
                try {
                    ongoingEduservice.delete(ongoingEduservice.findByeduid(eduid).get().getId());
                    Education educationTemp = eduservice.findByeduid(eduid).get();
                    educationTemp.setState("end");
                    eduservice.modify(educationTemp);
                    mv.setViewName("admin/admin");

                }catch  (IllegalStateException e) {

                    mv.addObject("alert","이미 종료된 교육입니다.");

                    mv.setViewName("forward:/ongoingedu");
                }

            }
        }
        else
        {
            mv.setViewName("admin/admin");
        }





        return mv;
    }

    @GetMapping("/ongoingedu/cancel")
    public String cancel(Model model, Admin admin)
    {
        model.addAttribute("adminNow", admin);
        return "admin/admin";
    }


}
