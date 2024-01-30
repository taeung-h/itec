package notice.noticespring.web.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

public class MemberForm {

        private String name;
        private int phonenum;
        private String signpath;

        private MultipartFile file;

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }


        public int getPhonenum() {
            return phonenum;
        }
        public void setPhonenum(int phonenum) {
            this.phonenum = phonenum;
        }

        public String getSignpath() {
            return signpath;
        }

        public void setSignpath(String signpath) {
            this.signpath = signpath;
        }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
