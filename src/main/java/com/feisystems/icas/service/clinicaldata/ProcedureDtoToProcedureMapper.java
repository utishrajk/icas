package com.feisystems.icas.service.clinicaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.icas.domain.clinicaldata.ProcedureObservation;
import com.feisystems.icas.domain.clinicaldata.ProcedureObservationRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.ActStatusCode;
import com.feisystems.icas.domain.reference.ActStatusCodeRepository;
import com.feisystems.icas.domain.reference.BodySiteCode;
import com.feisystems.icas.domain.reference.BodySiteCodeRepository;
import com.feisystems.icas.domain.reference.ProcedureTypeCode;
import com.feisystems.icas.domain.reference.ProcedureTypeCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.ProcedureObservationDto;

@Component
public class ProcedureDtoToProcedureMapper implements DtoToDomainEntityMapper<ProcedureObservationDto, ProcedureObservation> {
	
	private ProcedureObservationRepository procedureObservationRepository;
	
	private ProcedureTypeCodeRepository procedureTypeCodeRepository;
	
	private PatientRepository patientRepository;
	
	private ActStatusCodeRepository actStatusCodeRepository;
	
	private BodySiteCodeRepository bodySiteCodeRepository;
	
	@Autowired
	public ProcedureDtoToProcedureMapper(ProcedureObservationRepository procedureObservationRepository,
			ProcedureTypeCodeRepository procedureTypeCodeRepository, PatientRepository patientRepository, ActStatusCodeRepository actStatusCodeRepository,
			BodySiteCodeRepository bodySiteCodeRepository) {
		super();
		this.procedureObservationRepository = procedureObservationRepository;
		this.procedureTypeCodeRepository = procedureTypeCodeRepository;
		this.patientRepository = patientRepository;
		this.actStatusCodeRepository = actStatusCodeRepository;
		this.bodySiteCodeRepository = bodySiteCodeRepository;
	}

	@Override
	public ProcedureObservation map(ProcedureObservationDto dto) {
		
		ProcedureObservation procedure = null;
		
		if(dto.getId() != null) {
			procedure = procedureObservationRepository.findOne(dto.getId());
		} else {
			procedure = new ProcedureObservation();
		}
		
		if(procedure == null) {
			return null;
		}
		
		procedure.setProcedureStartDate(dto.getProcedureStartDate());
		procedure.setProcedureEndDate(dto.getProcedureEndDate());
		
		if(StringUtils.hasText(dto.getProcedureStatusCode())) {
			ProcedureTypeCode procedureTypeCode = procedureTypeCodeRepository.findByCode(dto.getProcedureTypeCode());
			
			procedure.setProcedureType(procedureTypeCode);
		}
		
		if(StringUtils.hasText(dto.getProcedureStatusCode())) {
			ActStatusCode actStatusCode = actStatusCodeRepository.findByCode(dto.getProcedureStatusCode());
			
			procedure.setProcedureStatusCode(actStatusCode);
		}
		
		if(StringUtils.hasText(dto.getBodySiteCode())) {
			BodySiteCode bodySiteCode = bodySiteCodeRepository.findByCode(dto.getBodySiteCode());
			
			procedure.setBodySiteCode(bodySiteCode);
		}
		
		Patient patient = patientRepository.findOne(dto.getPatientId());
		procedure.setPatient(patient);
		
		return procedure;
	}

}
