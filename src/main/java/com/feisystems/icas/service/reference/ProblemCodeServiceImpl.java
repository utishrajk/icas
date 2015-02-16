package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ProblemCode;
import com.feisystems.icas.domain.reference.ProblemCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class ProblemCodeServiceImpl implements ProblemCodeService {

	@Autowired
	ProblemCodeRepository problemCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllProblemCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<ProblemCode> problemCodeList = problemCodeRepository.findAll();

		for (ProblemCode entity : problemCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
