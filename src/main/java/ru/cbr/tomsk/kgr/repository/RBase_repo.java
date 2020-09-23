package ru.cbr.tomsk.kgr.repository;

import ru.cbr.tomsk.kgr.entity.RBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RBase_repo extends CrudRepository<RBase, Integer> {
    @Query(value="SELECT *  FROM r_base r WHERE (r.ccode = :cbase)  AND (CE_DATE > SYSDATE) ",nativeQuery = true)

    List<RBase> findAll(Integer cbase);
}
