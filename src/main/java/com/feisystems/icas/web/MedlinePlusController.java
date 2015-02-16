package com.feisystems.icas.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.icas.domain.client.Link;
import com.feisystems.icas.service.client.MedlinePlusService;

@Controller
@RequestMapping("/medlineplus")
public class MedlinePlusController {
	@Autowired
    MedlinePlusService medlinePlusService;

	@RequestMapping(value = "/{code}/{type}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Link> getMedlinePlusLink(@PathVariable("code") String code, @PathVariable("type") String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
		
        List<Link> result = medlinePlusService.getLinks(code, type);
        return result;
    }
}
