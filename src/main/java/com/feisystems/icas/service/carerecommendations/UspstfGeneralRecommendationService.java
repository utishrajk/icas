package com.feisystems.icas.service.carerecommendations;
import com.feisystems.icas.domain.carerecommendations.UspstfGeneralRecommendation;
import java.util.List;

public interface UspstfGeneralRecommendationService {

	public abstract long countAllUspstfGeneralRecommendations();


	public abstract void deleteUspstfGeneralRecommendation(UspstfGeneralRecommendation uspstfGeneralRecommendation);


	public abstract UspstfGeneralRecommendation findUspstfGeneralRecommendation(Long id);


	public abstract List<UspstfGeneralRecommendation> findAllUspstfGeneralRecommendations();


	public abstract List<UspstfGeneralRecommendation> findUspstfGeneralRecommendationEntries(int firstResult, int maxResults);


	public abstract void saveUspstfGeneralRecommendation(UspstfGeneralRecommendation uspstfGeneralRecommendation);


	public abstract UspstfGeneralRecommendation updateUspstfGeneralRecommendation(UspstfGeneralRecommendation uspstfGeneralRecommendation);

}
