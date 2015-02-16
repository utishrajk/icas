package com.feisystems.icas.service.reference;
import com.feisystems.icas.domain.reference.FacilityTypeCode;
import java.util.List;

public interface FacilityTypeCodeService {

	public abstract long countAllFacilityTypeCodes();


	public abstract void deleteFacilityTypeCode(FacilityTypeCode facilityTypeCode);


	public abstract FacilityTypeCode findFacilityTypeCode(Long id);


	public abstract List<FacilityTypeCode> findAllFacilityTypeCodes();


	public abstract List<FacilityTypeCode> findFacilityTypeCodeEntries(int firstResult, int maxResults);


	public abstract void saveFacilityTypeCode(FacilityTypeCode facilityTypeCode);


	public abstract FacilityTypeCode updateFacilityTypeCode(FacilityTypeCode facilityTypeCode);

}
