package notice.noticespring.app.controller;


import notice.noticespring.app.domain.Report;
import notice.noticespring.app.dto.PaginationDto;
import notice.noticespring.app.component.PaginationParams;
import notice.noticespring.app.dto.ReportDetailDto;
import notice.noticespring.app.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping("/report/list")
    public @ResponseBody PaginationDto reportList(Model model) {

        PaginationDto paginationDto = new PaginationDto();
        PaginationParams paginationParams = new PaginationParams();

        paginationParams.setHasMore(true);
        paginationParams.setCount(20);
        List<Report> tempList = reportService.findReportlist();

        paginationDto.setData(tempList);
        paginationDto.setMeta(paginationParams);



        return paginationDto;
    }

    @PostMapping(path = "/report/new", produces = "application/json")
    public ResponseEntity<Report> updateReport(@RequestBody Report reportDetailDto){
        reportDetailDto.setId(null);
        Report tempReport =  reportService.join(reportDetailDto);
        return new ResponseEntity<>(tempReport, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/report/{id}")
    public @ResponseBody ReportDetailDto responseReport(@PathVariable("id") int id) {

        ReportDetailDto reportDto = new ReportDetailDto();
        PaginationParams paginationParams = new PaginationParams();


        Report tempList = reportService.findByid(id).get();

        reportDto.setReport(tempList);


        return reportDto;
    }

}
