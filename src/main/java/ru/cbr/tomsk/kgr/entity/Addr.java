package ru.cbr.tomsk.kgr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "NSIK_ADDR")
public class Addr {
    @Id
    private String cid;
    private String caddr;
    private Integer ccode;
    private String ce_date;

    public Addr() {
    }

}
