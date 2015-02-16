package com.feisystems.icas.service.clinicaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.icas.domain.clinicaldata.Allergy;
import com.feisystems.icas.domain.clinicaldata.AllergyRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.AdverseEventTypeCode;
import com.feisystems.icas.domain.reference.AdverseEventTypeCodeRepository;
import com.feisystems.icas.domain.reference.AllergenCode;
import com.feisystems.icas.domain.reference.AllergenCodeRepository;
import com.feisystems.icas.domain.reference.AllergyReactionCode;
import com.feisystems.icas.domain.reference.AllergyReactionCodeRepository;
import com.feisystems.icas.domain.reference.AllergySeverityCode;
import com.feisystems.icas.domain.reference.AllergySeverityCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.AllergyDto;

@Component
public class AllergyDtoToAllergyMapper implements DtoToDomainEntityMapper<AllergyDto, Allergy> {

	private AdverseEventTypeCodeRepository adverseEventTypeCodeRepository;

	private AllergyRepository allergyRepository;

	private PatientRepository patientRepository;

	private AllergenCodeRepository allergenCodeRepository;

	private AllergyReactionCodeRepository allergyReactionCodeRepository;

	private AllergySeverityCodeRepository allergySeverityCodeRepository;

	@Autowired
	public AllergyDtoToAllergyMapper(AdverseEventTypeCodeRepository adverseEventTypeCodeRepository,
			AllergyRepository allergyRepository,
			PatientRepository patientRepository,
			AllergenCodeRepository allergenCodeRepository,
			AllergyReactionCodeRepository allergyReactionCodeRepository,
			AllergySeverityCodeRepository allergySeverityCodeRepository) {
		super();

		this.adverseEventTypeCodeRepository = adverseEventTypeCodeRepository;
		this.allergyRepository = allergyRepository;
		this.patientRepository = patientRepository;
		this.allergenCodeRepository = allergenCodeRepository;
		this.allergyReactionCodeRepository = allergyReactionCodeRepository;
		this.allergySeverityCodeRepository = allergySeverityCodeRepository;
	}

	@Override
	public Allergy map(AllergyDto dto) {
		Allergy allergy = null;

		if (dto.getId() != null) {
			allergy = allergyRepository.findOne(dto.getId());
		} else {
			allergy = new Allergy();
		}

		allergy.setAllergyStartDate(dto.getAllergyStartDate());
		allergy.setAllergyEndDate(dto.getAllergyEndDate());

		if (StringUtils.hasText(dto.getAdverseEventTypeCode())) {
			AdverseEventTypeCode adverseEventTypeCode = adverseEventTypeCodeRepository.findByCode(dto.getAdverseEventTypeCode());
			allergy.setAdverseEventTypeCode(adverseEventTypeCode);
		}

		if (StringUtils.hasText(dto.getAllergenCode())) {
			AllergenCode allergenCode = allergenCodeRepository.findByCode(dto.getAllergenCode());
			allergy.setAllergenCode(allergenCode);
		}

		if (StringUtils.hasText(dto.getAllergyReactionCode())) {
			AllergyReactionCode allergyReactionCode = allergyReactionCodeRepository.findByCode(dto.getAllergyReactionCode());
			allergy.setAllergyReactionCode(allergyReactionCode);
		}

		if (StringUtils.hasText(dto.getAllergySeverityCode())) {
			AllergySeverityCode allergySeverityCode = allergySeverityCodeRepository.findByCode(dto.getAllergySeverityCode());
			allergy.setAllergySeverityCode(allergySeverityCode);
		}

		allergy.setNote(dto.getNote());

		Patient patient = patientRepository.findOne(dto.getPatientId());
		allergy.setPatient(patient);

		return allergy;
	}
}
