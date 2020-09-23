package ru.cbr.tomsk.kgr.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "R_BASE")
@Getter
@Setter
public class RBase implements Serializable {
    @Id
    private String cid;
    private Integer ccode;
    private String cb_date;
    private String ce_date;
    private String cname;

    private Integer curaddr;
    private Integer caddrmn;



    @OneToMany(mappedBy = "rbaseaddr")
    @Fetch(value = FetchMode.SUBSELECT)
    List<Addr> addrList;


    public RBase() {
    }
}
