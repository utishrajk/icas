package com.feisystems.icas.domain.clinicaldata;
import com.feisystems.icas.domain.clinicaldata.VitalObservationRepository;
import com.feisystems.icas.domain.clinicaldata.VitalSignObservation;
import com.feisystems.icas.domain.reference.ActStatusCodeDataOnDemand;
import com.feisystems.icas.domain.reference.UnitOfMeasureCode;
import com.feisystems.icas.domain.valueobject.CodedConceptValueObject;
import com.feisystems.icas.domain.valueobject.QuantityType;
import com.feisystems.icas.service.clinicaldata.VitalSignObservationService;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class VitalSignObservationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<VitalSignObservation> data;

	@Autowired
    ActStatusCodeDataOnDemand actStatusCodeDataOnDemand;

	@Autowired
    VitalSignObservationService vitalSignObservationService;

	@Autowired
    VitalObservationRepository vitalObservationRepository;

	public VitalSignObservation getNewTransientVitalSignObservation(int index) {
        VitalSignObservation obj = new VitalSignObservation();
        setVitalSignObservationType(obj, index);
        setVitalSignObservationValue(obj, index);
        setResultReferenceRange(obj, index);
        setVitalSignObservationDateTime(obj, index);
        return obj;
    }

	public void setVitalSignObservationType(VitalSignObservation obj, int index) {
        CodedConceptValueObject embeddedClass = new CodedConceptValueObject();
        setVitalSignObservationTypeCode(embeddedClass, index);
        setVitalSignObservationTypeCodeSystem(embeddedClass, index);
        setVitalSignObservationTypeDisplayName(embeddedClass, index);
        setVitalSignObservationTypeCodeSystemName(embeddedClass, index);
        setVitalSignObservationTypeOriginalText(embeddedClass, index);
        setVitalSignObservationTypeCodeSystemVersion(embeddedClass, index);
        obj.setVitalSignObservationType(embeddedClass);
    }

	public void setVitalSignObservationTypeCode(CodedConceptValueObject obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setVitalSignObservationTypeCodeSystem(CodedConceptValueObject obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setVitalSignObservationTypeDisplayName(CodedConceptValueObject obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setVitalSignObservationTypeCodeSystemName(CodedConceptValueObject obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setVitalSignObservationTypeOriginalText(CodedConceptValueObject obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public void setVitalSignObservationTypeCodeSystemVersion(CodedConceptValueObject obj, int index) {
        Double codeSystemVersion = new Integer(index).doubleValue();
        obj.setCodeSystemVersion(codeSystemVersion);
    }

	public void setVitalSignObservationValue(VitalSignObservation obj, int index) {
        QuantityType embeddedClass = new QuantityType();
        setVitalSignObservationValueMeasuredValue(embeddedClass, index);
        setVitalSignObservationValueUnitOfMeasureCode(embeddedClass, index);
        obj.setVitalSignObservationValue(embeddedClass);
    }

	public void setVitalSignObservationValueMeasuredValue(QuantityType obj, int index) {
        Double measuredValue = new Integer(index).doubleValue();
        obj.setMeasuredValue(measuredValue);
    }

	public void setVitalSignObservationValueUnitOfMeasureCode(QuantityType obj, int index) {
        UnitOfMeasureCode unitOfMeasureCode = null;
        obj.setUnitOfMeasureCode(unitOfMeasureCode);
    }

	public void setResultReferenceRange(VitalSignObservation obj, int index) {
        String resultReferenceRange = "resultReferenceRange_" + index;
        obj.setResultReferenceRange(resultReferenceRange);
    }

	public void setVitalSignObservationDateTime(VitalSignObservation obj, int index) {
        Date vitalSignObservationDateTime = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setVitalSignObservationDateTime(vitalSignObservationDateTime);
    }

	public VitalSignObservation getSpecificVitalSignObservation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        VitalSignObservation obj = data.get(index);
        Long id = obj.getId();
        return vitalSignObservationService.findVitalSignObservation(id);
    }

	public VitalSignObservation getRandomVitalSignObservation() {
        init();
        VitalSignObservation obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return vitalSignObservationService.findVitalSignObservation(id);
    }

	public boolean modifyVitalSignObservation(VitalSignObservation obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = vitalSignObservationService.findVitalSignObservationEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'VitalSignObservation' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<VitalSignObservation>();
        for (int i = 0; i < 10; i++) {
            VitalSignObservation obj = getNewTransientVitalSignObservation(i);
            try {
                vitalSignObservationService.saveVitalSignObservation(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            vitalObservationRepository.flush();
            data.add(obj);
        }
    }
}
