package ru.cbr.tomsk.kgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cbr.tomsk.kgr.entity.RBase;
import ru.cbr.tomsk.kgr.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class KOController {
    private final BankService bankService;
    private final RUstavService rUstavService;
    private final RBaseService rBaseService;
    private final AddrService addrService;


    @Autowired
    public KOController(BankService bankService, RUstavService rUstavService,
                        RBaseService rBaseService, AddrService addrService) {
        this.bankService = bankService;
        this.rUstavService = rUstavService;
        this.rBaseService = rBaseService;
        this.addrService = addrService;
    }

   @GetMapping("/")
    public String getTest(Map<String, Object> model) {
        model.put("cregnum", "");
        return "main";
    }

    @PostMapping("/main")
    public String postTest(@RequestParam String cregnum, @RequestParam String select, Map<String, Object> model) {

        switch (select) {
            case "KO_Info": {
                return "redirect:/bank?regnum=" + cregnum;
            }
            case "SD_info": {
                return "redirect:/sdnow?regnum=" + cregnum;
            }
            case "SD_info_h": {
                return "redirect:/sdhist?regnum=" + cregnum;
            }
            case "Uch": {
                return "redirect:/uch?regnum=" + cregnum;
            }
            case "Uch1": {
                return "redirect:/uch1?regnum=" + cregnum;
            }
            case "Filials": {
                return "redirect:/branch?regnum=" + cregnum;
            }
            case "DopOf": {
                return "redirect:/dopof?regnum=" + cregnum;
            }
            case "Predst": {
                return "redirect:/preds?regnum=" + cregnum;
            }
            case "OperKass": {
                return "redirect:/opoc?regnum=" + cregnum;
            }
            case "KredKass": {
                return "redirect:/kko?regnum=" + cregnum;
            }
            case "OperOff": {
                return "redirect:/opof?regnum=" + cregnum;
            }
        }
        return "redirect:/main";
    }


    @GetMapping("/bank")
    public String Bank(@RequestParam(name = "regnum", required = true, defaultValue = "1481") Integer regnum
            , Map<String, Object> model) {
        Integer cregnum = regnum;
        Map<String, Object> bank = bankService.getBank(cregnum);
        model.put("bank", bank);

        if (bank == null) {
            String error = "Нет данных";
            model.put("error", error);
            return "errors";
        }
        String cbaseS = bank.get("cbase").toString();
        Integer cbase = Integer.valueOf(cbaseS);

        String ctbankS = bank.get("ctbank").toString();
        Integer ctbank = Integer.valueOf(ctbankS);


        List<Map<String, Object>> rUstavList = rUstavService.getUstav(cbase);
        model.put("ustav", rUstavList);

        RBase rBase = rBaseService.getRBase(cbase);
        String addrUst = addrService.getAddr(rBase.getCuraddr()).getCaddr();
        String addrFact = addrService.getAddr(rBase.getCaddrmn()).getCaddr();

        model.put("addrUst", addrUst);
        model.put("addrFact", addrFact);

        String tu = bankService.getTU(cregnum);
        model.put("RegionName", tu);

        String phone = bankService.getPhone(cregnum);
        model.put("Phones", phone);

        Map<String, Object> person= bankService.getPerson(cregnum);
        model.put("persons", person);

        Map<String, String> ustav = bankService.getUstav(cbase);
        model.put("UstCapital", ustav.get("UstCapital"));
        model.put("DateCapital", ustav.get("DateCapital"));

        List<Map<String, String>> licenList = bankService.getLicen(cregnum);
        model.put("licen", licenList);

        Map<String, String> countFilRF = bankService.getCountFilRF(cbase);
        model.put("CountFilRF", countFilRF.get("count"));

        Map<String, String> countFilFor = bankService.getCountFilFor(cbase);
        model.put("CountFilFor", countFilFor.get("count"));

        Map<String, String> countPredRF = bankService.getCountPredRF(cbase);
        model.put("CountPredRF", countPredRF.get("count"));

        Map<String, String> countPredFor = bankService.getCountPredFor(cbase);
        model.put("CountPredFor", countPredFor.get("count"));

        Map<String, String> countDopOf = bankService.getCountDopOf(cbase);
        model.put("CountDopOf", countDopOf.get("SUM_DOP"));

        Map<String, String> countOPC = bankService.getCountOPC(cbase);
        model.put("CountOperKass", countOPC.get("SUM_OPC"));

        Map<String, String> countKKO = bankService.getCountKKO(cbase);
        model.put("CountKKO", countKKO.get("SUM_KKO"));

        Map<String, String> countOPO = bankService.getCountOPO(cbase);
        model.put("CountOPO", countOPO.get("SUM_OPO"));

        Map<String, String> countPPKO = bankService.getCountPPKO(cbase);
        model.put("CountPPKO", countPPKO.get("SUM_PPKO"));

        String comment = bankService.getCommentLicen(cregnum, ctbank);
        model.put("LicType", comment);

        String strah = bankService.getStrah(cregnum);
        model.put("Strah", strah);

        return "bank";
    }

}
