package com.feisystems.icas.service.reference;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ServiceCode;
import com.feisystems.icas.domain.reference.ServiceCodeRepository;

@Service
@Transactional
public class ServiceCodeServiceImpl implements ServiceCodeService{
	
	@Autowired
    ServiceCodeRepository serviceCodeRepository;

	@Override
	public long countAllServiceCodes() {
		return serviceCodeRepository.count();
	}

	@Override
	public void deleteServiceCode(ServiceCode serviceCode) {
		serviceCodeRepository.delete(serviceCode);
	}

	
	@Override
	public List<ServiceCode> findAllServiceCodes() {
		return serviceCodeRepository.findAll();
	}

	@Override
	public List<ServiceCode> findServiceCodeEntries(int firstResult,int maxResults) {
		 return serviceCodeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
	}

	@Override
	public void saveServiceCode(ServiceCode serviceCode) {
		serviceCodeRepository.save(serviceCode);
	}

	@Override
	public ServiceCode updateServiceCode(ServiceCode serviceCode) {
		 return serviceCodeRepository.save(serviceCode);
	}

	@Override
	public ServiceCode findServiceCode(Long id) {
		return serviceCodeRepository.findOne(id);
	}

	@Override
	public ServiceCode findServiceCode(String code) {
		return serviceCodeRepository.findByCode(code);
	}
	
	
}
