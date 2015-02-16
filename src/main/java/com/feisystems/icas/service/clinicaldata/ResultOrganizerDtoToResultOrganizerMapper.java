package com.feisystems.icas.service.clinicaldata;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.clinicaldata.ResultObservation;
import com.feisystems.icas.domain.clinicaldata.ResultOrganizer;
import com.feisystems.icas.domain.clinicaldata.ResultOrganizerRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.ResultInterpretationCode;
import com.feisystems.icas.domain.reference.ResultInterpretationCodeRepository;
import com.feisystems.icas.domain.reference.ResultObservationType;
import com.feisystems.icas.domain.reference.ResultOrganizerCode;
import com.feisystems.icas.domain.reference.ResultOrganizerCodeRepository;
import com.feisystems.icas.domain.reference.ResultOrganizerStatusCode;
import com.feisystems.icas.domain.reference.ResultOrganizerStatusCodeRepository;
import com.feisystems.icas.domain.reference.UnitOfMeasureCode;
import com.feisystems.icas.domain.reference.UnitOfMeasureCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.ResultObservationDto;
import com.feisystems.icas.service.dto.ResultOrganizerDto;
import com.feisystems.icas.domain.reference.ResultObservationTypeRepository;


@Component
public class ResultOrganizerDtoToResultOrganizerMapper implements DtoToDomainEntityMapper<ResultOrganizerDto, ResultOrganizer> {
	
	private ResultOrganizerRepository resultOrganizerRepository;
	
	private ResultOrganizerStatusCodeRepository resultOrganizerStatusCodeRepository;
	
	private PatientRepository patientRepository;

	private ResultOrganizerCodeRepository resultOrganizerCodeRepository;
	
	private UnitOfMeasureCodeRepository unitOfMeasureCodeRepository;
	
	private ResultInterpretationCodeRepository resultInterpretationCodeRepository;	
	
	private ResultObservationTypeRepository resultObservationTypeRepository;
	 
	@Autowired
	public ResultOrganizerDtoToResultOrganizerMapper(
			ResultOrganizerRepository resultOrganizerRepository, 
			ResultOrganizerStatusCodeRepository resultOrganizerStatusCodeRepository, 
			PatientRepository patientRepository,
			ResultOrganizerCodeRepository resultOrganizerCodeRepository,
			UnitOfMeasureCodeRepository unitOfMeasureCodeRepository,
			ResultInterpretationCodeRepository resultInterpretationCodeRepository,
			ResultObservationTypeRepository resultObservationTypeRepository) {
		
		super();
		this.resultOrganizerRepository = resultOrganizerRepository;
		this.resultOrganizerStatusCodeRepository = resultOrganizerStatusCodeRepository;
		this.patientRepository = patientRepository;
		this.resultOrganizerCodeRepository = resultOrganizerCodeRepository;
		this.unitOfMeasureCodeRepository = unitOfMeasureCodeRepository;
		this.resultInterpretationCodeRepository = resultInterpretationCodeRepository;
		this.resultObservationTypeRepository = resultObservationTypeRepository; 
		
	}
	
	@Override
	public ResultOrganizer map(ResultOrganizerDto dto) {
		ResultOrganizer resultOrganizer = null;

		if (dto.getId() != null) {
			resultOrganizer = resultOrganizerRepository.findOne(dto.getId());
		} else {
			resultOrganizer = new ResultOrganizer();
		}

		if (resultOrganizer == null) {
			return null;
		}
		
		Patient patient = patientRepository.findOne(dto.getPatientId());
		resultOrganizer.setPatient(patient);
		
		
		ResultOrganizerCode resultOrganizerCode = resultOrganizerCodeRepository.findByCode(dto.getResultOrganizerCode());
		resultOrganizer.setResultOrganizerCode(resultOrganizerCode);

		resultOrganizer.setResultOrganizerDateTime(dto.getResultOrganizerDateTime());
		
		ResultOrganizerStatusCode resultOrganizerStatusCode = resultOrganizerStatusCodeRepository.findByCode(dto.getResultOrganizerStatusCode());
		resultOrganizer.setResultOrganizerStatusCode(resultOrganizerStatusCode);
		
		Set<ResultObservation> ResultObservationsList = new HashSet<ResultObservation>();
		
		for ( ResultObservationDto element : dto.getResultObservations()) {
			
			ResultObservation resultObservation = new ResultObservation();
			
			UnitOfMeasureCode unitOfMeasureCode = unitOfMeasureCodeRepository.findByCode(element.getResultObservationMeasureCode());
			resultObservation.setResultObservationMeasureCode(unitOfMeasureCode);
			
			resultObservation.setResultReferenceRangeMax(element.getResultReferenceRangeMax());
			resultObservation.setResultReferenceRangeMin(element.getResultReferenceRangeMin());
			resultObservation.setResultObservationDateTime(element.getResultObservationDateTime());
			resultObservation.setResultObservationValue(element.getResultObservationValue());
			
			ResultInterpretationCode resultObservationInterpretationCode = resultInterpretationCodeRepository.findByCode(element.getResultObservationInterpretationCode());
			resultObservation.setResultObservationInterpretationCode(resultObservationInterpretationCode);;
			
			ResultObservationType resultObservationType = resultObservationTypeRepository.findByCode(element.getResultObservationTypeCode());
			resultObservation.setResultObservationTypeCode(resultObservationType);
			
			resultObservation.setResultObservationNote(element.getResultObservationNote());
			resultObservation.setResultOrganizer(resultOrganizer);
			ResultObservationsList.add(resultObservation);
		}
		
		resultOrganizer.setResultObservations(ResultObservationsList);
		
		return resultOrganizer;
	}

}
