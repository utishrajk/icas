package com.feisystems.icas.domain.reference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.feisystems.icas.domain.provider.OrganizationalProvider;

@Entity
public class ServiceCode extends AbstractCodedConcept implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	private Set<OrganizationalProvider> organizationalProvider =  new HashSet<OrganizationalProvider>();
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	
	public void setOrganizationalProvider(
			Set<OrganizationalProvider> organizationalProvider) {
		this.organizationalProvider = organizationalProvider;
	}
	
	public Set<OrganizationalProvider> getOrganizationalProvider() {
		return organizationalProvider;
	}
	
	
}
