package com.feisystems.icas.service.reference;

import java.util.List;

import com.feisystems.icas.service.dto.LookupDto;

public interface ActStatusCodeService {

	public abstract List<LookupDto> findAllActStatusCodes();

}
