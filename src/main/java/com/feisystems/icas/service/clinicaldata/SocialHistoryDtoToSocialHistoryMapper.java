package com.feisystems.icas.service.clinicaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.icas.domain.clinicaldata.SocialHistory;
import com.feisystems.icas.domain.clinicaldata.SocialHistoryRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.ActStatusCode;
import com.feisystems.icas.domain.reference.ActStatusCodeRepository;
import com.feisystems.icas.domain.reference.SocialHistoryTypeCode;
import com.feisystems.icas.domain.reference.SocialHistoryTypeCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.SocialHistoryDto;

@Component
public class SocialHistoryDtoToSocialHistoryMapper implements DtoToDomainEntityMapper<SocialHistoryDto, SocialHistory> {

	private SocialHistoryRepository socialHistoryRepository;

	private SocialHistoryTypeCodeRepository socialHistoryTypeCodeRepository;

	private PatientRepository patientRepository;

	private ActStatusCodeRepository actStatusCodeRepository;

	@Autowired
	public SocialHistoryDtoToSocialHistoryMapper(SocialHistoryRepository socialHistoryRepository,
			SocialHistoryTypeCodeRepository socialHistoryTypeCodeRepository, PatientRepository patientRepository,
			ActStatusCodeRepository actStatusCodeRepository) {
		super();
		this.socialHistoryRepository = socialHistoryRepository;
		this.socialHistoryTypeCodeRepository = socialHistoryTypeCodeRepository;
		this.patientRepository = patientRepository;
		this.actStatusCodeRepository = actStatusCodeRepository;
	}

	@Override
	public SocialHistory map(SocialHistoryDto dto) {

		SocialHistory socialHistory = null;

		if (dto.getId() != null) {
			socialHistory = socialHistoryRepository.findOne(dto.getId());
		} else {
			socialHistory = new SocialHistory();
		}

		if (socialHistory == null) {
			return null;
		}

		socialHistory.setSocialHistoryStartDate(dto.getStartDate());
		socialHistory.setSocialHistoryEndDate(dto.getEndDate());

		if (StringUtils.hasText(dto.getSocialHistoryTypeCode())) {
			SocialHistoryTypeCode socialHistoryTypeCode = socialHistoryTypeCodeRepository.findByCode(dto.getSocialHistoryTypeCode());

			socialHistory.setSocialHistoryTypeCode(socialHistoryTypeCode);
		}

		if (StringUtils.hasText(dto.getSocialHistoryStatusCode())) {
			ActStatusCode socialHistoryStatusCode = actStatusCodeRepository.findByCode(dto.getSocialHistoryStatusCode());

			socialHistory.setSocialHistoryStatusCode(socialHistoryStatusCode);
		}

		Patient patient = patientRepository.findOne(dto.getPatientId());
		socialHistory.setPatient(patient);

		return socialHistory;

	}

}
