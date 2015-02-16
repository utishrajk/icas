package com.feisystems.icas.service.carerecommendations;
import com.feisystems.icas.domain.carerecommendations.UspstfRecommendationTool;
import java.util.List;

public interface UspstfRecommendationToolService {

	public abstract long countAllUspstfRecommendationTools();


	public abstract void deleteUspstfRecommendationTool(UspstfRecommendationTool uspstfRecommendationTool);


	public abstract UspstfRecommendationTool findUspstfRecommendationTool(Long id);


	public abstract List<UspstfRecommendationTool> findAllUspstfRecommendationTools();


	public abstract List<UspstfRecommendationTool> findUspstfRecommendationToolEntries(int firstResult, int maxResults);


	public abstract void saveUspstfRecommendationTool(UspstfRecommendationTool uspstfRecommendationTool);


	public abstract UspstfRecommendationTool updateUspstfRecommendationTool(UspstfRecommendationTool uspstfRecommendationTool);

}
