package notice.noticespring.web.service;

import notice.noticespring.web.domain.Admin;
import notice.noticespring.web.repository.AdminRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void join(Admin admin)
    {
        validateDuplicateMember(admin); //중복회원 검증
        adminRepository.save(admin);
    }

    private void validateDuplicateMember(Admin admin) {

        List<Admin> tempList = adminRepository.findAll();
        for (Admin tempadmin : tempList) {
            if (tempadmin.getName().equals(admin.getName())) {
                throw new IllegalStateException("이미 존재하는 데이터입니다.");
            }
        }
    }

    public boolean findAdmin(Admin admin)
    {
        boolean result = false;

        Admin tempAdmin = new Admin();

        try{
            if(adminRepository.findByname(admin.getName()).isPresent())
            {
                tempAdmin = adminRepository.findByname(admin.getName()).get();

                if( tempAdmin !=null )
                {
                    if(tempAdmin.getPassword().equals(admin.getPassword()))
                    {
                        result = true;
                    }
                }

            }

        }
        catch (IllegalStateException e)
        {
            throw new IllegalStateException("admin DB 읽기 실패");
        }


        return result;
    }
}
