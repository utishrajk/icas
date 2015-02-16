package com.feisystems.icas.domain.provider;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.feisystems.icas.domain.reference.FeedbackRatingsCode;
import com.feisystems.icas.domain.reference.FeedbackServicesCode;

@Entity
public class IndividualProviderFeedbackServices implements Serializable {

	private static final long serialVersionUID = 9213805567050954930L;
	
	@Id
 	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "individualprovider_id")
	private IndividualProvider individualProvider;

	@ManyToOne
	@JoinColumn(name = "serviceId")
	private FeedbackServicesCode services;

	@ManyToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name = "ratingId")
	private FeedbackRatingsCode ratings;
	
	public FeedbackRatingsCode getRatings() {
		return ratings;
	}

	public void setRatings(FeedbackRatingsCode ratings) {
		this.ratings = ratings;
	}

	public FeedbackServicesCode getServices() {
		return services;
	}

	public void setServices(FeedbackServicesCode services) {
		this.services = services;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IndividualProvider getIndividualProvider() {
		return individualProvider;
	}

	public void setIndividualProvider(IndividualProvider individualProvider) {
		this.individualProvider = individualProvider;
	}

}
