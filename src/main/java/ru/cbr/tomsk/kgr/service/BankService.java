package ru.cbr.tomsk.kgr.service;

import ru.cbr.tomsk.kgr.entity.Bank;
import ru.cbr.tomsk.kgr.repository.Bank_repo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class BankService {
    @Autowired
    private Bank_repo bank_repo;
    private List<Map<String, Object>> personList;

    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public BankService(Bank_repo bank_repo) {
        this.bank_repo = bank_repo;
    }

    public Map<String, Object> getBank(Integer cregnum) {
        List<Map<String, Object>> resultBank = bank_repo.findByCregnum(cregnum);
        Map<String, Object> bank = new HashMap<>();
        if (resultBank.size() != 0) {
            bank = resultBank.get(0);
            return bank;
        } else return null;
    }

    public String getTU(Integer cregnum) {
        List<String> tuList = bank_repo.findTU(cregnum);
        String tu = tuList.get(0);
        return tu;
    }

    public String getPhone(Integer cregnum) {
        List<String> phoneList = bank_repo.findPhone(cregnum);
        String phone = phoneList.stream().collect(Collectors.joining("; "));
        return phone;
    }

    public Map<String, Object> getPerson(Integer cregnum) {
        List<Map<String, Object>> personList = bank_repo.findPerson(cregnum);
        Map<String, Object> personNew = new HashMap<>();
        List<String> pList4 = new ArrayList<>();
        List<String> pList5 = new ArrayList<>();
        List<String> pList6 = new ArrayList<>();
        List<String> pList31 = new ArrayList<>();
        List<Map<String, Object>> persons = new ArrayList<>();
        personNew.put("vrioeio","");
        personNew.put("vriogbuh", "");
        String s;
        for (Map<String, Object> person : personList) {
            BigDecimal cpost1 = (BigDecimal) person.get("EMP_CPOST");
            int cpost= cpost1.intValue();
            switch (cpost) {
                case 1: {
                    personNew.put("Predsedatel", person.get("fio"));
                    personNew.put("DateSoglPred", person.get("cb_sogl"));
                    break;
                }
                case 2: {
                    personNew.put("GlavBuh", person.get("fio"));
                    personNew.put("DateSoglGBuh", person.get("cb_sogl"));
                    break;
                }
                case 4: {
                    pList4.add(person.get("fio").toString());
                    break;
                }
                case 5: {
                    pList5.add(person.get("fio").toString());
                    break;
                }
                case 6: {
                    pList6.add(person.get("fio").toString());
                    break;
                }
                case 427: {
                    personNew.put("vrioeio", person.get("fio"));
                    break;
                }
                case 429: {
                    personNew.put("vriogbuh", person.get("fio"));
                    break;
                }
                case 30: {
                    personNew.put("predSovet", person.get("fio"));
                    personNew.put("in_date", person.get("IN_DATE"));
                    break;
                }
                case 31: {
                    pList31.add(person.get("fio").toString());
                    break;
                }
            }
        }
        s = "";
        pList4 = pList4.stream().distinct().collect(Collectors.toList());
        for (String s1 : pList4) {
            s = s + s1 + "; ";
        }
        personNew.put("zampreds", s);
        persons.add(personNew);
        s = "";
        pList5 = pList5.stream().distinct().collect(Collectors.toList());
        for (String s1 : pList5) {
            s = s + s1 + "; ";
        }
        personNew.put("pravl", s);
        persons.add(personNew);
        s = "";
        pList6 = pList6.stream().distinct().collect(Collectors.toList());
        for (String s1 : pList6) {
            s = s + s1 + "; ";
        }
        personNew.put("zamgbuhs", s);
        persons.add(personNew);
        s = "";
        pList31 = pList31.stream().distinct().collect(Collectors.toList());
        for (String s1 : pList31) {
            s = s + s1 + "; ";
        }
        personNew.put("fioSovet", s);

        return personNew;
    }

    public Map<String, String> getUstav(Integer cbase) {
        List<Map<String, Object>> ustavList = bank_repo.findCapital(cbase);
        Map<String, String> ustavNew = new HashMap<>();
        if (ustavList.size() != 0) {
            Map<String, Object> ustav = ustavList.get(0);
            ustavNew.put("UstCapital", ustav.get("CMONEY").toString());
            ustavNew.put("DateCapital", dateFormat.format(ustav.get("CD_MONEY")));

        } else {
            ustavNew.put("UstCapital", "-");
            ustavNew.put("DateCapital", "-");
        }
        return ustavNew;
    }

    public List<Map<String, String>> getLicen(Integer cregnum) {
        List<Map<String, String>> licenList = bank_repo.findLicen(cregnum);
        return licenList;
    }

    public List<Map<String, Object>> getLastLicen(Integer cregnum) {
        List<Map<String, Object>> lastLicenList = bank_repo.findLastLicen(cregnum);
        return lastLicenList;
    }

    public String getCommentLicen(Integer cregnum, Integer ctbank) {
        String comment = "";
        List<Map<String, Object>> lastLicenListSource = getLastLicen(cregnum);
//        List<Map<String, Object>> lastLicenList = lastLicenListSource.stream().filter(l -> l.get("CLSTATE").toString().equals("0")).collect(Collectors.toList());
        Boolean flag = false;
        for (Map<String, Object> p : lastLicenListSource) {
            if (p.get("CLSTATE").toString().equals("0")) {
                flag = true;
                break;
            }
        }
        String q2 = "Дополнительная информация по лицензии отсутствует";
//        Boolean flag = lastLicenList.size() !=0;
        if (flag & (ctbank == 1)) {
            q2 = "Организация является банком с универсальной лицензией";
            List<Map<String, String>> licenList = getLicen(cregnum).stream().filter(l -> l.get("CABRV").toUpperCase().contains("БАЗОВАЯ")).collect(Collectors.toList());
            if (licenList.size() != 0) {
                q2 = "Организация является банком с базовой лицензией";
            }
        } else if (flag) {
            q2 = "";
        }
        List<Map<String, String>> commentLicenList = bank_repo.findCommentLicen(cregnum);
        // в исходниках КГР-онлайн здесь явный залип. Уточнить алгоритм
        if (flag & commentLicenList.size() != 0) {
            comment = "";
            String finalComment = comment;
            commentLicenList.forEach(com -> finalComment.concat(com.get("LICEN").concat(com.get("OGR"))));

            comment = finalComment;
        }
        comment = q2 + comment;
        return comment;
    }

    public Map<String, String> getSovet(Integer cbase) {
        List<Map<String, Object>> personSovetList = bank_repo.findSovet(cbase);
        Map<String, String> personNew = new HashMap<>();
        List<String> pList = new ArrayList<>();
        String s = "";
        for (Map<String, Object> person : personSovetList) {
            if (person.get("EMP_CPOST").toString().equals("30")) {
                personNew.put("predSovet", person.get("CFULLNAME").toString());
                personNew.put("in_date", person.get("IN_DATE").toString());
            } else {
                pList.add(person.get("CFULLNAME").toString());
            }
        }
        pList = pList.stream().distinct().collect(Collectors.toList());
        for (String s1 : pList) {
            s = s + s1 + "; ";
        }
        personNew.put("fio", s);
        return personNew;
    }

    public Map<String, String> getCountFilRF(Integer cbase) {
        Map<String, String> countFilRF = bank_repo.findCountFilRF(cbase);
        return countFilRF;
    }

    public Map<String, String> getCountFilFor(Integer cbase) {
        Map<String, String> countFilFor = bank_repo.findCountFilFor(cbase);
        return countFilFor;
    }

    public Map<String, String> getCountPredRF(Integer cbase) {
        Map<String, String> countPredRF = bank_repo.findCountPredRF(cbase);
        return countPredRF;
    }

    public Map<String, String> getCountPredFor(Integer cbase) {
        Map<String, String> countPredFor = bank_repo.findCountPredFor(cbase);
        return countPredFor;
    }

    public Map<String, String> getCountDopOf(Integer cbase) {
        Map<String, String> countDopOf = bank_repo.findCountDopOf(cbase);
        return countDopOf;
    }

    public Map<String, String> getCountOPC(Integer cbase) {
        Map<String, String> countOPC = bank_repo.findCountOPC(cbase);
        return countOPC;
    }

    public Map<String, String> getCountKKO(Integer cbase) {
        Map<String, String> countKKO = bank_repo.findCountKKO(cbase);
        return countKKO;
    }

    public Map<String, String> getCountPPKO(Integer cbase) {
        Map<String, String> countPPKO = bank_repo.findCountPPKO(cbase);
        return countPPKO;
    }

    public Map<String, String> getCountOPO(Integer cbase) {
        Map<String, String> countOPO = bank_repo.findCountOPO(cbase);
        return countOPO;
    }

    public String getStrah(Integer cregnum) {
        Map<String, Object> strahMap = bank_repo.findStrah(cregnum);
/*
        q1 := qryStrah.FieldByName('date_in').AsString;
        q2 := qryStrah.FieldByName('date_out').AsString;
        if (q1 <> '') and (q2 = '') then q1 := 'Да'
        else  q1 := 'Нет';
*/
        String s1 = "";
        String s2 = "";
        if (strahMap.get("date_in") != null) {
            s1 = dateFormat.format(strahMap.get("date_in"));
        }
        if (strahMap.get("date_out") != null) {
            s2 = dateFormat.format(strahMap.get("date_out"));
        }
        if (!(s1.equals("")) & (s2.equals(""))) {
            s1 = "Да";
        } else s1 = "Нет";
        return s1;
    }
}
