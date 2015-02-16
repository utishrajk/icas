package com.feisystems.icas.domain.reference;
import com.feisystems.icas.domain.reference.ServiceCode;
import com.feisystems.icas.domain.reference.ServiceCodeRepository;
import com.feisystems.icas.service.reference.ServiceCodeService;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class ServiceCodeDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ServiceCode> data;

	@Autowired
    ServiceCodeService ServiceCodeService;

	@Autowired
    ServiceCodeRepository serviceCodeRepository;

	public ServiceCode getNewTransientServiceCode(int index) {
        ServiceCode obj = new ServiceCode();
        setCode(obj, index);
        setCodeSystem(obj, index);
        setCodeSystemName(obj, index);
        setCodeSystemVersion(obj, index);
        setDisplayName(obj, index);
        setOriginalText(obj, index);
        return obj;
    }

	public void setCode(ServiceCode obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setCodeSystem(ServiceCode obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setCodeSystemName(ServiceCode obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setCodeSystemVersion(ServiceCode obj, int index) {
        Double codeSystemVersion = new Integer(index).doubleValue();
        obj.setCodeSystemVersion(codeSystemVersion);
    }

	public void setDisplayName(ServiceCode obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setOriginalText(ServiceCode obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public ServiceCode getSpecificServiceCode(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ServiceCode obj = data.get(index);
        Long id = obj.getId();
        return serviceCodeRepository.findOne(id);
    }

	public ServiceCode getRandomServiceCode() {
        init();
        ServiceCode obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return serviceCodeRepository.findOne(id);
    }

	public boolean modifyServiceCode(ServiceCode obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;;
        data = serviceCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'RaceCode' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ServiceCode>();
        for (int i = 0; i < 10; i++) {
        	ServiceCode obj = getNewTransientServiceCode(i);
            try {
            	serviceCodeRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            serviceCodeRepository.flush();
            data.add(obj);
        }
    }
}
