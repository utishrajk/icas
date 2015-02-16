package com.feisystems.icas.service.provider;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.reference.ServiceCode;

@Component
public class SetOfObjectToSetOfStringContverter extends AbstractConverter<Set<ServiceCode>, Set<String>>{
	
	public SetOfObjectToSetOfStringContverter() {
	}

	@Override
	protected Set<String> convert(Set<ServiceCode> source) {
		Set<String> serviceCodeStringSet = new HashSet<String>();
		  
		  for(ServiceCode s : source){
			  serviceCodeStringSet.add(s.getCode());
		  }
	      return serviceCodeStringSet;
	}
}
