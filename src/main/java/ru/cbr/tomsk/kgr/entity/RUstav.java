package ru.cbr.tomsk.kgr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "R_USTAV", schema = "NSI_KGS")
@Getter
@Setter
public class RUstav {
    @Id
    private Integer cid;
    private Integer ccode;
    private Integer CCHNGE_N;
    private Date CSOGL_D;
    private String CREASON;
    private Date CCHNGE_D15;
    private String CCHNGE_N15;

    public RUstav() {

    }
}
