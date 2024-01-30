package notice.noticespring.web.domain;


import org.hibernate.property.access.internal.PropertyAccessStrategyResolverInitiator;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "oils")

public class Oils {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column
    private double perliter;

    @Column
    private int price;

    @Column
    private int repaircost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPerliter() {
        return perliter;
    }

    public void setPerliter(double perliter) {
        this.perliter = perliter;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRepaircost() {
        return repaircost;
    }

    public void setRepaircost(int repaircost) {
        this.repaircost = repaircost;
    }





}
