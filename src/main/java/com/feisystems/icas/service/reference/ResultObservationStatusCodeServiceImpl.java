package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.RaceCode;
import com.feisystems.icas.domain.reference.RaceCodeRepository;
import com.feisystems.icas.domain.reference.ResultObservationStatusCode;
import com.feisystems.icas.domain.reference.ResultObservationStatusCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class ResultObservationStatusCodeServiceImpl implements
		ResultObservationStatusCodeService {

	@Autowired
	ResultObservationStatusCodeRepository resultObservationStatusCodeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllResultObservationStatusCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<ResultObservationStatusCode> resultObservationStatusCodeList = resultObservationStatusCodeRepository.findAll();

		for (ResultObservationStatusCode entity : resultObservationStatusCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}
}
