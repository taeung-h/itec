package notice.noticespring.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ongoingedu")
public class OngoingEdu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long eduid;
    @Column
    private int period;


    public void setId(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEduid(long eduid) {
        this.eduid = eduid;
    }

    public long getEduid() {
        return eduid;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }


}



