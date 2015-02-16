package com.feisystems.icas.domain.reference;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;

@Entity
@Table
public class FeedbackServicesCode implements Serializable {
	
	private static final long serialVersionUID = 488343779479515176L;
	
	@NotNull
    @Id
    private Long serviceId;
	
	@NotNull
	@Column(unique=true)
	private String name;
	
    @OneToMany(mappedBy = "individualProvider", cascade=CascadeType.ALL)
    private Set<IndividualProviderFeedbackServices> individualProviderFeedbackServices;

    
	public Set<IndividualProviderFeedbackServices> getIndividualProviderFeedbackServices() {
		return individualProviderFeedbackServices;
	}

	public void setIndividualProviderFeedbackServices(
			Set<IndividualProviderFeedbackServices> individualProviderFeedbackServices) {
		this.individualProviderFeedbackServices = individualProviderFeedbackServices;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeedbackServicesCode other = (FeedbackServicesCode) obj;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
