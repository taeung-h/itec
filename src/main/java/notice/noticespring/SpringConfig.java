package notice.noticespring;


import notice.noticespring.app.repository.ReportRepository;
import notice.noticespring.app.service.ReportService;
import notice.noticespring.web.repository.*;
import notice.noticespring.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SpringConfig implements WebMvcConfigurer {

    private final SpringDataJpaMemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final EduRepository eduRepository;
    private final OngoingEduRepository ongoingEduRepository;

    private final CompleteRepository completeRepository;

    private final AdminRepository adminRepository;

    private final OilsRepository oilsRepository;

    private final LocationRepository locationRepository;

    private final ReportRepository reportRepository;




    @Autowired
    public SpringConfig(SpringDataJpaMemberRepository memberRepository, FileRepository fileRepository
            , EduRepository eduRepository, OngoingEduRepository ongoingEduRepository,
                        CompleteRepository completeRepository, AdminRepository adminRepository, OilsRepository oilsRepository, LocationRepository locationRepository, ReportRepository reportRepository) {

        this.memberRepository = memberRepository;
        this.fileRepository = fileRepository;
        this.eduRepository = eduRepository;
        this.ongoingEduRepository = ongoingEduRepository;
        this.completeRepository = completeRepository;
        this.adminRepository = adminRepository;
        this.oilsRepository = oilsRepository;
        this.locationRepository = locationRepository;
        this.reportRepository = reportRepository;
    }



    @Bean
    public OngoingEduservice ongoingEduservice(){
        return new OngoingEduservice(ongoingEduRepository);}
    @Bean
    public Eduservice eduservice() {
        return new Eduservice(eduRepository);
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

    @Bean
    public Fileservice fileservice() {return new Fileservice(fileRepository);}

    @Bean
    public CompleteService completeService(){ return new CompleteService(completeRepository);}

    @Bean
    public AdminService adminService(){return new AdminService(adminRepository);}

    @Bean
    public OilsService oilsService(){
        return new OilsService(oilsRepository);}

    @Bean
    public LocationService locationService(){
        return new LocationService(locationRepository);}

    @Bean
    public ReportService reportService(){
        return new ReportService(reportRepository);
    }


    //@Bean
    //public MemberRepository memberRepository(){return new MemoryMemberRepository();    }
    //public MemberRepository memberRepository(){        return new JPAMemberRepository(em);    }



    @Value("${spring.servlet.multipart.location}")
    String filePath;


    //html에서 로컬 경로 가지고 온라인에 띄우기 위한 오버라이드
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/itec/**").addResourceLocations("file:///webserver/itec/");
        //registry.addResourceHandler("/itec/**").addResourceLocations("file://"+ filePath+"/itec/");
        //registry.addResourceHandler("/itec/**").addResourceLocations("file:///C:/itec/");
    }


    //flutter와 통신 중 CORS 문제 해결을 위한 방법
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST","PUT","DELETE");}
}
