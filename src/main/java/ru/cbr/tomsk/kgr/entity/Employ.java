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
@Table(name = "NSI_EMPLOY")
@Getter
@Setter
public class Employ implements Serializable{
    @Id
    private Integer cid;
    private Integer cbase;
    private Integer cc_fl;
    private Integer cperson;
    private String ce_date;
    private Integer cpost;

    public Employ() {
    }

}
