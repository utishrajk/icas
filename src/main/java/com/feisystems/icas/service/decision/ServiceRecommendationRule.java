package com.feisystems.icas.service.decision;

import com.feisystems.icas.domain.decisionfacts.RuleExecutionContainer;


public interface ServiceRecommendationRule {

    //todo: Make a DTO
    public RuleExecutionContainer recommendService(String problemCode,
			String problemCodeSystem, String socialHistoryCode, String age, String race,
			String genderCode, String zipCode);
    
    
}
