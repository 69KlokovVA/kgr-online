package ru.cbr.tomsk.kgr.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "NSI_PERSON")
@Getter
@Setter
public class Person implements Serializable {
    @Id
    private Integer cid;
    private Integer ccode;
    private Integer cc_fl;
    private String clname;
    private String cname;
    private String cpatr;
    private String ce_date;

    public Person() {
    }

}
