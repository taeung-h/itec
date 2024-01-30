package notice.noticespring.web.controller;

import notice.noticespring.web.domain.Member;
import notice.noticespring.web.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/adminlogin")
    public String adminlogin() {
        return "admin/adminlogin";
    }

    @GetMapping("/adminonly")
    public String adminonly() {
        return "admin/adminonly";
    }

    @PostMapping("/admin")
    public String admin() {
        return "admin/admin";
    }


    //사용자 로그인 창에서 로그인 시 (home.html)
    //@RequestMapping(value = "/home/login")
    @PostMapping("/home/login")
    public ModelAndView login(@RequestParam("name") String name, @RequestParam("phonenum") String phonenum)
    {
        ModelAndView mv = new ModelAndView();

        Member getmember = new Member();




        if(memberService.matchingName(name) != true)
        {
            mv.setViewName("home");
            mv.addObject("namealert", "가입된 이름이 아닙니다.");
        }
        else if(memberService.matchingPhonenum(phonenum) != true)
        {
            mv.setViewName("home");
            mv.addObject("phonenumalert", "잘못된 번호, 8자리 번호를 확인바랍니다.");
        }
        else{
            getmember.setName(name);
            getmember.setPhonenum(Integer.parseInt(phonenum));
            getmember.setId(memberService.findByphonenum(getmember.getPhonenum()).getId());
            mv.addObject("member",getmember);

            mv.setViewName("members/loginMember");
        }


        return mv;
    }


    //pdf 리스트 버튼 클릭 시 (admin.html)
    @GetMapping("/pdflist")
    public String pdflist() {
        return "testPDF";
    }




}
