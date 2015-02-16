package com.feisystems.icas.service.reference;

import com.feisystems.icas.domain.reference.RaceCode;
import com.feisystems.icas.domain.reference.RaceCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RaceCodeServiceImpl implements RaceCodeService {

	/** The race code repository. */
	@Autowired
	RaceCodeRepository raceCodeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;

	/** {@inheritDoc} */
	@Override
	public List<LookupDto> findAllRaceCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<RaceCode> raceCodeList = raceCodeRepository.findAll();

		for (RaceCode entity : raceCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}

}
