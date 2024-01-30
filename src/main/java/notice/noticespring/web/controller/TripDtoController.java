package notice.noticespring.web.controller;

import notice.noticespring.web.domain.Education;
import notice.noticespring.web.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TripDtoController {

    @GetMapping("/trip/list")
    public String edulist(Model model, Member member) {


        //List<OngoingEdu> tempOnEdulist = ongoingEduservice.findOngoingEdulist();

        List<Education> list = new ArrayList<>();
        List<String> result = new ArrayList<>();

//        for(OngoingEdu OnEdu : tempOnEdulist) {
//            list.add(eduservice.findByeduid(OnEdu.getEduid()).get());
//        }

        model.addAttribute("list", list);


        model.addAttribute("member",member);



        return "education/edulist";
    }


}
