package com.feisystems.icas.service.reference;

import com.feisystems.icas.domain.reference.StateCode;
import com.feisystems.icas.domain.reference.StateCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StateCodeServiceImpl implements StateCodeService {

	@Autowired
    StateCodeRepository stateCodeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	/** {@inheritDoc} */
	@Override
	public List<LookupDto> findAllStateCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<StateCode> stateCodeList = stateCodeRepository.findAll();

		for (StateCode entity : stateCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}

}
