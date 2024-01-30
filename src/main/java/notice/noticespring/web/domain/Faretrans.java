package notice.noticespring.web.domain;


import javax.persistence.*;

@Entity
@Table(name = "faretrans")
public class Faretrans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long fareid;

    @Column
    private Long oilsid;

    @Column
    private Long destiid;

    @Column
    private String category;

    @Column
    private String date;

    @Column
    private String company;

    @Column
    private String depart;

    @Column
    private String desti;

    @Column
    private String ways;

    @Column
    private String code;

    @Column
    private String distance;

    @Column
    private int cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFareid() {
        return fareid;
    }

    public void setFareid(Long fareid) {
        this.fareid = fareid;
    }

    public Long getOilsid() {
        return oilsid;
    }

    public void setOilsid(Long oilsid) {
        this.oilsid = oilsid;
    }

    public Long getDestiid() {
        return destiid;
    }

    public void setDestiid(Long destiid) {
        this.destiid = destiid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDesti() {
        return desti;
    }

    public void setDesti(String desti) {
        this.desti = desti;
    }

    public String getWays() {
        return ways;
    }

    public void setWays(String ways) {
        this.ways = ways;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    @Column
    private String receipt;


    public Faretrans(Long id, Long fareid, Long oilsid, Long destiid, String category, String date, String company, String depart, String desti, String ways, String code, String distance, int cost, String receipt) {
        this.id = id;
        this.fareid = fareid;
        this.oilsid = oilsid;
        this.destiid = destiid;
        this.category = category;
        this.date = date;
        this.company = company;
        this.depart = depart;
        this.desti = desti;
        this.ways = ways;
        this.code = code;
        this.distance = distance;
        this.cost = cost;
        this.receipt = receipt;
    }
}
