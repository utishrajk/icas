package com.feisystems.icas.service.clinicaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.icas.domain.clinicaldata.Outcome;
import com.feisystems.icas.domain.clinicaldata.OutcomeRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.CgiICode;
import com.feisystems.icas.domain.reference.CgiICodeRepository;
import com.feisystems.icas.domain.reference.CgiSCode;
import com.feisystems.icas.domain.reference.CgiSCodeRepository;
import com.feisystems.icas.domain.reference.MedicationCodeOutcomes;
import com.feisystems.icas.domain.reference.MedicationCodeOutcomesRepository;
import com.feisystems.icas.domain.reference.ProcedureTypeCode;
import com.feisystems.icas.domain.reference.ProcedureTypeCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.OutcomeDto;

@Component
public class OutcomeDtoToOutcomeMapper implements DtoToDomainEntityMapper<OutcomeDto, Outcome> {
	private OutcomeRepository outcomeRepository;

	private ProcedureTypeCodeRepository outcomeTypeCodeRepository;

	private PatientRepository patientRepository;

	private CgiICodeRepository cgiICodeRepository;

	private CgiSCodeRepository cgiSCodeRepository;
	
	private MedicationCodeOutcomesRepository medicationCodeOutcomesRepository;
	

	@Autowired
	public OutcomeDtoToOutcomeMapper(OutcomeRepository outcomeRepository, ProcedureTypeCodeRepository outcomeTypeCodeRepository,
			PatientRepository patientRepository, CgiICodeRepository cgiICodeRepository, CgiSCodeRepository cgiSCodeRepository,
			MedicationCodeOutcomesRepository medicationCodeOutcomesRepository) {
		super();
		this.outcomeRepository = outcomeRepository;
		this.outcomeTypeCodeRepository = outcomeTypeCodeRepository;
		this.patientRepository = patientRepository;
		this.cgiICodeRepository = cgiICodeRepository;
		this.cgiSCodeRepository = cgiSCodeRepository;
		this.medicationCodeOutcomesRepository = medicationCodeOutcomesRepository;
	}

	@Override
	public Outcome map(OutcomeDto dto) {

		Outcome outcome = null;

		if (dto.getId() != null) {
			outcome = outcomeRepository.findOne(dto.getId());
		} else {
			outcome = new Outcome();
		}

		if (outcome == null) {
			return null;
		}

		outcome.setStartDate(dto.getStartDate());
//		outcome.setEndDate(dto.getEndDate());
		outcome.setSymptoms(dto.getSymptoms());
		outcome.setTolerability(dto.getTolerability());

		if (StringUtils.hasText(dto.getProcedureTypeCode())) {
			ProcedureTypeCode outcomeTypeCode = outcomeTypeCodeRepository.findByCode(dto.getProcedureTypeCode());
			outcome.setProcedureType(outcomeTypeCode);
		}

		if (StringUtils.hasText(dto.getCgiICode())) {
			CgiICode cgiICode = cgiICodeRepository.findByCode(dto.getCgiICode());
			outcome.setCgiICode(cgiICode);
		}

		if (StringUtils.hasText(dto.getCgiSCode())) {
			CgiSCode cgiSCode = cgiSCodeRepository.findByCode(dto.getCgiSCode());
			outcome.setCgiSCode(cgiSCode);
		}
		
		if (StringUtils.hasText(dto.getCgiSCode())) {
			MedicationCodeOutcomes medicationCodeOutcome = medicationCodeOutcomesRepository.findByCode(dto.getMedicationCodeOutcome());
			outcome.setMedicationCodeOutcome(medicationCodeOutcome);
		}

		
		Patient patient = patientRepository.findOne(dto.getPatientId());
		outcome.setPatient(patient);

		return outcome;
	}

}
