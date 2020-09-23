package ru.cbr.tomsk.kgr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Date;

@Entity
@Table(name = "R_BANK")
@Getter
@Setter
public class Bank {
    @Id
    private Integer cid;
    private Integer ccode;
    private Integer cdoc;
    private Integer cdEnd;
    private Integer coType;
    private Integer ccFl;
    private Date ccDate;
    private Date cbDate;
    private Date ceDate;
    private Date cbAct;
    private Date ceAct;
    private Integer cbase;
    private Integer cgu;
    private String cnamer;
    private String cname;
    private String csname;
    private String cfname;
    private Date cdName;
    private Integer cregnum;
    private Integer cregnr;
    private Date cdreg;
    private Date cdpreg;
    private String cregnum15;
    private Date cdreg15;
    private Date cdUlikv;
    private Date cdBrot;
    private Integer copf;
    private Date cdOpf;
    private String cokpo;
    private Date cdOkpo;
    @Column(name = "CD_HREG")
    private Date cdHreg;
    private Date cdArko;
    private String carend;
    private Integer cflown;
    private Integer ctbank;
    private Integer ctspecb;
    private Integer ccrbase;
    private Integer ctbanul;
    private Integer cppBase;
    private String cregnumL;
    private Date cdregL;
    private Integer ccorr;
    private String centFio;
    private Date cvDate;
    private Integer cupdate;
    private Integer csend;
    private Long p1;
    private Long p2;
    private String t1;
    private Date d1;
    private Date d2;
    private Date cpoDate;
    private String cpoPc;
    private String cpoUser;
    @Column(name ="CPO_TIME")
    private Date cpoTime;
    private Integer cidloc;
    private Integer cbankId;
    private Integer clmaket;
    private Date innDate;
    private String inn;

    public Bank() {
    }

}



