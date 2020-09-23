package ru.cbr.tomsk.kgr.service;

import ru.cbr.tomsk.kgr.entity.RBase;
import ru.cbr.tomsk.kgr.repository.RBase_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RBaseService {
    @Autowired
    private RBase_repo rbase_repo;

    public RBaseService(RBase_repo rbase_repo) {
        this.rbase_repo = rbase_repo;
    }

    public RBase getRBase(Integer cbase) {
        List<RBase> resultRBase = rbase_repo.findAll(cbase);
        if (resultRBase.size() != 0) {
            RBase rbase = resultRBase.get(0);
            return rbase;
        } else return null;


    }
}
