package com.feisystems.icas.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.icas.service.dto.LookupDto;
import com.feisystems.icas.service.reference.AllergySeverityCodeService;

@Controller
@RequestMapping("/allergyseveritycodes")
public class AllergySeverityCodeController {

	@Autowired
	AllergySeverityCodeService allergySeverityCodeService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public List<LookupDto> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<LookupDto> result = allergySeverityCodeService.findAllAllergySeverityCodes();
		return result;
	}
}
