package com.feisystems.icas.service.clinicaldata;

import com.feisystems.icas.domain.clinicaldata.ResultOrganizer;
import com.feisystems.icas.domain.clinicaldata.ResultOrganizerRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.ResultOrganizerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = { ClinicalDataNotFoundException.class })
public class ResultOrganizerServiceImpl implements ResultOrganizerService {
	@Autowired
	ResultOrganizerRepository resultOrganizerRepository;
	@Autowired
	PatientRepository patientRepository; 
	@Autowired
	private DtoToDomainEntityMapper<ResultOrganizerDto, ResultOrganizer> resultOrganizerDtoToResultOrganizerMapper;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	ResultObservationToResultObservationDtoAbstractConverter resultObservationToResultObservationDtoAbstractConverter;
	
	public long countAllResultOrganizers() {
		return resultOrganizerRepository.count();
	}

	public void deleteResultOrganizer(ResultOrganizerDto resultOrganizerDto) {
		 resultOrganizerRepository.delete(resultOrganizerDto.getId());
	}

	public ResultOrganizerDto findResultOrganizer(Long id) {
		ResultOrganizer resultOrganizer = resultOrganizerRepository.findOne(id);

		if (resultOrganizer != null) {
			return modelMapper.map(resultOrganizer, ResultOrganizerDto.class);
		}
		throw new ClinicalDataNotFoundException(id);
	}

	public List<ResultOrganizerDto> findAllResultOrganizers() {
		List<ResultOrganizerDto> resultOrganizerDtoList = new ArrayList<ResultOrganizerDto>();
		List<ResultOrganizer> resultOrganizerList = resultOrganizerRepository.findAll();

		if (resultOrganizerList != null) {
			for (ResultOrganizer resultOrganizer : resultOrganizerList) {				
				ResultOrganizerDto resultOrganizerDto = modelMapper.map(resultOrganizer, ResultOrganizerDto.class);
				resultOrganizerDtoList.add(resultOrganizerDto);
			}
			return resultOrganizerDtoList;
		}

		throw new IllegalArgumentException("No Problems Found");
	}

	public List<ResultOrganizer> findResultOrganizerEntries(int firstResult,
			int maxResults) {
		return resultOrganizerRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void saveResultOrganizer(ResultOrganizerDto resultOrganizerDto) {
		ResultOrganizer resultOrganizer = resultOrganizerDtoToResultOrganizerMapper.map(resultOrganizerDto);
		resultOrganizerRepository.save(resultOrganizer);
	}

	public ResultOrganizer updateResultOrganizer(
			ResultOrganizerDto resultOrganizerDto) {
		ResultOrganizer resultOrganizer = resultOrganizerDtoToResultOrganizerMapper
				.map(resultOrganizerDto);

		if (resultOrganizer == null) {
			// throw new
			// ClinicalDataNotFoundException(resultOrganizerDto.getId());
			throw new ClinicalDataNotFoundException(1L);
		}

		return resultOrganizerRepository.save(resultOrganizer);
	}

	@Override
	public List<ResultOrganizerDto> findAllResultOrganizerByPatientId(Long patientId) {		
		
		modelMapper.addConverter(resultObservationToResultObservationDtoAbstractConverter);
		
		List<ResultOrganizerDto> resultorganizerDtoList = new ArrayList<>();
		Patient patient = patientRepository.findOne(patientId);

		Set<ResultOrganizer> resultorganizerList = patient.getResultOrganizers();

		for (ResultOrganizer resultOrganizer : resultorganizerList) {
			ResultOrganizerDto dto = modelMapper.map(resultOrganizer, ResultOrganizerDto.class);
			resultorganizerDtoList.add(dto);
		}

		return resultorganizerDtoList;
		
	}
}
