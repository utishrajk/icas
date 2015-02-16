package com.feisystems.icas.service.provider;

import java.util.List;

import com.feisystems.icas.service.dto.ActivityDto;

public interface IndividualProviderActivityService {
	
	public abstract void saveActivity(ActivityDto activityDto);
	
	public List<ActivityDto> findActivitiesByIndividualProviderId(Long id);

}
