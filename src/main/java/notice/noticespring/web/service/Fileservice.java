package notice.noticespring.web.service;

import notice.noticespring.web.domain.UserFile;
import notice.noticespring.web.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class Fileservice {

    private final FileRepository fileRepository;

    @Autowired
    public Fileservice(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * 파일업로드
     */
    public void join(UserFile file)
    {
        //같은 폰번호 있으면 안된다
        validateDuplicateMember(file); //중복회원 검증
        fileRepository.save(file);

    }

    public void join(List<UserFile> fileList)
    {
        if (fileList.isEmpty())
        {
            return;
        }
        for (UserFile file: fileList)
        {
            validateDuplicateMember(file); //중복파일 검증
            fileRepository.save(file);
        }

    }


    private void validateDuplicateMember(UserFile file) {
        fileRepository.findByfilename(file.getFilename())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 파일명입니다.");
                });
    }

    public List<UserFile> findFiles(){
        return fileRepository.findAll();
    }

    public Page<UserFile> findFiles(Pageable pageable){


        return fileRepository.findAll(pageable);
    }



    public List<UserFile> findFilesbyeduid(long eduid){

        return fileRepository.findByeduid(eduid);
    }

    public UserFile findFilebyid(long id){

        return fileRepository.findByid(id);
    }

    public void delete(long id){
        fileRepository.deleteById(id);
    }


}
