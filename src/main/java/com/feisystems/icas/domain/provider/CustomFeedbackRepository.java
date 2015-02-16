package com.feisystems.icas.domain.provider;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@NoRepositoryBean
public interface CustomFeedbackRepository {
	
	List<Object[]> findIps(Long id);
	
}
