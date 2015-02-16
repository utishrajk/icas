package com.feisystems.icas.service.reference;
import com.feisystems.icas.service.dto.LookupDto;

import java.util.List;

public interface ProviderTaxonomyCodeService {

	public abstract List<LookupDto> findAllProviderTaxonomyCodes();

}
