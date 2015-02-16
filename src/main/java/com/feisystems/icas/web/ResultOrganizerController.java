package com.feisystems.icas.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.icas.service.clinicaldata.ResultOrganizerService;
import com.feisystems.icas.service.dto.ResultOrganizerDto;

@Controller
@RequestMapping("/resultorganizers")
public class ResultOrganizerController {

	@Autowired
    ResultOrganizerService resultOrganizerService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResultOrganizerDto showJson(@PathVariable("id") Long id) {
        ResultOrganizerDto resultOrganizerDto = resultOrganizerService.findResultOrganizer(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return resultOrganizerDto;
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public List<ResultOrganizerDto> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<ResultOrganizerDto> result = resultOrganizerService.findAllResultOrganizers();
        return result;
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody ResultOrganizerDto resultOrganizerDto) {
        resultOrganizerService.saveResultOrganizer(resultOrganizerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody ResultOrganizerDto resultOrganizerDto, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (resultOrganizerService.updateResultOrganizer(resultOrganizerDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        ResultOrganizerDto resultOrganizerDto = resultOrganizerService.findResultOrganizer(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (resultOrganizerDto == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        resultOrganizerService.deleteResultOrganizer(resultOrganizerDto);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<ResultOrganizerDto> findAllResultOrganizerByPatientId(@PathVariable("id") Long patientId) {
		List<ResultOrganizerDto> result = resultOrganizerService.findAllResultOrganizerByPatientId(patientId) ;
		Collections.sort(result, new Comparator<ResultOrganizerDto>() {
			public int compare(ResultOrganizerDto dto1, ResultOrganizerDto dto2) {
				return dto1.getResultOrganizerDateTime().compareTo(dto2.getResultOrganizerDateTime());
			}
		});
		
		return result;
	}
	
}
