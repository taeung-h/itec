package notice.noticespring.web.controller;


import notice.noticespring.web.domain.Oils;
import notice.noticespring.web.service.OilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OilsController {

    private final OilsService oilsService;

    @Autowired
    public OilsController(OilsService oilsService) {
        this.oilsService = oilsService;
    }


    @GetMapping("/oils/new")
    public String oilsNew(Model model)
    {
        return "businesstrip/newoils";
    }

    @GetMapping("/oils/cancel")
    public String oilsCancle()
    {
        return "admin/admin";
    }

    @PostMapping("/oils/new")
    public ModelAndView create(Oils oils) {

        ModelAndView mv = new ModelAndView();

        try {
            oilsService.join(oils);
            mv.setViewName("admin/admin");
        }
        catch (IllegalStateException e) {

            mv.addObject("joinalert","중복된 유류명입니다.");
            mv.setViewName("businesstrip/newoils");
        }

        return mv;
    }

    @GetMapping("/oils/list")
    public String  oilslist(Model model){
        ModelAndView mv = new ModelAndView();

        List<Oils> list = oilsService.findOillist();

        model.addAttribute("list", list);

        return "businesstrip/oilslist";
    }


    @GetMapping("/oils/modify")
    public String modifyMain(Model model,Long oilid) {

        Oils temp = oilsService.findOne(oilid);

        model.addAttribute("oil", temp);

        return "businesstrip/modifyoils";
    }

    @PostMapping("/oils/modify")
    public String modifyResult(@ModelAttribute Oils oils, Long id) {

        oils.setId(id);
        oilsService.modify(oils);

        return "redirect:/oils/list";
    }

}
