package notice.noticespring.app.domain;


import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Table(name ="Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String date;

    @Column
    private String imgUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEngineers() {
        return engineers;
    }

    public void setEngineers(String engineers) {
        this.engineers = engineers;
    }

    public String getEquip_num() {
        return equip_num;
    }

    public void setEquip_num(String equip_num) {
        this.equip_num = equip_num;
    }

    public String getEquip_type() {
        return equip_type;
    }

    public void setEquip_type(String equip_type) {
        this.equip_type = equip_type;
    }

    public String getEquip_name() {
        return equip_name;
    }

    public void setEquip_name(String equip_name) {
        this.equip_name = equip_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column
    private String username;
    @Column
    private String engineers;
    @Column
    private String equip_num;
    @Column
    private String equip_type;
    @Column
    private String equip_name;
    @Column
    private String remark;
    @Column
    private String content;

    @Column
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
