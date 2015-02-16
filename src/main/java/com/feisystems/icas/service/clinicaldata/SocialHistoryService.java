package com.feisystems.icas.service.clinicaldata;
import com.feisystems.icas.domain.clinicaldata.SocialHistory;
import com.feisystems.icas.domain.clinicaldata.SocialHistory;
import com.feisystems.icas.service.dto.SocialHistoryDto;

import java.util.List;

public interface SocialHistoryService {

	public abstract void deleteSocialHistory(SocialHistoryDto SocialHistory);


	public abstract SocialHistoryDto findSocialHistory(Long id);


	public abstract List<SocialHistoryDto> findAllSocialHistorys();


	public abstract void saveSocialHistory(SocialHistoryDto SocialHistory);


	public abstract SocialHistory updateSocialHistory(SocialHistoryDto SocialHistory);
	
	
	public abstract List<SocialHistoryDto> findAllSocialHistorysByPatientId(Long patientId);

}
