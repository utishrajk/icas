package com.feisystems.icas.service.clinicaldata;
import com.feisystems.icas.domain.clinicaldata.VitalSignObservation;
import java.util.List;

public interface VitalSignObservationService {

	public abstract long countAllVitalSignObservations();


	public abstract void deleteVitalSignObservation(VitalSignObservation vitalSignObservation);


	public abstract VitalSignObservation findVitalSignObservation(Long id);


	public abstract List<VitalSignObservation> findAllVitalSignObservations();


	public abstract List<VitalSignObservation> findVitalSignObservationEntries(int firstResult, int maxResults);


	public abstract void saveVitalSignObservation(VitalSignObservation vitalSignObservation);


	public abstract VitalSignObservation updateVitalSignObservation(VitalSignObservation vitalSignObservation);

}
