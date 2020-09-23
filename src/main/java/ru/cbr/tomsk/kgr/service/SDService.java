package ru.cbr.tomsk.kgr.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.tomsk.kgr.repository.Bank_repo;
import ru.cbr.tomsk.kgr.repository.SD_repo;

import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
public class SDService {
    @Autowired
    private SD_repo sd_repo;

    public SDService(Bank_repo bank_repo) {
        this.sd_repo = sd_repo;
    }

    public List<Map<String, Object>> getSD(Integer cbase) {
        List<Map<String, Object>> sdNowList = sd_repo.findSD_now(cbase);
        return sdNowList;
    }
    public List<Map<String, Object>> getSDhist(Integer cbase) {
        List<Map<String, Object>> sdHistList = sd_repo.findSD_hist(cbase);
        return sdHistList;
    }
}
