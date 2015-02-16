package com.feisystems.icas.domain.commondomainservices;

import com.feisystems.icas.service.dto.SmsDto;

public interface SmsService {
	
	public void sendSms(SmsDto smsDto);

}
