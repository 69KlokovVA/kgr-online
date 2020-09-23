package ru.cbr.tomsk.kgr.repository;

import ru.cbr.tomsk.kgr.entity.Addr;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Addr_repo extends CrudRepository<Addr, Integer> {
    @Query(value="SELECT *  FROM NSI_Addr a WHERE (a.ccode = :caddr) AND (CC_FL>=0) and (CE_DATE > SYSDATE)",nativeQuery = true)
    List<Addr> findAll(Integer caddr);
}
