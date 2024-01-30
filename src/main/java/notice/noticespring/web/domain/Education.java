package notice.noticespring.web.domain;

import javax.persistence.*;


@Entity
@Table(name = "edulist")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 63)
    private String title;
    @Column(length = 511)
    private String content;
    @Column(length = 127)
    private String filepath;
    @Column(length = 7)
    private String year;
    @Column(length = 15)
    private String state;
    @Column(length = 7)
    private String month;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

}
