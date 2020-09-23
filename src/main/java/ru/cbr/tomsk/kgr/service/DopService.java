package ru.cbr.tomsk.kgr.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.tomsk.kgr.repository.Bank_repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
public class DopService {
    @Autowired
    private Bank_repo bank_repo;

    public DopService(Bank_repo bank_repo) {
        this.bank_repo = bank_repo;
    }

    public List<Map<String, Object>> getUch(Integer cregnum) {
        List<Map<String, Object>> uchList = bank_repo.findUch(cregnum);
        return uchList ;
    }
    public List<Map<String, Object>> getUch11(Integer cregnum, String cmaket) {
        List<Map<String, Object>> uch11List = bank_repo.findUch11(cregnum, cmaket);
        return uch11List ;
    }
    public List<Map<String, Object>> getUch1(Integer cregnum, String cmaket, String cmaket_e) {
        List<Map<String, Object>> uch1List = bank_repo.findKO(cregnum, cmaket, cmaket_e);
        return uch1List ;
    }

    public Integer getGetCountUch(Integer cregnum, String cmaket, String cmaket_e) {
        Integer countUch = bank_repo.findCountUch(cregnum, cmaket, cmaket_e);
        return countUch;
    }

    public Map<String, Object> getBranch(Integer cbase, Integer cpost) {
        Map<String, Object> filials = new HashMap<>();
        List<Map<String, Object>> filialsList = bank_repo.findFilials(cbase);
        filials.put("branch", filialsList);
        Integer quantity = bank_repo.findQuantity(cbase);
        filials.put("quantity", quantity);
        Integer freenum = bank_repo.findFreeNum(cbase);
        filials.put("freenum",freenum);
        String addr = bank_repo.findAddr(cbase);
        filials.put("addr",addr);
        String chief = bank_repo.findChief(cbase, cpost);
        filials.put("chief",chief);
        return filials;
    }

    public List<Map<String, Object>> getOpof(Integer cbase) {
        List<Map<String, Object>> opofList = bank_repo.findOpof(cbase);
        return opofList ;
    }
    public List<Map<String, Object>> getDopof(Integer cbase) {
        List<Map<String, Object>> dopofList = bank_repo.findDopof(cbase);
        return dopofList ;
    }
    public List<Map<String, Object>> getOpoc(Integer cbase) {
        List<Map<String, Object>> opocList = bank_repo.findOpoc(cbase);
        return opocList ;
    }
    public List<Map<String, Object>> getKko(Integer cbase) {
        List<Map<String, Object>> kkoList = bank_repo.findKko(cbase);
        return kkoList ;
    }
    public List<Map<String, Object>> getPreds(Integer cbase) {
        List<Map<String, Object>> predsList = bank_repo.findPreds(cbase);
        return predsList ;
    }
}
