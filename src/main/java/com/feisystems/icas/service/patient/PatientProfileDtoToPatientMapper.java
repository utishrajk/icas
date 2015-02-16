package com.feisystems.icas.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.AdministrativeGenderCode;
import com.feisystems.icas.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.icas.domain.reference.CountryCodeRepository;
import com.feisystems.icas.domain.reference.MaritalStatusCodeRepository;
import com.feisystems.icas.domain.reference.RaceCodeRepository;
import com.feisystems.icas.domain.reference.ReligiousAffiliationCodeRepository;
import com.feisystems.icas.domain.reference.StateCodeRepository;
import com.feisystems.icas.domain.valueobject.AddressType;
import com.feisystems.icas.domain.valueobject.TelephoneType;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.PatientProfileDto;

/**
 * The Class PatientProfileDtoToPatientMapper.
 */
@Component
public class PatientProfileDtoToPatientMapper implements
		DtoToDomainEntityMapper<PatientProfileDto, Patient> {

	/** The patient repository. */
	private PatientRepository patientRepository;
	
	/** The state code repository. */
	private StateCodeRepository stateCodeRepository;
	
	/** The country code repository. */
	private CountryCodeRepository countryCodeRepository;
	
	/** The administrative gender code repository. */
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	/** The race code repository. */
	private RaceCodeRepository raceCodeRepository;
	

	/**
	 * Instantiates a new patient profile dto to patient mapper.
	 *
	 * @param patientRepository the patient repository
	 * @param stateCodeRepository the state code repository
	 * @param countryCodeRepository the country code repository
	 * @param administrativeGenderCodeRepository the administrative gender code repository
	 * @param languageCodeRepository the language code repository
	 * @param maritalStatusCodeRepository the marital status code repository
	 * @param raceCodeRepository the race code repository
	 * @param religiousAffiliationCodeRepository the religious affiliation code repository
	 */
	@Autowired
	public PatientProfileDtoToPatientMapper(
			PatientRepository patientRepository,
			StateCodeRepository stateCodeRepository,
			CountryCodeRepository countryCodeRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			MaritalStatusCodeRepository maritalStatusCodeRepository,
			RaceCodeRepository raceCodeRepository,
			ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository) {
		
		super();
		
		this.patientRepository = patientRepository;
		this.stateCodeRepository = stateCodeRepository;
		this.countryCodeRepository= countryCodeRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.raceCodeRepository = raceCodeRepository;
	}

	/* (non-Javadoc)
	 * @see com.feisystems.icas.infrastructure.DtoToDomainEntityMapper#map(java.lang.Object)
	 */
	@Override
	public Patient map(PatientProfileDto patientDto) {
		Patient patient = null;

		if(patientDto.getId() != null) {
			patient = patientRepository.findOne(patientDto.getId());
		}
		else {
			patient = new Patient();
		}

		// Return null if no Patient found
		if (patient == null) {
			return null;
		}
		
		patient.setFirstName(patientDto.getFirstName());
		patient.setLastName(patientDto.getLastName());
		patient.setBirthDay(patientDto.getBirthDate());
		patient.setUsername(patientDto.getUsername());
		
		if (StringUtils.hasText(patientDto.getMedicalRecordNumber())) {
			patient.setMedicalRecordNumber(patientDto
					.getMedicalRecordNumber());
		} else {
			patient.setMedicalRecordNumber(null);
		}
		
		if (StringUtils.hasText(patientDto.getAddressStateCode())
				|| StringUtils.hasText(patientDto.getAddressPostalCode())) {

			AddressType address = new AddressType();
			address.setStateCode(stateCodeRepository.findByCode(patientDto
					.getAddressStateCode()));
			address.setPostalCode(patientDto.getAddressPostalCode());
			address.setCountryCode(countryCodeRepository.findByCode(patientDto.getAddressCountryCode())); 
			address.setNonUSState(patientDto.getNonUSState());
			patient.setAddress(address);
		} else {
			patient.setAddress(null);
		}
		
		if(StringUtils.hasText(patientDto.getTelephone())){
			TelephoneType telephone = new TelephoneType();
			telephone.setTelephone(patientDto.getTelephone());
			patient.setTelephone(telephone);
		}

		if (StringUtils.hasText(patientDto.getAdministrativeGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
					.findByCode(patientDto.getAdministrativeGenderCode());
			patient.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			patient.setAdministrativeGenderCode(null);
		}


		if (patientDto.getRaceCode() != null
				&& StringUtils.hasText(patientDto.getRaceCode())) {
			patient.setRaceCode(raceCodeRepository.findByCode(patientDto
					.getRaceCode()));
		} else {
			patient.setRaceCode(null);
		}


		return patient;
	}

}
