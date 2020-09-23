package ru.cbr.tomsk.kgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cbr.tomsk.kgr.entity.Bank;
import ru.cbr.tomsk.kgr.service.BankService;
import ru.cbr.tomsk.kgr.service.DopService;

import java.util.Map;

@Controller
public class BranchController {
    private DopService dopService;
    private BankService bankService;

    public BranchController(DopService dopService, BankService bankService) {
        this.dopService = dopService;
        this.bankService = bankService;
    }

    @RequestMapping("/branch")
    public String Branch(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        Map<String, Object> branchList = dopService.getBranch(cbase, 1);
        model.put("bank", bank);
        model.put("branches", branchList);
        return "branch";
    }

}
