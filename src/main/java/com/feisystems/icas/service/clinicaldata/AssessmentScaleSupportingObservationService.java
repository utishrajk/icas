package com.feisystems.icas.service.clinicaldata;
import com.feisystems.icas.domain.clinicaldata.AssessmentScaleSupportingObservation;
import java.util.List;

public interface AssessmentScaleSupportingObservationService {

	public abstract long countAllAssessmentScaleSupportingObservations();


	public abstract void deleteAssessmentScaleSupportingObservation(AssessmentScaleSupportingObservation assessmentScaleSupportingObservation);


	public abstract AssessmentScaleSupportingObservation findAssessmentScaleSupportingObservation(Long id);


	public abstract List<AssessmentScaleSupportingObservation> findAllAssessmentScaleSupportingObservations();


	public abstract List<AssessmentScaleSupportingObservation> findAssessmentScaleSupportingObservationEntries(int firstResult, int maxResults);


	public abstract void saveAssessmentScaleSupportingObservation(AssessmentScaleSupportingObservation assessmentScaleSupportingObservation);


	public abstract AssessmentScaleSupportingObservation updateAssessmentScaleSupportingObservation(AssessmentScaleSupportingObservation assessmentScaleSupportingObservation);

}
