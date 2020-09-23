package ru.cbr.tomsk.kgr.controller;

import ru.cbr.tomsk.kgr.entity.Bank;
import ru.cbr.tomsk.kgr.service.BankService;
import ru.cbr.tomsk.kgr.service.SDService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class SDController {
    private SDService sdService;
    private BankService bankService;

    public SDController(SDService sdService, BankService bankService) {
        this.sdService = sdService;
        this.bankService = bankService;
    }


    @RequestMapping("/sdnow")
    public String SDnow(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> sdList = sdService.getSD(cbase);
        model.put("bank", bank);
        model.put("regnum", bank.get("cregnum"));
        model.put("sdlist", sdList);
        return "sdnow";
    }

    @RequestMapping("/sdhist")
    public String SDhist(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }

        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> sdList = sdService.getSDhist(cbase);
        model.put("bank", bank);
        model.put("regnum", bank.get("cregnum"));
        model.put("sdlist", sdList);
        return "sdhist";
    }
}
