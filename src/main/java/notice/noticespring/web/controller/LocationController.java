package notice.noticespring.web.controller;

import notice.noticespring.web.domain.Location;
import notice.noticespring.web.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @GetMapping("/location/new")
    public String locationNew(Model model)
    {
        return "businesstrip/newlocation";
    }


    @GetMapping("/location/cancel")
    public String locationCancle()
    {
        return "admin/admin";
    }


    @PostMapping("/location/new")
    public ModelAndView create(Location location) {

        ModelAndView mv = new ModelAndView();

        try {
            locationService.join(location);
            mv.setViewName("admin/admin");
        }
        catch (IllegalStateException e) {

            mv.addObject("joinalert","중복된 출장지명입니다.");
            mv.setViewName("businesstrip/newlocation");
        }

        return mv;
    }


    @GetMapping("/location/list")
    public String  locationlist(Model model){
        ModelAndView mv = new ModelAndView();

        List<Location> list = locationService.findOillist();

        model.addAttribute("list", list);

        return "businesstrip/locationlist";
    }


    @GetMapping("/location/modify")
    public String modifyMain(Model model,Long locationid) {

        Location temp = locationService.findOne(locationid);

        model.addAttribute("data", temp);

        return "businesstrip/modifylocation";
    }

    @PostMapping("/location/modify")
    public String modifyResult(@ModelAttribute Location location, Long locationid) {

        location.setId(locationid);
        locationService.modify(location);

        return "redirect:/location/list";
    }

}
