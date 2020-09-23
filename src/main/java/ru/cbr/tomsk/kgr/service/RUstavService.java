package ru.cbr.tomsk.kgr.service;

import ru.cbr.tomsk.kgr.entity.RUstav;
import ru.cbr.tomsk.kgr.repository.RUstav_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class RUstavService{
    @Autowired
    private RUstav_repo rUstav_repo;

    public RUstavService(RUstav_repo bank_repo) {
        this.rUstav_repo = rUstav_repo;
    }

    public List<Map<String, Object>> getUstav(Integer ccode)  {
        List<Map<String, Object>> resultRUstav = rUstav_repo.findByCcode(ccode);
  //      DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
  /*      resultRUstav.forEach(rUstav -> {
               if (rUstav.getCREASON() == null) {
                rUstav.setCREASON("Новая редакция");
            }
        });*/
     return resultRUstav;
    }
}

