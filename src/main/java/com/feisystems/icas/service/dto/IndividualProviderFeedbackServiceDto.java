package com.feisystems.icas.service.dto;

public class IndividualProviderFeedbackServiceDto {

	private Long rating_id;
	
	private Long service_id;

	private String name;

	private Long id;
	
	private Long individualprovider_id;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRating_id() {
		return rating_id;
	}

	public void setRating_id(Long rating_id) {
		this.rating_id = rating_id;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public Long getIndividualprovider_id() {
		return individualprovider_id;
	}

	public void setIndividualprovider_id(Long individualprovider_id) {
		this.individualprovider_id = individualprovider_id;
	}
	
}
