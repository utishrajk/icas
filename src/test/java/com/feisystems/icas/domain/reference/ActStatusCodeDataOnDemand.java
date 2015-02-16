package com.feisystems.icas.domain.reference;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.feisystems.icas.service.reference.ActStatusCodeService;

@Component
@Configurable
public class ActStatusCodeDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ActStatusCode> data;

	@Autowired
	ActStatusCodeService actStatusCodeService;

	@Autowired
	ActStatusCodeRepository actStatusCodeRepository;

	public ActStatusCode getNewTransientActStatusCode(int index) {
		ActStatusCode obj = new ActStatusCode();
		setCode(obj, index);
		setCodeSystem(obj, index);
		setCodeSystemName(obj, index);
		setCodeSystemVersion(obj, index);
		setDisplayName(obj, index);
		setOriginalText(obj, index);
		return obj;
	}

	public void setCode(ActStatusCode obj, int index) {
		String code = "code_" + index;
		if (code.length() > 250) {
			code = code.substring(0, 250);
		}
		obj.setCode(code);
	}

	public void setCodeSystem(ActStatusCode obj, int index) {
		String codeSystem = "codeSystem_" + index;
		if (codeSystem.length() > 250) {
			codeSystem = codeSystem.substring(0, 250);
		}
		obj.setCodeSystem(codeSystem);
	}

	public void setCodeSystemName(ActStatusCode obj, int index) {
		String codeSystemName = "codeSystemName_" + index;
		if (codeSystemName.length() > 250) {
			codeSystemName = codeSystemName.substring(0, 250);
		}
		obj.setCodeSystemName(codeSystemName);
	}

	public void setCodeSystemVersion(ActStatusCode obj, int index) {
		Double codeSystemVersion = new Integer(index).doubleValue();
		obj.setCodeSystemVersion(codeSystemVersion);
	}

	public void setDisplayName(ActStatusCode obj, int index) {
		String displayName = "displayName_" + index;
		if (displayName.length() > 250) {
			displayName = displayName.substring(0, 250);
		}
		obj.setDisplayName(displayName);
	}

	public void setOriginalText(ActStatusCode obj, int index) {
		String originalText = "originalText_" + index;
		if (originalText.length() > 250) {
			originalText = originalText.substring(0, 250);
		}
		obj.setOriginalText(originalText);
	}

	public ActStatusCode getSpecificActStatusCode(int index) {
		return null;
	}

	public ActStatusCode getRandomActStatusCode() {
		return null;
	}

	public boolean modifyActStatusCode(ActStatusCode obj) {
		return false;
	}

	public void init() {
	}
}
