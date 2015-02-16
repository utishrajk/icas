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

import com.feisystems.icas.service.clinicaldata.AllergyService;
import com.feisystems.icas.service.dto.AllergyDto;
import com.feisystems.icas.service.patient.PatientService;
import com.feisystems.icas.service.reference.ActStatusCodeService;
import com.feisystems.icas.service.reference.AdverseEventTypeCodeService;
import com.feisystems.icas.service.reference.AllergenCodeService;
import com.feisystems.icas.service.reference.AllergyReactionCodeService;
import com.feisystems.icas.service.reference.AllergySeverityCodeService;

@Controller
@RequestMapping("/allergys")
public class AllergyController {

	@Autowired
	AllergyService allergyService;

	@Autowired
	PatientService patientService;

	@Autowired
	ActStatusCodeService actStatusCodeService;

	@Autowired
	AdverseEventTypeCodeService adverseEventTypeCodeService;

	@Autowired
	AllergyReactionCodeService allergyReactionCodeService;

	@Autowired
	AllergySeverityCodeService allergySeverityCodeService;

	@Autowired
	AllergenCodeService AllergenCodeService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public AllergyDto showJson(@PathVariable("id") Long id) {
		AllergyDto allergy = allergyService.findAllergy(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return allergy;
	}


	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<AllergyDto> retrieveJsonForPatient(@PathVariable("id") Long patientId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AllergyDto> result = allergyService.findAllAllergysByPatientId(patientId);
		return result;
	}

	@RequestMapping(value = "/patient/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody AllergyDto allergyDto, @PathVariable("id") Long patientId) {
		allergyDto.setPatientId(patientId);
		allergyService.saveAllergy(allergyDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody AllergyDto allergy, @PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (allergyService.updateAllergy(allergy) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		AllergyDto allergy = allergyService.findAllergy(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (allergy == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		allergyService.deleteAllergy(allergy);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
