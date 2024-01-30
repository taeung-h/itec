package notice.noticespring.app.dto;

import notice.noticespring.app.domain.Report;

public class ReportDetailDto {
    private int id;
    private String title;
    private String date;
    private String username;
    private String engineers;

    private String equip_num;
    private String equip_type;
    private String equip_name;
    private String remark;
    private String content;

    public void setReport(Report report){
        this.id = Integer.parseInt(report.getId().toString());
        this.title = report.getTitle();
        this.date = report.getDate();
        this.username = report.getUsername();
        this.engineers = report.getUsername();
        this.equip_name = report.getEquip_name();
        this.equip_type = report.getEquip_type();
        this.equip_num =  report.getEquip_num();
        this.content = report.getContent();
        this.remark = report.getRemark();

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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


}
