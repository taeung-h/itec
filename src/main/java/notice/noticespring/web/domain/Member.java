package notice.noticespring.web.domain;


import lombok.ToString;

import javax.persistence.*;


@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(length = 15)
    private String name;
    @Column
    private int phonenum;
    @Column(length = 127)
    private String signpath;

    @Column
    private Long superiorid;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Long getSuperiorid() {
        return superiorid;
    }

    public void setSuperiorid(Long superiorid) {
        this.superiorid = superiorid;
    }
}