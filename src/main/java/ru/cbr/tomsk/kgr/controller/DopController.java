package ru.cbr.tomsk.kgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cbr.tomsk.kgr.entity.Bank;
import ru.cbr.tomsk.kgr.service.*;

import java.util.List;
import java.util.Map;

@Controller
public class DopController {
    private DopService dopService;
    private BankService bankService;

    public DopController(DopService dopService, BankService bankService) {
        this.dopService = dopService;
        this.bankService = bankService;
    }
    @RequestMapping("/uch")
    public String Uch(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Map<String, Object> bank = bankService.getBank(regnum);
        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        List<Map<String, Object>> uchList = dopService.getUch(regnum);
        if (uchList != null) {
            model.put("bank", bank);
            model.put("uch", uchList);
            return "uch";
        } else {
            model.put("error", "нет данных");
            return "errors";
        }
    }

    @RequestMapping("/uch1")
    public String Uch1(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Map<String, Object> bank = bankService.getBank(regnum);
        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        // заглушка
        String cmaket = "77779378";
        String cmaket_e = "77779378";
        // конец заглушки

        List<Map<String, Object>> uch1List = dopService.getUch1(regnum, cmaket, cmaket_e);
        if (uch1List != null & uch1List.size()!=0) {
            model.put("bank", bank);
            model.put("uch1", uch1List);
            List<Map<String, Object>> uch11List = dopService.getUch11(regnum, cmaket);
            Map<String, Object> uch11 = uch11List.get(0);
            model.put("uch11", uch11);
            model.put("countUch", dopService.getGetCountUch(regnum, cmaket, cmaket_e));
            return "uch1";
        } else {
            model.put("error", "нет данных");
            return "errors";
        }
    }


    @RequestMapping("/opof")
    public String Opof(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> opofList = dopService.getOpof(cbase);
        if (opofList != null) {
            model.put("bank", bank);
            model.put("opofs", opofList);
            return "opof";
        } else {
           model.put("error", "нет данных");
            return "errors";
        }
    }

    @RequestMapping("/dopof")
    public String Dopof(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> dopofList = dopService.getDopof(cbase);
        if (dopofList != null) {
            model.put("bank", bank);
            model.put("dopofs", dopofList);
            return "dopof";
        } else {
            model.put("error", "нет данных");
            return "errors";
        }
    }

    @RequestMapping("/opoc")
    public String Opoc(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> opocList = dopService.getOpoc(cbase);
        if (opocList != null) {
            model.put("bank", bank);
            model.put("opocs", opocList);
            return "opoc";
        } else {
            model.put("error", "нет данных");
            return "errors";
        }
    }

    @RequestMapping("/kko")
    public String Kko(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> kkoList = dopService.getKko(cbase);
        if (kkoList != null) {
            model.put("bank", bank);
            model.put("kkos", kkoList);
            return "kko";
        } else {
            model.put("error", "нет данных");
            return "errors";
        }
    }

    @RequestMapping("/preds")
    public String Preds(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        Integer cbase = Integer.valueOf(bank.get("cbase").toString());
        List<Map<String, Object>> predsList = dopService.getPreds(cbase);
        if (predsList != null) {
            model.put("bank", bank);
            model.put("preds", predsList);
            return "preds";
        } else {
            model.put("error", "нет данных");
            return "errors";
        }
    }
}
