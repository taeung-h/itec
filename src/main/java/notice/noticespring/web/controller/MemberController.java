package notice.noticespring.web.controller;

import notice.noticespring.web.component.FileHandler;
import notice.noticespring.web.domain.Admin;
import notice.noticespring.web.domain.Member;
import notice.noticespring.web.service.AdminService;
import notice.noticespring.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.List;

@Controller
public class MemberController {




    private final MemberService memberService;

    private final AdminService adminService;


    @Autowired
    public MemberController(MemberService memberService, AdminService adminService) {
        this.memberService = memberService;
        this.adminService = adminService;
    }

    @GetMapping("/members/new")
    public String createForm(Model model, Admin admin)
    {
        model.addAttribute("adminNow", admin);
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public ModelAndView create(@RequestPart(value="file", required = true) MultipartFile files, Member member, String adminName) throws IOException {


        FileHandler fileHandler = new FileHandler();

        ModelAndView mv = new ModelAndView();

        Admin admin = new Admin();

        admin.setName(adminName);


        mv.addObject("adminNow", admin);


        try {
            String path = fileHandler.signFilePath(files,member);
            member.setSignpath(path);
            memberService.join(member);

            mv.setViewName("admin/admin");
        } catch (IOException | IllegalStateException  e) {

            mv.addObject("joinalert","중복된 번호입니다.");
            mv.setViewName("members/createMemberForm");
        }
        return mv;


    }

    @GetMapping("/members/cancel")
    public String cancel(Model model,Admin admin)
    {
        model.addAttribute("adminNow", admin);
        return "admin/admin";
    }


    @GetMapping("/members")
    public String list(Model model, Admin admin) {
        List<Member> members = memberService.findmembers();
        model.addAttribute("members", members);
        model.addAttribute("adminNow", admin);
        return "members/memberList";
    }


    @PostMapping("/members/adminlogin")
    public ModelAndView adminlogin(Model model, Admin admin) {

        ModelAndView mv = new ModelAndView();

        if(adminService.findAdmin(admin)) {

            mv.addObject("adminNow", admin);

//            model.addAttribute("admin",admin);
            mv.setViewName("admin/admin");

//            return "admin/admin";

        } else {

            mv.setViewName("admin/adminlogin");

//            return "admin/adminlogin";
        }

        return mv;

    }


    @PostMapping("/members/adminonly")
    public String adminonly(Admin admin) {

        ModelAndView mv = new ModelAndView();

        try {
            adminService.join(admin);

        } catch (IllegalStateException e) {
        }
        finally {
            return "adminonly";
        }


    }

}