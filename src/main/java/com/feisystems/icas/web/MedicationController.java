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

import com.feisystems.icas.service.clinicaldata.MedicationService;
import com.feisystems.icas.service.dto.MedicationDto;

@Controller
@RequestMapping("/medications")
public class MedicationController {
	
	@Autowired
    MedicationService medicationService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public MedicationDto showJson(@PathVariable("id") Long id) {
        MedicationDto medicationDto = medicationService.findMedication(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return medicationDto;
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public List<MedicationDto> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<MedicationDto> result = medicationService.findAllMedications();
        return result;
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody MedicationDto medicationDto) {
        medicationService.saveMedication(medicationDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody MedicationDto medicationDto, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (medicationService.updateMedication(medicationDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        MedicationDto medicationDto = medicationService.findMedication(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (medicationDto == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        medicationService.deleteMedication(medicationDto);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<MedicationDto> findAllMedicationByPatientId(@PathVariable("id") Long patientId) {
		List<MedicationDto> result = medicationService.findAllMedicationByPatientId(patientId) ;
		Collections.sort(result, new Comparator<MedicationDto>() {
			public int compare(MedicationDto dto1, MedicationDto dto2) {
				return dto1.getMedicationStartDate().compareTo(dto2.getMedicationStartDate());
			}
		});
		
		return result;
	}
}
