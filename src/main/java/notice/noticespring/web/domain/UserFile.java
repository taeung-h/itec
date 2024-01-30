package notice.noticespring.web.domain;

import jdk.jfr.DataAmount;

import javax.persistence.*;


@Entity
@Table(name = "filelist")
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private long filesize;
    @Column(length = 63)
    private String filename;
    @Column(length = 127)
    private String filepath;
    @Column
    private Long eduid;

    @Column
    private String state;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEduid() {
        return eduid;
    }

    public void setEduid(Long eduid) {
        this.eduid = eduid;
    }



    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }



    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}

