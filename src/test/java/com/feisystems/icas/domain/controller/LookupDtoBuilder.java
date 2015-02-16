package com.feisystems.icas.domain.controller;
import com.feisystems.icas.service.dto.LookupDto;

public class LookupDtoBuilder {
	
	private LookupDto dto;
	
	public LookupDtoBuilder() {
		dto = new LookupDto();
	}
	
	public LookupDtoBuilder code(String code) {
		dto.setCode(code); 	
		return this;
	}
	
	public LookupDtoBuilder displayName(String displayName) {
		dto.setDisplayName(displayName);
		return this;
	}
	
	public LookupDto build() {
		return dto;
	}
	
}
