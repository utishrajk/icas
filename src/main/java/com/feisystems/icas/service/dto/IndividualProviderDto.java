package com.feisystems.icas.service.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IndividualProviderDto extends AbstractProviderDto {

	private String userName;

	private String credential;

	private String confirmPassword;

	private String lastName;

	private String firstName;

	private String middleName;

	private String namePrefixCode;

	private String nameSuffix;

	private String administrativeGenderCode;

	private String providerTaxonomyCode;

	private String providerTaxonomyCodeDisplayName;
	
	private List<String> roles;
	
	private String securityQuestion1;
	
	private String securityQuestion2;
	
	private String securityAnswer1;
	
	private String securityAnswer2;
	
	private String token;
	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	private Date dateOfBirth;
	
	public IndividualProviderDto() {
		super();
	}

	public IndividualProviderDto(Long id, String userName, String firstName,
			String lastName, String email, List<String> roles) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setId(id);
		this.setEmail(email);
		this.roles = roles;
	}

	public String getProviderTaxonomyCode() {
		return providerTaxonomyCode;
	}

	public void setProviderTaxonomyCode(String providerTaxonomyCode) {
		this.providerTaxonomyCode = providerTaxonomyCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNamePrefixCode() {
		return namePrefixCode;
	}

	public void setNamePrefixCode(String namePrefixCode) {
		this.namePrefixCode = namePrefixCode;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	public void setAdministrativeGenderCode(String administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	public String getProviderTaxonomyCodeDisplayName() {
		return providerTaxonomyCodeDisplayName;
	}

	public void setProviderTaxonomyCodeDisplayName(
			String providerTaxonomyCodeDisplayName) {
		this.providerTaxonomyCodeDisplayName = providerTaxonomyCodeDisplayName;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSecurityQuestion1() {
		return securityQuestion1;
	}

	public void setSecurityQuestion1(String securityQuestion1) {
		this.securityQuestion1 = securityQuestion1;
	}

	public String getSecurityQuestion2() {
		return securityQuestion2;
	}

	public void setSecurityQuestion2(String securityQuestion2) {
		this.securityQuestion2 = securityQuestion2;
	}

	public String getSecurityAnswer1() {
		return securityAnswer1;
	}

	public void setSecurityAnswer1(String securityAnswer1) {
		this.securityAnswer1 = securityAnswer1;
	}

	public String getSecurityAnswer2() {
		return securityAnswer2;
	}

	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
