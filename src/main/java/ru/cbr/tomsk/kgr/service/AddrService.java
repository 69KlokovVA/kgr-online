package ru.cbr.tomsk.kgr.service;

import ru.cbr.tomsk.kgr.entity.Addr;
import ru.cbr.tomsk.kgr.repository.Addr_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddrService {
    @Autowired
    private Addr_repo addr_repo;

    public AddrService(Addr_repo addr_repo) {
        this.addr_repo = addr_repo;
    }

    public Addr getAddr(Integer caddr) {
        List<Addr> resultAddr = addr_repo.findAll(caddr);
        if (resultAddr.size() != 0) {
            Addr addr = resultAddr.get(0);
            return addr;
        } else return null;


    }
}
