package com.feisystems.icas.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.feisystems.icas.service.clinicaldata.TreatmentPlanService;
import com.feisystems.icas.service.dto.PatientProfileDto;
import com.feisystems.icas.service.dto.TreatmentPlanDto;

@Controller
@RequestMapping("/treatmentplan")
public class TreatmentPlanController {

	@Autowired
	TreatmentPlanService treatmentPlanService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public TreatmentPlanDto showJson(@PathVariable("id") Long id) {
		TreatmentPlanDto treatmentPlanDto = treatmentPlanService.findTreatmentPlan(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return treatmentPlanDto;
	}

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<TreatmentPlanDto> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TreatmentPlanDto> result = treatmentPlanService.findAllTreatmentPlans();
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody TreatmentPlanDto treatmentPlanDto) {
		treatmentPlanService.saveTreatmentPlan(treatmentPlanDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		TreatmentPlanDto treatmentPlanDto = treatmentPlanService.findTreatmentPlan(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (treatmentPlanDto == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        treatmentPlanService.deleteTreatmentPlan(treatmentPlanDto);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }


	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo handleTypeMismatchException(HttpServletRequest req, TypeMismatchException ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage("error.patient.bad.request", null, locale);

		errorMessage += ex.getValue();
		String errorURL = req.getRequestURL().toString();

		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Locale locale = LocaleContextHolder.getLocale();

		String errorMessage = messageSource.getMessage("error_resourcenotfound_title", null, locale);
		errorMessage += ex.getMessage();

		String errorURL = req.getRequestURL().toString();

		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

}
