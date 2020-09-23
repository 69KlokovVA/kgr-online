package ru.cbr.tomsk.kgr.repository;

import ru.cbr.tomsk.kgr.entity.RUstav;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface RUstav_repo extends CrudRepository<RUstav, Integer> {
    @Query(value="SELECT nvl(CCHNGE_N,0) as CCHNGE_N, to_char(CSOGL_D, 'fxdd.MM.yy') CSOGL_D, CREASON, to_char(CCHNGE_D15, 'fxdd.MM.yy') CCHNGE_D15, CCHNGE_N15\n" +
            "        FROM nsi_kgs.r_ustav R_USTAV\n" +
            "        WHERE (R_USTAV.CBASE = :ccode) AND (CC_FL>=0)\n" +
            "        and (csogl_d >= (SELECT MAX(CSOGL_D) FROM nsi_kgs.r_ustav\n" +
            "        WHERE ((CCHNGE_N is NULL) OR (CCHNGE_N=0)) AND (CBASE = :ccode) and (cc_fl>=0)))\n" +
            "        and (csogl_d >=(SELECT MAX(CSOGL_D) FROM nsi_kgs.r_ustav WHERE ((CCHNGE_N is NULL) OR (CCHNGE_N=0)) AND (CBASE = :ccode) and (cc_fl>=0)))\n" +
            "        ORDER BY R_USTAV.CSOGL_D",nativeQuery = true)
    List<Map<String, Object>> findByCcode(Integer ccode);
/*    @Query(value="SELECT * " +
            " FROM nsi_kgs.r_ustav R_USTAV " +
            " WHERE (R_USTAV.CBASE = :ccode) AND (CC_FL>=0) " +
            " and (csogl_d >= (SELECT MAX(CSOGL_D) FROM nsi_kgs.r_ustav " +
            " WHERE ((CCHNGE_N is NULL) OR (CCHNGE_N=0)) AND (CBASE = :ccode) and (cc_fl>=0))) " +
            " and (csogl_d >=(SELECT MAX(CSOGL_D) FROM nsi_kgs.r_ustav WHERE ((CCHNGE_N is NULL) OR (CCHNGE_N=0)) AND (CBASE = :ccode) and (cc_fl>=0)))" +
            " ORDER BY R_USTAV.CSOGL_D",nativeQuery = true)*/


}
