package com.feisystems.icas.domain.reference;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class FeedbackRatingsCode implements Serializable {
	
	private static final long serialVersionUID = 48834377947934234L;
	
	@NotNull
 	@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long ratingId;
	
	@NotNull
	@Column(unique=true)
	private String ratingValue;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ratingId == null) ? 0 : ratingId.hashCode());
		result = prime * result
				+ ((ratingValue == null) ? 0 : ratingValue.hashCode());
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
		FeedbackRatingsCode other = (FeedbackRatingsCode) obj;
		if (ratingId == null) {
			if (other.ratingId != null)
				return false;
		} else if (!ratingId.equals(other.ratingId))
			return false;
		if (ratingValue == null) {
			if (other.ratingValue != null)
				return false;
		} else if (!ratingValue.equals(other.ratingValue))
			return false;
		return true;
	}

	public Long getRatingId() {
		return ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	public String getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(String ratingValue) {
		this.ratingValue = ratingValue;
	}

}
