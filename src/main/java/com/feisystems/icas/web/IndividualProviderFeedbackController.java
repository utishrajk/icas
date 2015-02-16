package com.feisystems.icas.web;

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

import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;
import com.feisystems.icas.service.provider.services.IndividualProviderFeedbackServicesService;

@Controller
@RequestMapping("/individualprovidersfeedback")
public class IndividualProviderFeedbackController {

	@Autowired
    IndividualProviderFeedbackServicesService individualProviderFeedbackServicesService;
		
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<IndividualProviderFeedbackServiceDto> listJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<IndividualProviderFeedbackServiceDto> result = individualProviderFeedbackServicesService.findServicesByIndividualProviderId(id);
        return result;
    }

	@RequestMapping(value = "/assign", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody IndividualProviderFeedbackServiceDto individualProviderServiceDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpStatus httpStatus = HttpStatus.CREATED;
		individualProviderFeedbackServicesService.saveIndividualProviderFeedbackServices(individualProviderServiceDto);
        return new ResponseEntity<String>(headers, httpStatus);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody IndividualProviderFeedbackServiceDto individualProviderServiceDto, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		if (individualProviderFeedbackServicesService.updateIndividualProviderService(individualProviderServiceDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

}
