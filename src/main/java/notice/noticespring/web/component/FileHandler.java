package notice.noticespring.web.component;

import notice.noticespring.web.domain.Education;
import notice.noticespring.web.domain.Member;
import notice.noticespring.web.domain.UserFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {


    @Value("${spring.servlet.multipart.location}")
    String filePath;


    public List<UserFile> UserFileUpload(List<MultipartFile> files, String year, String month, Education education, long eduid, String state) throws IOException
    {

        List<UserFile> fileList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(files))
        {
            for(MultipartFile multipartFile : files) {

                //이 부분을 바꿔야 실제 저장되는 위치가 바뀜
                //밑에 single함수(서명을 위한 그부분도 바꿔야함
                String path = "/webserver/itec/"+ year+"년/"+month+"월/";


                String contentType = multipartFile.getContentType();

                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }

                String file_name = multipartFile.getOriginalFilename();

                File file = new File(path + file_name); //파일 저장 경로 확인, 없으면 만든다.
                if (!file.exists()) {
                    file.mkdirs();
                }
                UserFile userFile = new UserFile();


                education.setFilepath(path);

                //이부분은 바뀌면 x 이건 DB에 저장될때 주소 이름인데.. 사실 쓸데 없이 길긴 함...
                path ="/itec/"+year+"년/"+month+"월/";
                userFile.setFilepath(path + file_name);
                userFile.setFilesize(multipartFile.getSize());
                userFile.setFilename(file_name);
                userFile.setEduid(eduid);
                userFile.setState(state);

                fileList.add(userFile);

                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }

            return fileList;
        }
        return null;

    }


    public String signFilePath(MultipartFile files, Member member) throws IOException
    {

        if(files != null)
        {
            String path = "/webserver/itec/signfiles/";


            String contentType = files.getContentType();


            String file_name = files.getOriginalFilename();

            File file = new File(path + file_name); //파일 저장 경로 확인, 없으면 만든다.
            if (!file.exists()) {
                file.mkdirs();
            }

        //이부분은 바뀌면 x 이건 DB에 저장될때 주소 이름인데.. 사실 쓸데 없이 길긴 함...
            path ="/itec/signfiles/"+ file_name;



            files.transferTo(file);

            file.setWritable(true);
            file.setReadable(true);


            return path;

        }

        return null;

    }
}
