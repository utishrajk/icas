package com.feisystems.icas.service.provider;

import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.provider.Activity;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.ActivityDto;

@Component
public class ActivityDtoToActivityMapper implements
		DtoToDomainEntityMapper<ActivityDto, Activity> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.feisystems.icas.infrastructure.DtoToDomainEntityMapper#map(java.lang
	 * .Object)
	 */
	@Override
	public Activity map(ActivityDto activityDto) {
		Activity activity = new Activity();

		if(activityDto != null){
			activity.setAction(activityDto.getMethodName());
			activity.setDescription(activityDto.getDescription());
			activity.setTimestamp(activityDto.getTimestamp());
			activity.setUsername(activityDto.getUsername());
			activity.setIpAddress(activityDto.getIpAddress());
		}

		return activity;
	}

}
