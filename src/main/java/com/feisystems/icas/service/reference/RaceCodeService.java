package com.feisystems.icas.service.reference;
import com.feisystems.icas.service.dto.LookupDto;

import java.util.List;

public interface RaceCodeService {

	/**
	 * Find all race codes.
	 * @return the list
	 */
	List<LookupDto> findAllRaceCodes();

}
