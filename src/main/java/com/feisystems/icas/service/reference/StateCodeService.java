package com.feisystems.icas.service.reference;
import com.feisystems.icas.service.dto.LookupDto;

import java.util.List;

public interface StateCodeService {

	public abstract List<LookupDto> findAllStateCodes();

}
