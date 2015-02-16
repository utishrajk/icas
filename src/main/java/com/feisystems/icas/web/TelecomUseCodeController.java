package com.feisystems.icas.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.icas.domain.reference.TelecomUseCode;
import com.feisystems.icas.service.reference.TelecomUseCodeService;

@Controller
@RequestMapping("/telecomusecodes")
public class TelecomUseCodeController {

	@Autowired
    TelecomUseCodeService telecomUseCodeService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public TelecomUseCode showJson(@PathVariable("id") Long id) {
        TelecomUseCode telecomUseCode = telecomUseCodeService.findTelecomUseCode(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return telecomUseCode;
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public List<TelecomUseCode> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<TelecomUseCode> result = telecomUseCodeService.findAllTelecomUseCodes();
        return result;
    }

}
