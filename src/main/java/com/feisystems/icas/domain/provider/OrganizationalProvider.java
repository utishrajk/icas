package com.feisystems.icas.domain.provider;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.feisystems.icas.domain.reference.ServiceCode;

/**
 * @author tomson.ngassa
 *
 */
@Entity
public class OrganizationalProvider extends AbstractProvider implements Serializable {

    /**
     */
    @NotNull
    @Size(max = 30)
    private String orgName;

    /**
     */
    @Size(max = 30)
    private String otherOrgName;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialLastName;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialFirstName;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialTitle;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialNamePrefix;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialTelephoneNumber;
    
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "ORGANIZATION_SERVICE",
            joinColumns = {@JoinColumn(name = "orgainzation_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "id")})
    private Set<ServiceCode> services = new HashSet<ServiceCode>();
    
    
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


	private static final long serialVersionUID = 1L;

//	public boolean equals(Object obj) {
//        if (!(obj instanceof OrganizationalProvider)) {
//            return false;
//        }
//        if (this == obj) {
//            return true;
//        }
//        OrganizationalProvider rhs = (OrganizationalProvider) obj;
//        return new EqualsBuilder().append(authorizedOfficialFirstName, rhs.authorizedOfficialFirstName).append(authorizedOfficialLastName, rhs.authorizedOfficialLastName).append(authorizedOfficialNamePrefix, rhs.authorizedOfficialNamePrefix).append(authorizedOfficialTelephoneNumber, rhs.authorizedOfficialTelephoneNumber).append(authorizedOfficialTitle, rhs.authorizedOfficialTitle).append(enumerationDate, rhs.enumerationDate).append(firstLineMailingAddress, rhs.firstLineMailingAddress).append(firstLinePracticeLocationAddress, rhs.firstLinePracticeLocationAddress).append(id, rhs.id).append(lastUpdateDate, rhs.lastUpdateDate).append(mailingAddressCityName, rhs.mailingAddressCityName).append(mailingAddressCountryCode, rhs.mailingAddressCountryCode).append(mailingAddressFaxNumber, rhs.mailingAddressFaxNumber).append(mailingAddressPostalCode, rhs.mailingAddressPostalCode).append(mailingAddressStateName, rhs.mailingAddressStateName).append(mailingAddressTelephoneNumber, rhs.mailingAddressTelephoneNumber).append(npi, rhs.npi).append(orgName, rhs.orgName).append(otherOrgName, rhs.otherOrgName).append(practiceLocationAddressCityName, rhs.practiceLocationAddressCityName).append(practiceLocationAddressCountryCode, rhs.practiceLocationAddressCountryCode).append(practiceLocationAddressFaxNumber, rhs.practiceLocationAddressFaxNumber).append(practiceLocationAddressPostalCode, rhs.practiceLocationAddressPostalCode).append(practiceLocationAddressStateName, rhs.practiceLocationAddressStateName).append(practiceLocationAddressTelephoneNumber, rhs.practiceLocationAddressTelephoneNumber).append(providerEntityType, rhs.providerEntityType).append(providerTaxonomyCode, rhs.providerTaxonomyCode).append(secondLineMailingAddress, rhs.secondLineMailingAddress).append(secondLinePracticeLocationAddress, rhs.secondLinePracticeLocationAddress).isEquals();
//    }
//
//	public int hashCode() {
//        return new HashCodeBuilder().append(authorizedOfficialFirstName).append(authorizedOfficialLastName).append(authorizedOfficialNamePrefix).append(authorizedOfficialTelephoneNumber).append(authorizedOfficialTitle).append(enumerationDate).append(firstLineMailingAddress).append(firstLinePracticeLocationAddress).append(id).append(lastUpdateDate).append(mailingAddressCityName).append(mailingAddressCountryCode).append(mailingAddressFaxNumber).append(mailingAddressPostalCode).append(mailingAddressStateName).append(mailingAddressTelephoneNumber).append(npi).append(orgName).append(otherOrgName).append(practiceLocationAddressCityName).append(practiceLocationAddressCountryCode).append(practiceLocationAddressFaxNumber).append(practiceLocationAddressPostalCode).append(practiceLocationAddressStateName).append(practiceLocationAddressTelephoneNumber).append(providerEntityType).append(providerTaxonomyCode).append(secondLineMailingAddress).append(secondLinePracticeLocationAddress).toHashCode();
//    }

	public String getOrgName() {
        return this.orgName;
    }

	public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	public String getOtherOrgName() {
        return this.otherOrgName;
    }

	public void setOtherOrgName(String otherOrgName) {
        this.otherOrgName = otherOrgName;
    }

	public String getAuthorizedOfficialLastName() {
        return this.authorizedOfficialLastName;
    }

	public void setAuthorizedOfficialLastName(String authorizedOfficialLastName) {
        this.authorizedOfficialLastName = authorizedOfficialLastName;
    }

	public String getAuthorizedOfficialFirstName() {
        return this.authorizedOfficialFirstName;
    }

	public void setAuthorizedOfficialFirstName(String authorizedOfficialFirstName) {
        this.authorizedOfficialFirstName = authorizedOfficialFirstName;
    }

	public String getAuthorizedOfficialTitle() {
        return this.authorizedOfficialTitle;
    }

	public void setAuthorizedOfficialTitle(String authorizedOfficialTitle) {
        this.authorizedOfficialTitle = authorizedOfficialTitle;
    }

	public String getAuthorizedOfficialNamePrefix() {
        return this.authorizedOfficialNamePrefix;
    }

	public void setAuthorizedOfficialNamePrefix(String authorizedOfficialNamePrefix) {
        this.authorizedOfficialNamePrefix = authorizedOfficialNamePrefix;
    }

	public String getAuthorizedOfficialTelephoneNumber() {
        return this.authorizedOfficialTelephoneNumber;
    }

	public void setAuthorizedOfficialTelephoneNumber(String authorizedOfficialTelephoneNumber) {
        this.authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber;
    }
	public void setServices(Set<ServiceCode> services) {
		this.services = services;
	}
	
	public Set<ServiceCode> getServices() {
		return services;
	}
	
}
