package com.feisystems.icas.service.clinicaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.clinicaldata.Medication;
import com.feisystems.icas.domain.clinicaldata.MedicationRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.domain.reference.MedicationCode;
import com.feisystems.icas.domain.reference.MedicationCodeRepository;
import com.feisystems.icas.domain.reference.ProductFormCode;
import com.feisystems.icas.domain.reference.ProductFormCodeRepository;
import com.feisystems.icas.domain.reference.RouteCode;
import com.feisystems.icas.domain.reference.RouteCodeRepository;
import com.feisystems.icas.domain.reference.UnitOfMeasureCode;
import com.feisystems.icas.domain.reference.UnitOfMeasureCodeRepository;
import com.feisystems.icas.domain.valueobject.QuantityType;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.MedicationDto;

@Component
public class MedicationDtoToMedicationMapper implements DtoToDomainEntityMapper<MedicationDto, Medication> {
	
	private PatientRepository patientRepository;
	
	private UnitOfMeasureCodeRepository unitOfMeasureCodeRepository;
	
	private RouteCodeRepository routeCodeRepository;
	
	private MedicationCodeRepository medicationCodeRepository;
	
	private ProductFormCodeRepository productFormCodeRepository;
	
	private MedicationRepository medicationRepository;
	
	@Autowired
	public MedicationDtoToMedicationMapper(
			MedicationCodeRepository medicationCodeRepository,
			PatientRepository patientRepository, 
			UnitOfMeasureCodeRepository unitOfMeasureCodeRepository, 
			RouteCodeRepository routeCodeRepository,
			ProductFormCodeRepository productFormCodeRepository,
			MedicationRepository medicationRepository
			) {
		
		super();
		this.medicationCodeRepository = medicationCodeRepository;
		this.patientRepository = patientRepository;
		this.unitOfMeasureCodeRepository = unitOfMeasureCodeRepository;
		this.routeCodeRepository = routeCodeRepository;
		this.productFormCodeRepository = productFormCodeRepository;
		this.medicationRepository = medicationRepository;
	}
	
	@Override
	public Medication map(MedicationDto dto) {
		
		Medication medication = null;

		if (dto.getId() != null) {
			medication = medicationRepository.findOne(dto.getId());
		} else {
			medication = new Medication();
		}

		if (medication == null) {
			return null;
		}
		
		Patient patient = patientRepository.findOne(dto.getPatientId());
		medication.setPatient(patient);
		
		MedicationCode medicationCode = medicationCodeRepository.findByCode(dto.getMedicationCode());
		medication.setMedicationCode(medicationCode);
		
		medication.setFreeTextSig(dto.getFreeTextSig());
		medication.setMedicationStartDate(dto.getMedicationStartDate());
		medication.setMedicationEndDate(dto.getMedicationEndDate());
		medication.setMedicationNote(dto.getMedicationNote());
		
		QuantityType doseQuantity = new QuantityType();
		doseQuantity.setMeasuredValue(dto.getDoseQuantity().getMeasuredValue());
		
		UnitOfMeasureCode unitOfMeasureCode = unitOfMeasureCodeRepository.findByCode(dto.getDoseQuantity().getUnitOfMeasureCode());
		doseQuantity.setUnitOfMeasureCode(unitOfMeasureCode);		
		medication.setDoseQuantity(doseQuantity);
		
		RouteCode routeCode = routeCodeRepository.findByCode(dto.getRouteCode());
		medication.setRouteCode(routeCode);
		
		ProductFormCode productFormCode = productFormCodeRepository.findByCode(dto.getProductFormCode());
		medication.setProductFormCode(productFormCode);
		
		return medication;
	}

}
