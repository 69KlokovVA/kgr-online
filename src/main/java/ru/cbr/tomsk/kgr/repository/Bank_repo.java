package ru.cbr.tomsk.kgr.repository;

import ru.cbr.tomsk.kgr.entity.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface Bank_repo extends CrudRepository<Bank, Integer> {
      @Query(value = "SELECT  cname, cbase, csname, ctbank" +
              ", cnamer, to_char(cd_Hreg, 'fxdd.MM.yy') as cdHreg, cregnum, to_char(cdreg15, 'fxdd.MM.yy') as cdreg15, cregnum15, nvl(inn, 0) as inn, to_char(nvl(inn_Date,'01.01.1900'), 'fxdd.MM.yy') as innDate\n" +
              " FROM R_Bank b WHERE  b.cregnum = :cregnum\n" +
              " and ce_date > SYSDATE", nativeQuery = true)
      List<Map<String, Object>> findByCregnum(Integer cregnum);
/*    @Query(value = " SELECT k_bank.ctbank, k_bank.CB_DATE, k_bank.CE_DATE, k_bank.CBASE, k_bank.CGU, k_bank.CNAMER, k_bank.CNAME, k_bank.CSNAME, " +
            "    k_bank.CPP_BASE, k_bank.ctbanul, k_bank.INN, k_bank.INN_DATE, " +
            "    k_bank.CFNAME, k_bank.CREGNUM, " +
            "    k_bank.CREGNR, TO_CHAR(k_bank.CDREG, 'fxdd.mm.yy') as CDREG, " +
            "    TO_CHAR(k_bank.CDPREG, 'fxdd.mm.yy') as CDPREG, k_bank.COPF, k_bank.CD_NAME, k_bank.CD_OPF, k_bank.COKPO, " +
            "    TO_CHAR(k_bank.CV_DATE, 'fxdd.mm.yy') as CV_DATE, k_bank.CD_END, " +
            "    DECODE (k_bank.CE_DATE, k_bank3.max_date, 'new', 'old') as type_name, ntype.csname as preff, k_bank.cregnum15, " +
            "    TO_CHAR(k_bank.CDREG15, 'fxdd.mm.yy') as CDREG15 " +
            "    FROM nsi_kgs.nsi_type ntype,  " +
            "(SELECT cbase, max(ce_date) as Max_Date from nsi_kgs.k_bank WHERE " +
            "            (cbase in (SELECT CBASE FROM nsi_kgs.k_bank WHERE 1=1 " +
//            "                    --&Cond " +
            "                    GROUP BY CBASE )) group by cbase) k_bank3, " +
            "    NSI_KGS.k_BANK k_bank,  " +
            "(SELECT CBASE, MAX(CE_DATE) as Max_Date FROM nsi_kgs.k_bank WHERE 1=1 " +
//            "            --&Cond " +
            "    GROUP BY CBASE) k_bank2 " +
            "    WHERE (k_bank.cbase = k_bank2.cbase) AND (k_bank.cbase = k_bank3.cbase) " +
            "    AND ((k_bank.CE_DATE = k_bank2.Max_Date) OR (k_bank.CE_DATE = k_bank3.Max_Date)) " +
            "    AND (k_bank.cregnr = ntype.ccode) AND (ntype.cstype = 5) " +
            "    ORDER BY  k_bank.cregnum, k_bank.ce_date DESC, k_bank.CNAMER ", nativeQuery = true)*/


    @Query(value = "SELECT DECODE(kp.ccode, 16, 'МГТУ ЦБ РФ', 17, 'МГТУ ЦБ РФ', kp.CNAME) as CNAME " +
            " FROM nsi_kgs.list_kp kp, R_Bank b WHERE b.cgu = kp.ccode " +
            " and cregnum = :cregnum and b.CE_DATE > SYSDATE", nativeQuery = true)
    List<String> findTU(Integer cregnum);

    @Query(value = "SELECT t.cnum as cnum FROM r_bank b, r_base bs, nsi_p_tel t" +
            "  where b.cbase = bs.ccode and bs.cnum = t.ccode and cregnum=:cregnum " +
            " and b.CE_DATE > SYSDATE and t.ce_date > SYSDATE AND (b.CC_FL>=0) " +
            " AND (bs.CC_FL>=0) order by ctorf ", nativeQuery = true)
    List<String> findPhone(Integer cregnum);


 /*   @Query(value = "select distinct p.CLNAME || ' ' || p.CNAME || ' ' || p.CPATR as fio, cb_sogl, e.CB_DATE as in_date, e.cpost as emp_cpost" +
            "  from r_bank b, r_base bs, nsi_employ e, nsi_person p where cregnum=:cregnum " +
            " and p.ccode = e.cperson and bs.ce_date >sysdate and bs.ccode = b.cbase " +
            " and e.cbase  = bs.ccode and e.ce_date >sysdate and p.ce_date >sysdate " +
            " and b.ce_date >sysdate and  cpost = :cpost and CREJECT = 0  and e.cc_fl >=0 order by fio", nativeQuery = true)
    List<Map<String, Object>> findPerson(Integer cregnum, Integer cpost);*/

    @Query(value = "select distinct p.CLNAME || ' ' || p.CNAME || ' ' || p.CPATR as fio, to_char(cb_sogl, 'fxDD.MM.YY') as cb_sogl," +
            "TO_CHAR(e.CB_DATE, 'fxDD.MM.YY') as in_date, e.cpost as emp_cpost" +
            "  from r_bank b, r_base bs, nsi_employ e, nsi_person p where cregnum=:cregnum " +
            " and p.ccode = e.cperson and bs.ce_date >sysdate and bs.ccode = b.cbase " +
            " and e.cbase  = bs.ccode and e.ce_date >sysdate and p.ce_date >sysdate " +
            " and b.ce_date >sysdate and CREJECT = 0  and e.cc_fl >=0 order by fio", nativeQuery = true)
    List<Map<String, Object>> findPerson(Integer cregnum);

    @Query(value = "SELECT * FROM nsi_kgs.k_capital WHERE (CE_DATE > SYSDATE) AND (CBASE = :cbase) AND (CC_FL >=0)", nativeQuery = true)
    List<Map<String, Object>> findCapital(Integer cbase);

    @Query(value = "SELECT employ.CCODE as CCODE, person.ccode as pers_code, employ.cpost as emp_cpost, " +
            " (person.CLNAME || ' ' || person.CNAME || ' ' || DECODE(person.CPATR, '0', '', '-', '', person.CPATR)) as CFULLNAME, " +
            " DECODE(employ.CPOST, 30, 'Председатель Совета директоров', 31, 'Член Совета директоров') as Priz_decode," +
            "    DECODE(sovet.CTYPE, 'Ф', 'Физическое лицо', 'ф', 'Физическое лицо', sovet.CMESTO) as MESTO," +
            " DECODE(sovet.CTYPE, 'Ф', '-', 'ф', '-', sovet.CDOLJN) as DOLJN, DECODE(sovet.CTYPE, 'Ф', '-', " +
            " 'ф', '-', DECODE(sovet.CADRES, '0', '-', sovet.CADRES)) as ADRES, DECODE(sovet.CNUM, '0', '-', NVL(sovet.CNUM, '-')) as Telefon, " +
            " TO_CHAR(employ.CB_DATE, 'fxDD.MM.YY') as IN_DATE,DECODE (NVL(s_ca_date,TO_CHAR(employ.CB_DATE,'fxDD.MM.YY')), " +
            " TO_CHAR(employ.CB_DATE, 'fxDD.MM.YY'), '-', s_ca_date) as Pereiz_date" +
            "    FROM (SELECT TO_CHAR(Max(s.ca_date), 'fxDD.MM.YY') as s_ca_date, s.cemploy as s_cemploy " +
            "    FROM nsi_kgs.nsi_sovpr s WHERE (s.CC_FL >=0) AND (s.cbase = :cbase) GROUP BY s.cemploy) sovpr, " +
            " nsi_kgs.nsi_employ employ, nsi_kgs.nsi_person person, nsi_kgs.nsi_sovet sovet  WHERE (employ.CBASE = :cbase) " +
            "    AND (employ.CPERSON = person.CCODE) AND (employ.ccode = sovet.cemploy) And (person.CC_FL >=0) " +
            " AND (sovet.cc_fl>=0) AND (employ.CC_FL >=0) AND (person.CE_DATE > SYSDATE) AND ((employ.CPOST = 30) OR (employ.CPOST = 31)) " +
            "  AND (employ.CB_DATE < employ.CE_DATE) and (person.cb_date < person.ce_date) AND (sovet.ce_date > sysdate) " +
            " AND (employ.ce_date > SYSDATE)  AND (sovpr.s_cemploy(+) = employ.ccode) AND (employ.creject = 0) " +
            "    ORDER BY employ.CPOST, person.CLNAME ", nativeQuery = true)
    List<Map<String, Object>> findSovet(Integer cbase);

    //qryListLicen
    @Query(value = "Select  MIN_CDATEP, MAX_CDATEP, CLSTATE, (TBLIC.CABRV || DECODE(R_LICEN.CLSTATE," +
            " 0, ' (действующая)', " +
            " 1, ' (отозванная)', " +
            " 2, ' (приост. отзыв)'," +
            " 3, ' (история)'," +
            " 4, ' (история)'," +
            " 5, ' (история)'," +
            " 6, ' (аннулированная)')) " +
            " as CABRV, DECODE(R_LICEN.CLSTATE, 0, '', 3, '', CDOCNUM) as CDOCNUM " +
            " FROM NSI_KGS.R_LICEN R_LICEN, NSI_KGS.NSI_TBLIC TBLIC, " +
            " ( select to_char(min(licen.cv_date), 'fxDD.MM.YYYY') as min_cdatep, " +
            " DECODE(TO_CHAR(Max(LICEN.CV_DATE), 'fxDD.MM.YYYY'), TO_CHAR(Min(LICEN.CV_DATE), 'fxDD.MM.YYYY'), '-', TO_CHAR(Max(LICEN.CV_DATE), 'fxDD.MM.YYYY')) AS Max_CDATEP, " +
            " licen.ctblic, bank.cregnum, max(licen.cb_date) as m_cbd, max(licen.ce_date) as m_ced, licen.cbase" +
            " from nsi_kgs.r_licen licen, nsi_kgs.k_bank bank where (bank.ce_date > sysdate) and (bank.cc_fl >=0) and (bank.cregnum = :cregnum) " +
            " and (licen.cc_fl >= 0) and (licen.cbase = bank.cbase) GROUP BY bank.cregnum, licen.ctblic, licen.cbase ) lic " +
            " WHERE (TBLIC.CE_DATE > SYSDATE) AND (TBLIC.CC_FL >= 0) AND (lic.m_cbd = r_licen.cb_date) and (lic.m_ced = r_licen.ce_date)  " +
            " AND (LIC.CTBLIC = TBLIC.CCODE) and (lic.cbase = r_licen.cbase) and (r_licen.cc_fl >=0) and (r_licen.ctblic = lic.ctblic) " +
            " order by r_licen.cdatep desc ", nativeQuery = true)
    List<Map<String, String>> findLicen(Integer cregnum);

    //qryLastLicen
    @Query(value = "SELECT DISTINCT R_LICEN.CLSTATE as CLSTATE, R_LICEN.CDOCNUM as CDOCNUM, TO_CHAR(R_LICEN.CDATEP, 'fxdd.mm.yyyy') as CDATEP " +
            " FROM NSI_KGS.R_BANK R_BANK, NSI_KGS.R_LICEN R_LICEN " +
            " WHERE (R_BANK.CE_DATE > SYSDATE) " +
            " AND (R_BANK.CBASE = R_LICEN.CBASE) AND (R_LICEN.CC_FL >= 0) AND (R_BANK.CC_FL >= 0) " +
            " AND (R_BANK.CREGNUM = :cregnum) AND (R_LICEN.CE_DATE>SYSDATE) ", nativeQuery = true)
    List<Map<String, Object>> findLastLicen(Integer cregnum);

    //qryCommentLicen
    @Query(value = "SELECT TBLIC.CABRV as LICEN, TOPER.CNAME as OGR " +
            " FROM NSI_KGS.R_BANK R_BANK, NSI_KGS.R_LICEN R_LICEN, NSI_KGS.R_LOPER R_LOPER, NSI_KGS.NSI_TOPER TOPER, NSI_KGS.NSI_TBLIC TBLIC " +
            " WHERE (R_BANK.CREGNUM = :cregnum) " +
            "  AND (R_LICEN.CBASE = R_BANK.CBASE) AND (R_LOPER.CTOPER = TOPER.CCODE) " +
            "  AND (R_LICEN.CTBLIC = TBLIC.CCODE) AND (R_LOPER.CLICEN = R_LICEN.CCODE) " +
            "  AND (R_LOPER.CE_DATE >SYSDATE) AND (R_BANK.CE_DATE > SYSDATE) " +
            "  AND (R_LICEN.CE_DATE > SYSDATE) AND (TBLIC.CE_DATE > SYSDATE) " +
            "  AND (TOPER.CE_DATE > SYSDATE) " +
            "  AND (R_LICEN.CC_FL >=0) AND (R_LOPER.CC_FL >= 0) " +
            "  AND (R_BANK.CC_FL >=0) AND (TBLIC.CC_FL >=0) " +
            "  AND (TOPER.CC_FL >=0)", nativeQuery = true)
    List<Map<String, String>> findCommentLicen(Integer cregnum);

    @Query(value = "SELECT count(*) count FROM r_bdept  WHERE (CE_DATE > SYSDATE)" +
            " AND (CMAIN = :cbase) AND (CC_FL >= 0)  AND (CGOS = 643)", nativeQuery = true)
    Map<String, String> findCountFilRF(Integer cbase);

    @Query(value = "SELECT count(*) count FROM r_bdept  WHERE (CE_DATE > SYSDATE)" +
            " AND (CMAIN = :cbase) AND (CC_FL >= 0)  AND (CGOS <> 643)", nativeQuery = true)
    Map<String, String> findCountFilFor(Integer cbase);

    @Query(value = "SELECT count(*) count FROM r_repr  WHERE (CE_DATE > SYSDATE)" +
            " AND (CMAIN = :cbase) AND (CC_FL >= 0)  AND (CGOS = 643)", nativeQuery = true)
    Map<String, String> findCountPredRF(Integer cbase);

    @Query(value = "SELECT count(*) count FROM r_repr  WHERE (CE_DATE > SYSDATE)" +
            " AND (CMAIN = :cbase) AND (CC_FL >= 0)  AND (CGOS <> 643)", nativeQuery = true)
    Map<String, String> findCountPredFor(Integer cbase);

    @Query(value = "Select Count(ccode) as Sum_Dop FROM " +
            " (select r_dopo.ccode  from nsi_kgs.r_dopo r_dopo, nsi_kgs.k_bdept k_bdept " +
            " where (r_dopo.ce_date > SYSDATE) and (r_dopo.cmain = k_bdept.cbase) AND " +
            " (k_bdept.cmain = :cbase)  and k_bdept.cc_fl >=0 and r_dopo.cc_fl >=0 " +
            "  UNION " +
            "  SELECT ccode FROM nsi_kgs.r_dopo r_dopo" +
            " WHERE (r_dopo.CE_DATE > SYSDATE) AND (r_dopo.CMAIN = :cbase)  and r_dopo.cc_fl >=0)", nativeQuery = true)
    Map<String, String> findCountDopOf(Integer cbase);

    @Query(value = "Select count(ccode) Sum_OPC From " +
            " ( select r_opc.ccode from nsi_kgs.r_opc r_opc, nsi_kgs.k_bdept k_bdept " +
            " where (r_opc.ce_date > SYSDATE) and (r_opc.cmain = k_bdept.cbase) AND" +
            " (k_bdept.cmain = :cbase) and r_opc.cc_fl>=0  and k_bdept.cc_fl>=0 UNION SELECT ccode FROM nsi_kgs.r_opc r_opc " +
            " WHERE (r_opc.CE_DATE > SYSDATE) AND (r_opc.CMAIN = :cbase) and r_opc.cc_fl>=0 )", nativeQuery = true)
    Map<String, String> findCountOPC(Integer cbase);

    @Query(value = "select count(*) SUM_KKO from r_kko where cmain = :cbase and ce_date > sysdate and cc_fl>=0", nativeQuery = true)
    Map<String, String> findCountKKO(Integer cbase);

    @Query(value = "Select Count(ccode) as Sum_OPO FROM ( select r_opo.ccode  from nsi_kgs.r_opo r_opo, nsi_kgs.k_bdept k_bdept" +
            " where (r_opo.ce_date > SYSDATE) and (r_opo.cmain = k_bdept.cbase) AND (k_bdept.cmain = :cbase) " +
            " and k_bdept.cc_fl>=0 and r_opo.cc_fl>=0" +
            " UNION " +
            " SELECT ccode FROM nsi_kgs.r_opo r_opo  WHERE (r_opo.CE_DATE > SYSDATE) " +
            " AND (r_opo.CMAIN = :cbase) and cc_fl>=0)", nativeQuery = true)
    Map<String, String> findCountOPO(Integer cbase);

    @Query(value = "Select Count(ccode) as Sum_PPKO FROM ( select r_ppko.ccode  from nsi_kgs.r_ppko r_ppko, nsi_kgs.k_bdept k_bdept" +
            " where (r_ppko.ce_date > SYSDATE) and (r_ppko.cmain = k_bdept.cbase) AND (k_bdept.cmain = :cbase) " +
            " and k_bdept.cc_fl>=0 and r_ppko.cc_fl>=0" +
            " UNION " +
            " SELECT ccode FROM nsi_kgs.r_ppko r_ppko  WHERE (r_ppko.CE_DATE > SYSDATE) " +
            " AND (r_ppko.CMAIN = :cbase) and cc_fl>=0)", nativeQuery = true)
    Map<String, String> findCountPPKO(Integer cbase);

    @Query(value = "select distinct date_in, date_out " +
            " FROM nsi_kgs.list_rssv ssv, nsi_kgs.k_bank b" +
            " WHERE (ssv.regn=b.CREGNUM) AND (b.CREGNUM = :cregnum) ", nativeQuery = true)
    Map<String, Object> findStrah(Integer cregnum);
// Участники
    @Query(value = "SELECT rownum, (PR_N1 || ' ' || PR_N2 || ' ' || PR_N3) as PR_NAME, " +
            " (RTU_N1 || ' ' || Nsi_r_uch_conf_view.RTU_N2 || ' ' || RTU_N3) as RTU_NAME, " +
            " (TO_CHAR(DT_PRIN, 'fxdd.mm.yyyy')) as DT_PRIN, " +
            " (TO_CHAR(DT_PRIN, 'yyyy')) as DTy, " +
            " (TO_CHAR(DT_PRIN, 'mm')) as DTm, " +
            " (TO_CHAR(DT_PRIN, 'fxdd')) as DTdd, " +
            " (TO_CHAR(CB_DATE, 'fxdd.mm.yyyy')) as CB_DATE, " +
            " (TO_CHAR(CE_DATE, 'fxdd.mm.yyyy')) as CE_DATE, " +
            " A_COUNT, T_COUNT, CMAKET, CMAKET_E " +
            " FROM NSI_KGS.R_UCH_CONF Nsi_r_uch_conf_view " +
            " WHERE  (REGN = :cregnum) " +
 //           "--&m_Date  " +
            " AND (cc_fl >=0) " +
            " ORDER BY dty DESC, dtm DESC, dtdd DESC", nativeQuery = true)
    List<Map<String, Object>> findUch(Integer cregnum);

// qryKO
    // cmaket должен приходить из другой формы по урлу? а cmaket_e ?
    @Query(value = "SELECT rownum, DECODE(r_uchast.PRIZ_TIP, 1, 'физ. лицо', 'юр. лицо') || " +
            " DECODE(r_uchast.PRIZ_RES, 2, ' нерезидент', '') as TIP, " +
            " (NVL(r_uchast.NAMEUCH, r_uchast.NAME1 || ' ' || r_uchast.NAME2 || ' ' || r_uchast.NAME3)) as NAME, " +
            " DECODE(r_uchast.PRIZ_TIP, 1, nvl(gos.short_name, ''), (r_uchast.STREET || '; ' || r_uchast.NAME1 || " +
            "' ' || r_uchast.NAME2 || ' ' || r_uchast.NAME3 || ', ' || r_uchast.TEL)) as Address, " +
            " r_uchast.DOL as DOL_money, " +
            " DECODE(TO_CHAR(ROUND(r_uchast.DOL, 3), 'fm990.099'), '0.0', 'менее 0.001', " +
            " TO_CHAR(ROUND(r_uchast.DOL, 3), 'fm990.099')) as DOL, " +
            " DECODE (r_uchast.PRIZ_UCH, 2, 'учредитель', 'участник') as Priz_Uch " +
            " FROM NSI_KGS.R_UCHAST_VIEW r_uchast, nsi_kgs.list_gos gos " +
            " WHERE   (r_uchast.REGN = :cregnum) AND (r_uchast.CC_FL>=0) AND (gos.iso_lat3(+) = r_uchast.country) " +
            " and ((gos.end_date > sysdate) or (gos.end_date is null)) " +
            " AND (r_uchast.CMAKET <= :cmaket_e)  AND (r_uchast.CMAKET_E >= :cmaket)  AND ((r_uchast.Dol > 1.0) OR (r_uchast.PRIZ_TIP = 3) OR (r_uchast.PRIZ_RES = 2)) " +
            " ORDER BY r_uchast.DOL DESC, name ", nativeQuery = true)
    List<Map<String, Object>> findKO(Integer cregnum, String cmaket, String cmaket_e);

    //qryUtv11
    @Query(value = "SELECT (PR_N1 || ' ' || PR_N2 || ' ' || PR_N3) as PR_NAME, " +
            " (RTU_N1 || ' ' || Nsi_r_uch_conf_view.RTU_N2 || ' ' || RTU_N3) as RTU_NAME, " +
            " (TO_CHAR(DT_PRIN, 'fxdd.mm.yyyy')) as DT_PRIN, " +
            " (TO_CHAR(DT_PRIN, 'yyyy')) as DTy, " +
            " (TO_CHAR(DT_PRIN, 'mm')) as DTm, " +
            " (TO_CHAR(DT_PRIN, 'fxdd')) as DTdd, " +
            " (TO_CHAR(CB_DATE, 'fxdd.mm.yyyy')) as CB_DATE, " +
            " (TO_CHAR(CE_DATE, 'fxdd.mm.yyyy')) as CE_DATE, " +
            " A_COUNT, T_COUNT, CMAKET, CMAKET_E " +
            " FROM NSI_KGS.R_UCH_CONF Nsi_r_uch_conf_view " +
            " WHERE  (REGN = :cregnum) " +
            " AND (CMAKET = :cmaket) " +
            " AND (cc_fl >=0) " +
            " ORDER BY dty DESC, dtm DESC, dtdd DESC ", nativeQuery = true)
    List<Map<String, Object>> findUch11(Integer cregnum, String cmaket);

    @Query(value = "SELECT COUNT (CCODE) as a  " +
            " FROM R_UCHAST_VIEW Nsi_r_uchast_view " +
            " WHERE   (REGN = :cregnum) AND (CC_FL>=0) " +
            " AND (NSI_r_uchast_view.CMAKET <= :cmaket_e  AND (nsi_r_uchast_view.CMAKET_E >= :cmaket) ", nativeQuery = true)
    Integer findCountUch(Integer cregnum, String cmaket, String cmaket_e);


//    @Query(value = " ", nativeQuery = true)




    // филиалы
    @Query(value = "SELECT Filials.CNAME, Filials.CREGNUM, " +
            "            (Personal.CLNAME || ' ' || Personal.CNAME || ' ' || Personal.CPATR) as Predsedatel, Addr.CAddr, " +
            "    TO_CHAR(rbase.cb_date, 'fxDD.MM.YY') as CB_DATE " +
            "    FROM nsi_kgs.k_bdept Filials, nsi_kgs.r_base RBase, nsi_kgs.nsik_addr Addr, " +
            "(SELECT employ.cbase, person.clname, person.cname, person.cpatr FROM nsi_kgs.nsi_employ Employ, nsi_kgs.nsi_person Person " +
            "    WHERE (Employ.CPERSON = Person.CCODE) AND (Employ.CPOST = 7) AND (Employ.CE_DATE > SYSDATE) AND (PERSON.CE_DATE > SYSDATE) " +
            "    AND (Employ.CC_FL >= 0) AND (person.CC_FL >=0)) personal " +
            "    WHERE (Personal.CBASE(+) = Filials.CBASE) AND (Filials.CBASE = RBASE.CCODE) " +
            "    AND (Rbase.CADDRMN = Addr.CCode) " +
            "    AND (Filials.CE_DATE > SYSDATE) AND (RBASE.CE_DATE > SYSDATE) " +
            "    AND (Addr.CE_DATE > SYSDATE) " +
            "    AND (Filials.CC_FL >=0) " +
            "    AND (Filials.CMAIN = :cbase) " +
            //  "&Cond_Gos " +
            "    ORDER BY filials.CREGNUM ", nativeQuery = true)
    List<Map<String, Object>> findFilials(Integer cbase);

    // Всего  филиалов
    @Query(value = " SELECT count(*) count FROM nsi_kgs.k_bdept " +
            " WHERE (CE_DATE > SYSDATE) AND (CMAIN = :cbase) AND (CC_FL >= 0) ", nativeQuery = true)
    Integer findQuantity(Integer cbase);

    // Свободный номер
    @Query(value = " SELECT (MAX(CREGNUM)+1) as FreeNum FROM nsi_kgs.k_bdept " +
            " WHERE (CMAIN = :cbase) AND (CC_FL >= 0)", nativeQuery = true)
    Integer findFreeNum(Integer cbase);

    // Фактический адрес
    @Query(value = " SELECT caddr FROM r_base r, nsik_addr a " +
            " WHERE (r.CCODE = :cbase) AND (r.CE_DATE > SYSDATE) " +
            " and (a.CE_DATE > SYSDATE) AND (a.CCODE = r.CADDRMN) AND (a.CC_FL >=0) ", nativeQuery = true)
    String findAddr(Integer cbase);

    // Руководитель
    @Query(value = " SELECT  (CLNAME || ' ' || CNAME || ' ' || CPATR) as CFULLNAME FROM nsi_employ e, nsi_person p " +
            " WHERE (e.CE_DATE > SYSDATE) AND (e.CBASE = :cbase) AND (e.CPOST = :cpost) " +
            " AND (e.CC_FL >= 0) AND (e.CREJECT = 0) " +
            " and (p.CE_DATE > SYSDATE) AND (p.CCODE = e.cperson) AND (p.CC_FL >= 0)", nativeQuery = true)
    String findChief(Integer cbase, Integer cpost);

    //qryListOpof операционные офисы
    @Query(value = " select rownum, oc.cname, prinad, TO_CHAR(cn_date, 'fxDD.MM.YY') as cn_date, addr.caddr" +
            //     "select oc.*, addr.caddr " +
            "        from nsi_kgs.nsi_addr addr, nsi_kgs.r_base base, " +
            "        (select opc.ccode, opc.cb_date, opc.cbase, opc.cgu, opc.cmain, 'KO' as prinad, opc.cname, opc.cn_date " +
            "        from nsi_kgs.r_opo opc " +
            "        where (opc.cc_fl >= 0) and (opc.ce_date > SYSDATE) and (opc.cb_date < opc.ce_date) " +
            "        and (opc.ctoff = 1) and (opc.cmain = :cbase) " +
            "        union " +
            "        select o.ccode, o.cb_date, o.cbase, o.cgu, o.cmain, ('(филиал) ' || fil.cname) as prinad, o.cname, o.cn_date " +
            "        from nsi_kgs.r_opo o, nsi_kgs.r_bdept fil " +
            "        where (o.cc_fl >= 0) and (o.ce_date > SYSDATE) and (o.cb_date < o.ce_date) " +
            "        and (o.ctoff = 2) and (o.cmain = fil.cbase) and (fil.ce_date > SYSDATE) " +
            "        and (fil.cc_fl >=0) and (fil.cb_date < fil.ce_date) and (fil.cmain = :cbase) " +
            "        ) oc " +
            "        where (oc.cbase = base.ccode) and (base.curaddr = addr.ccode) and (base.ce_date >= sysdate) " +
            "        and (base.cc_fl >= 0) and (base.cb_date < base.ce_date) and (addr.cc_fl>=0) " +
            "        and (addr.ce_date >sysdate) and (addr.cb_date < addr.ce_date) ", nativeQuery = true)
    List<Map<String, Object>> findOpof(Integer cbase);

    // qryListDopof доп офисы
    @Query(value = "select rownum, dop.cname, dop.prinad,  TO_CHAR(cn_date, 'fxDD.MM.YY') as cn_date, addr.caddr " +
            " from nsi_kgs.nsi_addr addr, nsi_kgs.r_base base, " +
            " (select dopo.ccode, dopo.cb_date, dopo.cbase, dopo.cgu, dopo.cmain, 'KO' as prinad, dopo.cname, dopo.cn_date " +
            " from nsi_kgs.r_dopo dopo " +
            " where (dopo.cc_fl >= 0) and (dopo.ce_date > SYSDATE) and (dopo.cb_date < dopo.ce_date) " +
            " and (dopo.ctoff = 1) and (dopo.cmain = :cbase) " +
            " union " +
            " select d.ccode, d.cb_date, d.cbase, d.cgu, d.cmain, ('(филиал) ' || fil.cname) as prinad, d.cname, d.cn_date " +
            " from nsi_kgs.r_dopo d, nsi_kgs.r_bdept fil " +
            " where (d.cc_fl >= 0) and (d.ce_date > SYSDATE) and (d.cb_date < d.ce_date) " +
            " and (d.ctoff = 2) and (d.cmain = fil.cbase) and (fil.ce_date > SYSDATE) " +
            " and (fil.cc_fl >=0) and (fil.cb_date < fil.ce_date) and (fil.cmain = :cbase)) dop " +
            " where (dop.cbase = base.ccode) and (base.curaddr = addr.ccode) and (base.ce_date >= sysdate) " +
            " and (base.cc_fl >= 0) and (base.cb_date < base.ce_date) and (addr.cc_fl>=0) " +
            " and (addr.ce_date >sysdate) and (addr.cb_date < addr.ce_date)", nativeQuery = true)
//            " order by cname ",nativeQuery = true)
    List<Map<String, Object>> findDopof(Integer cbase);

    // операционные кассы
    @Query(value = " select rownum, oc.cname, oc.prinad,  TO_CHAR(cn_date, 'fxDD.MM.YY') as cn_date, addr.caddr " +
            " from nsi_kgs.nsi_addr addr, nsi_kgs.r_base base, " +
            " (select opc.ccode, opc.cb_date, opc.cbase, opc.cgu, opc.cmain, 'KO' as prinad, opc.cname, opc.cn_date " +
            " from nsi_kgs.r_opc opc " +
            " where (opc.cc_fl >= 0) and (opc.ce_date > SYSDATE) and (opc.cb_date < opc.ce_date) " +
            " and (opc.ctoff = 1) and (opc.cmain = :cbase) " +
            " union " +
            " select o.ccode, o.cb_date, o.cbase, o.cgu, o.cmain, ('(филиал) ' || fil.cname) as prinad, o.cname, o.cn_date " +
            " from nsi_kgs.r_opc o, nsi_kgs.r_bdept fil " +
            " where (o.cc_fl >= 0) and (o.ce_date > SYSDATE) and (o.cb_date < o.ce_date) " +
            " and (o.ctoff = 2) and (o.cmain = fil.cbase) and (fil.ce_date > SYSDATE) " +
            " and (fil.cc_fl >=0) and (fil.cb_date < fil.ce_date) and (fil.cmain = :cbase) " +
            " ) oc " +
            " where (oc.cbase = base.ccode) and (base.curaddr = addr.ccode) and (base.ce_date >= sysdate) " +
            " and (base.cc_fl >= 0) and (base.cb_date < base.ce_date) and (addr.cc_fl>=0) " +
            " and (addr.ce_date > sysdate) and (addr.cb_date < addr.ce_date) ", nativeQuery = true)
    List<Map<String, Object>> findOpoc(Integer cbase);

    @Query(value = "select  rownum, oc.cname, oc.prinad,  TO_CHAR(cn_date, 'fxDD.MM.YY') as cn_date, addr.caddr " +
            " from nsi_kgs.nsi_addr addr, nsi_kgs.r_base base, " +
            " (select opc.ccode, opc.cb_date, opc.cbase, opc.cgu, opc.cmain, 'KO' as prinad, opc.cname, opc.cn_date " +
            " from nsi_kgs.r_kko opc " +
            " where (opc.cc_fl >= 0) and (opc.ce_date > SYSDATE) and (opc.cb_date < opc.ce_date) " +
            " and (opc.ctoff = 1) and (opc.cmain = :cbase) union " +
            " select o.ccode, o.cb_date, o.cbase, o.cgu, o.cmain, ('(филиал) ' || fil.cname) as prinad, o.cname, o.cn_date " +
            " from nsi_kgs.r_kko o, nsi_kgs.r_bdept fil " +
            " where (o.cc_fl >= 0) and (o.ce_date > SYSDATE) and (o.cb_date < o.ce_date) " +
            " and (o.ctoff = 2) and (o.cmain = fil.cbase) and (fil.ce_date > SYSDATE) " +
            " and (fil.cc_fl >=0) and (fil.cb_date < fil.ce_date) and (fil.cmain = :cbase)) oc " +
            " where (oc.cbase = base.ccode) and (base.curaddr = addr.ccode) and (base.ce_date >= sysdate) " +
            " and (base.cc_fl >= 0) and (base.cb_date < base.ce_date) and (addr.cc_fl>=0) " +
            " and (addr.ce_date >sysdate) and (addr.cb_date < addr.ce_date)", nativeQuery = true)
    List<Map<String, Object>> findKko(Integer cbase);

    // представительства
    @Query(value = " SELECT pr.cbase, pr.cmain, pr.cname, nvl(gu.cname, '') as gu_name, nvl(gos.short_name, '')  as gos_name, " +
            "to_char(pr.cdreg, 'fxdd.mm.yy') as cdreg, ad.caddr, pr.ctoff, pr.cgos, " +
            "decode((Personal.CLNAME || ' ' || Personal.CNAME || ' ' || Personal.CPATR),'  ', 'Данные отсутствуют', " +
            "                                  (Personal.CLNAME || ' ' || Personal.CNAME || ' ' || Personal.CPATR) ) as Predsedatel " +
            "FROM nsi_kgs.k_repr pr, nsi_kgs.list_kp gu, nsi_kgs.list_gos gos, nsi_kgs.nsi_addr ad, " +
            "nsi_kgs.r_base base, " +
            " " +
            "(SELECT employ.cbase, person.clname, person.cname, person.cpatr FROM nsi_kgs.nsi_employ Employ, nsi_kgs.nsi_person Person " +
            "WHERE (Employ.CPERSON = Person.CCODE) AND (Employ.CPOST = 11) AND (Employ.CE_DATE > SYSDATE) AND (PERSON.CE_DATE > SYSDATE) " +
            "AND (Employ.CC_FL >= 0) AND (person.CC_FL >=0)) personal " +
            " " +
            "WHERE (pr.CE_DATE > SYSDATE) and (pr.cmain = :cbase) " +
            "and (pr.cgu = gu.ccode(+)) and ((gu.ce_date > sysdate) or (gu.ce_date is null)) " +
            "and (gu.cb_date < gu.ce_date) " +
            "and (pr.cgos = gos.iso_dig(+)) and ((gos.end_date > sysdate) or (gos.end_date is null)) " +
            "and (gos.begin_date < gos.end_date) " +
            "and (pr.cbase = base.ccode) and (base.ce_date > sysdate) and (base.cc_fl >=0) and (base.cb_date < base.ce_date) " +
            "and (base.curaddr = ad.ccode) and (ad.cc_fl >=0) and (ad.cb_date < ad.ce_date) and (ad.ce_date > sysdate) " +
            "and (Personal.CBASE(+) = pr.CBASE)", nativeQuery = true)
    List<Map<String, Object>> findPreds(Integer cbase);
}



        