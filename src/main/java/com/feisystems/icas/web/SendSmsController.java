package com.feisystems.icas.web;

public interface SendSmsController {
	
	void sendSms(String individualProviderNumber, String messageBody);

}
