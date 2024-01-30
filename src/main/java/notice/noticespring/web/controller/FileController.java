package notice.noticespring.web.controller;


import notice.noticespring.web.component.FileHandler;
import notice.noticespring.web.domain.Admin;
import notice.noticespring.web.domain.Education;
import notice.noticespring.web.domain.UserFile;
import notice.noticespring.web.service.Eduservice;
import notice.noticespring.web.service.Fileservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class FileController {

    private final Fileservice fileservice;
    private final Eduservice eduservice;


    @Autowired
    public FileController(Fileservice fileservice, Eduservice eduservice) {
        this.eduservice = eduservice;
        this.fileservice = fileservice;
    }

    /**교육업로드 페이지 버튼 눌림
     *
     * @return
     */
    @GetMapping("/file/new")
    public String createForm(Model model, Admin admin)
    {
        model.addAttribute("adminNow", admin);
        return "files/uploadFile";
    }


    /** 업로드 교육 파일 확인 페이지 버튼 눌림
     *
     * @param model
     * @return
     */
    @GetMapping("/files")
    public String list(Model model, Admin admin) {
        List<UserFile> files = fileservice.findFiles();
        model.addAttribute("adminNow", admin);
        model.addAttribute("files", files);
        return "files/fileList";
    }

    //세부 교육 뷰에서 파일관리 버튼 클릭시 (adminmonthedu.html)
    @GetMapping("/edufiles")
    public ModelAndView edufilelist(long id, Admin admin) {

        ModelAndView mv = new ModelAndView();

        List<UserFile> files = fileservice.findFilesbyeduid(id);
        mv.addObject("files", files);
        mv.addObject("adminNow", admin);
        mv.setViewName("files/fileList");
        return mv;
    }

    @PostMapping("/files/new")
    public ModelAndView create(@ModelAttribute Education education,
                               @RequestPart(value="filename", required = false) List<MultipartFile> files
                               , @RequestPart(value = "downfile", required = false) List<MultipartFile> downfiles, Admin admin) throws IOException {

    //public ModelAndView create(@ModelAttribute Education education) throws IOException {
        ModelAndView mv = new ModelAndView();

        FileHandler fileHandler = new FileHandler();


        try{
            education.setState("new");
            eduservice.join(education);
            mv.addObject("adminNow", admin);
            mv.setViewName("admin/admin");

        }catch  (IllegalStateException e) {

            mv.addObject("jointitlealert","중복된 교육명입니다.");
            mv.setViewName("files/uploadFile");
        }



        if (files.get(0).getOriginalFilename().equals("")) {
        }
        else {

            long tempeduid = (eduservice.findBytitle(education.getTitle())).get().getId();

            List<UserFile> filesTemp = fileHandler.UserFileUpload(files, education.getYear(), education.getMonth(), education, tempeduid, "V");

            try {

                if (filesTemp != null) {
                    fileservice.join(filesTemp);
                    mv.setViewName("admin/admin");
                } else {
                    mv.setViewName("files/uploadFile");
                    mv.addObject("joinfilealert", "본문파일 업로드 실패.");
                }

            } catch (IllegalStateException e) {

                mv.addObject("joinfilealert", "본문파일이 중복된 파일명입니다.");
                mv.setViewName("files/uploadFile");
            }
        }

        if (downfiles.get(0).getOriginalFilename().equals("")) {
        }
        else {

            long tempeduid = (eduservice.findBytitle(education.getTitle())).get().getId();

            List<UserFile> filesTemp = fileHandler.UserFileUpload(downfiles, education.getYear(), education.getMonth(), education, tempeduid, "D");

            try {

                if (filesTemp != null) {
                    fileservice.join(filesTemp);
                    mv.setViewName("admin/admin");
                } else {
                    mv.setViewName("files/uploadFile");
                    mv.addObject("joinfilealert", "첨부파일 업로드 실패.");
                }

            } catch (IllegalStateException e) {

                mv.addObject("joinfilealert", "첨부파일이 중복된 파일명입니다.");
                mv.setViewName("files/uploadFile");
            }
        }


        return mv;
    }






    /** 파일 업로드 취소
     *
     * @return
     */
    @PostMapping("/files/cancel")
    public String cancel()
    {
        return "admin/admin";
    }


    @Value("${spring.servlet.multipart.location}")
    String filePath;

    @GetMapping("/files/download")
    public ResponseEntity<Resource> download(@RequestParam Long id) throws IOException{
        UserFile userFile = fileservice.findFilebyid(id);
        Path path = Paths.get(filePath +"/"+ userFile.getFilepath());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(userFile.getFilename(), StandardCharsets.UTF_8).build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}
