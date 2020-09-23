package ru.cbr.tomsk.kgr.repository;

import ru.cbr.tomsk.kgr.entity.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface SD_repo extends CrudRepository<Bank, Integer> {

    // Действующий совет директоров
    @Query(value = "SELECT employ.CCODE as CCODE, person.ccode as PERS_CODE, employ.cpost as EMP_CPOST," +
            " (person.CLNAME || ' ' || person.CNAME || ' ' || DECODE(person.CPATR, '0', '', '-', '', person.CPATR)) as CFULLNAME," +
            " DECODE(employ.CPOST, 30, 'Председатель Совета директоров', 31, 'Член Совета директоров') as PRIS_DECODE," +
            " DECODE(sovet.CTYPE, 'Ф', 'Физическое лицо', 'ф', 'Физическое лицо', sovet.CMESTO) as MESTO," +
            " DECODE(sovet.CTYPE, 'Ф', '-'," +
            "         'ф', '-', sovet.CDOLJN) as DOLJN," +
            " DECODE(sovet.CTYPE, 'Ф', '-'," +
            "         'ф', '-', DECODE(sovet.CADRES, '0', '-', sovet.CADRES)) as ADRES," +
            " DECODE(sovet.CNUM, '0', '-', NVL(sovet.CNUM, '-')) as TELEFON," +
            " TO_CHAR(employ.CB_DATE, 'fxDD.MM.YY') as IN_DATE," +
            " DECODE (NVL(s_ca_date,TO_CHAR(employ.CB_DATE,'fxDD.MM.YY')),TO_CHAR(employ.CB_DATE, 'fxDD.MM.YY'), '-', s_ca_date) as Pereiz_date" +
            " FROM (SELECT TO_CHAR(Max(s.ca_date), 'fxDD.MM.YY') as s_ca_date, s.cemploy as s_cemploy" +
            " FROM nsi_kgs.nsi_sovpr s WHERE (s.CC_FL >=0) AND (s.cbase = :cbase) GROUP BY s.cemploy) sovpr," +
            " nsi_kgs.nsi_employ employ, nsi_kgs.nsi_person person, nsi_kgs.nsi_sovet sovet" +
            " WHERE (employ.CBASE = :cbase)" +
            " AND (employ.CPERSON = person.CCODE) AND (employ.ccode = sovet.cemploy)" +
            " And (person.CC_FL >=0) AND (sovet.cc_fl>=0) AND (employ.CC_FL >=0)" +
            " AND (person.CE_DATE > SYSDATE) AND ((employ.CPOST = 30) OR (employ.CPOST = 31))" +
            " AND (employ.CB_DATE < employ.CE_DATE)" +
            " and (person.cb_date < person.ce_date) AND (sovet.ce_date > sysdate) AND (employ.ce_date > SYSDATE)" +
            " AND (sovpr.s_cemploy(+) = employ.ccode) AND (employ.creject = 0)" +
            " ORDER BY employ.CPOST, person.CLNAME, person.CNAME ", nativeQuery = true)
    List<Map<String, Object>> findSD_now(Integer cbase);

    @Query(value = "SELECT employ.CCODE as CCODE, person.ccode as pers_code," +
            " (person.CLNAME || ' ' || person.CNAME || ' ' || DECODE(person.CPATR, '0', '', '-', '', person.CPATR)) as CFULLNAME," +
            " DECODE(employ.CPOST, 30, 'Председатель Совета директоров'," +
            "                     31, 'Член Совета директоров') as Priz_decode," +
            " TO_CHAR(employ.CB_DATE, 'fxDD.MM.YY') as IN_DATE," +
            " DECODE(TO_CHAR(employ.CE_DATE, 'fmYYYY'), '2099', '-', TO_CHAR(employ.CE_DATE, 'fxdd.mm.yy')) as OUT_DATE" +
            " FROM nsi_kgs.nsi_employ employ, nsi_kgs.nsi_person person" +
            " WHERE (employ.CBASE = :cbase) AND (employ.CPERSON = person.CCODE) And (person.CC_FL >=0)" +
            " AND (employ.CC_FL >=0) AND (person.CE_DATE > SYSDATE)" +
            " AND ((employ.CPOST = 30) OR (employ.CPOST = 31))  AND (employ.CB_DATE < employ.CE_DATE)" +
            " and (person.cb_date < person.ce_date) and (employ.creject = 0)" +
            " ORDER BY person.CLNAME, person.CNAME, employ.CPOST ", nativeQuery = true)
    List<Map<String, Object>> findSD_hist(Integer cbase);
}