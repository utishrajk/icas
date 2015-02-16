package com.feisystems.icas.service.provider;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.provider.Activity;
import com.feisystems.icas.domain.provider.ActivityRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.ActivityDto;

@Service
@Transactional
public class IndividualProviderActivityServiceImpl implements IndividualProviderActivityService {
	
	/** The Activity dto to Activity mapper. */
	@Autowired
	private DtoToDomainEntityMapper<ActivityDto, Activity> activityDtoToActivityMapper;
	
	@Autowired
	private ActivityRepository activityRepository; 
	
	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void saveActivity(ActivityDto activityDto) {
		Activity activity = activityDtoToActivityMapper.map(activityDto);
		activityRepository.save(activity);
	}

	@Override
	public List<ActivityDto> findActivitiesByIndividualProviderId(Long id) {

		List<ActivityDto> individualProviderProfileDtoList = new ArrayList<>();
		List<Activity> activityList = activityRepository.findAll();

		if (activityList != null && activityList.size() > 0) {
			for (Activity activity : activityList) {
				ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
				individualProviderProfileDtoList.add(activityDto);
			}
			return individualProviderProfileDtoList;
		}
		throw new IllegalArgumentException("No Care Manager Found");

	}
	
}
