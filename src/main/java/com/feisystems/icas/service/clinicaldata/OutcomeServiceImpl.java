package com.feisystems.icas.service.clinicaldata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.clinicaldata.Outcome;
import com.feisystems.icas.domain.clinicaldata.OutcomeRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.OutcomeDto;

@Service
@Transactional(rollbackFor = { ClinicalDataNotFoundException.class })
public class OutcomeServiceImpl implements OutcomeService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private OutcomeRepository outcomeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DtoToDomainEntityMapper<OutcomeDto, Outcome> outcomeDtoToOutcomeMapper;

	@Override
	public OutcomeDto findOutcome(Long id) {
		Outcome outcome = outcomeRepository.findOne(id);

		if (outcome != null) {
			return modelMapper.map(outcome, OutcomeDto.class);
		}
		throw new ClinicalDataNotFoundException(id);
	}

	@Override
	public List<OutcomeDto> findAllOutcomes() {
		List<OutcomeDto> outcomeDtoList = new ArrayList<>();
		List<Outcome> outcomeList = outcomeRepository.findAll();

		if (outcomeList.isEmpty()) {
			throw new IllegalArgumentException("No Outcome Found");
		}

		for (Outcome outocome : outcomeList) {
			OutcomeDto dto = modelMapper.map(outocome, OutcomeDto.class);
			outcomeDtoList.add(dto);
		}

		return outcomeDtoList;
	}

	@Override
	public void saveOutcome(OutcomeDto dto) {
		Outcome outcome = outcomeDtoToOutcomeMapper.map(dto);
		outcomeRepository.save(outcome);
	}

	@Override
	public Outcome updateOutcome(OutcomeDto dto) {
		Outcome outcome = outcomeDtoToOutcomeMapper.map(dto);

		if (outcome == null) {
			throw new ClinicalDataNotFoundException(dto.getId());
		}

		return outcomeRepository.save(outcome);
	}

	@Override
	public void deleteOutcome(OutcomeDto dto) {
		outcomeRepository.delete(dto.getId());
	}

	@Override
	public List<OutcomeDto> findAllOutcomesByPatientId(Long patientId) {
		List<OutcomeDto> dtoList = new ArrayList<>();
		Patient patient = patientRepository.findOne(patientId);

		Set<Outcome> outcomeList = patient.getOutcomes();

		for (Outcome outcome : outcomeList) {
			OutcomeDto dto = modelMapper.map(outcome, OutcomeDto.class);
			dtoList.add(dto);
		}

		return dtoList;
	}

}
