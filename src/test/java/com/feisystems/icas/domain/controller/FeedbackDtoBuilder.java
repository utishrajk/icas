package com.feisystems.icas.domain.controller;

import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;


public class FeedbackDtoBuilder {
	
	private IndividualProviderFeedbackServiceDto dto;
	
	public FeedbackDtoBuilder() {
		dto = new IndividualProviderFeedbackServiceDto();
	}
	
	public FeedbackDtoBuilder id(Long id) {
		dto.setId(id);
		return this;		
	}
	
	public FeedbackDtoBuilder name(String name) {
		dto.setName(name);		
		return this;
	}
	
	public FeedbackDtoBuilder ratingId(Long ratingId) {
		dto.setRating_id(ratingId);
		return this;
	}
	
	public FeedbackDtoBuilder serviceId(Long serviceId) {
		dto.setService_id(serviceId);		
		return this;
	}
	
	public FeedbackDtoBuilder individualproviderId(Long individualproviderId) {
		dto.setIndividualprovider_id(individualproviderId);		
		return this;
	}
	
	public IndividualProviderFeedbackServiceDto build() {
		return dto;
	}
	
}
