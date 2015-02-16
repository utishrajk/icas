package com.feisystems.icas.service.dto;

import java.util.HashSet;
import java.util.Set;


public class OrganizationalProviderDto extends AbstractProviderDto {
	
    private String orgName;

    private String otherOrgName;

    private String authorizedOfficialLastName;

    private String authorizedOfficialFirstName;

    private String authorizedOfficialTitle;

    private String authorizedOfficialNamePrefix;

	private String authorizedOfficialTelephoneNumber;
    
    private Set<String> services = new HashSet<String>();
    
    public Set<String> getServices() {
		return services;
	}

	public void setServices(Set<String> services) {
		this.services = services;
	}

	public OrganizationalProviderDto() {
	}
    
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOtherOrgName() {
		return otherOrgName;
	}

	public void setOtherOrgName(String otherOrgName) {
		this.otherOrgName = otherOrgName;
	}

	public String getAuthorizedOfficialLastName() {
		return authorizedOfficialLastName;
	}

	public void setAuthorizedOfficialLastName(String authorizedOfficialLastName) {
		this.authorizedOfficialLastName = authorizedOfficialLastName;
	}

	public String getAuthorizedOfficialFirstName() {
		return authorizedOfficialFirstName;
	}

	public void setAuthorizedOfficialFirstName(String authorizedOfficialFirstName) {
		this.authorizedOfficialFirstName = authorizedOfficialFirstName;
	}

	public String getAuthorizedOfficialTitle() {
		return authorizedOfficialTitle;
	}

	public void setAuthorizedOfficialTitle(String authorizedOfficialTitle) {
		this.authorizedOfficialTitle = authorizedOfficialTitle;
	}

	public String getAuthorizedOfficialNamePrefix() {
		return authorizedOfficialNamePrefix;
	}

	public void setAuthorizedOfficialNamePrefix(String authorizedOfficialNamePrefix) {
		this.authorizedOfficialNamePrefix = authorizedOfficialNamePrefix;
	}

	public String getAuthorizedOfficialTelephoneNumber() {
		return authorizedOfficialTelephoneNumber;
	}

	public void setAuthorizedOfficialTelephoneNumber(
			String authorizedOfficialTelephoneNumber) {
		this.authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber;
	}
}
