package notice.noticespring.web.domain;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name = "completedmember")
public class CompletedMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long memberid;
    @Column
    private Long eduid;
    @Column
    private String date;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setEduid(Long ongoingid) {
        this.eduid = ongoingid;
    }

    public Long getEduid() {
        return eduid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
