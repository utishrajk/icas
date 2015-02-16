package com.feisystems.icas.domain.provider;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Configuration
public class CustomFeedbackRepositoryImpl implements CustomFeedbackRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findIps(Long id) {
		 //use JPA query to select columns from different tables
		 Query nativeQuery = entityManager.createNativeQuery( "SELECT s.service_id, s.name, ips.rating_id, ips.id, ips.individualprovider_id "
															+ "FROM feedback_services_code s, feedback_ratings_code r, individual_provider_feedback_services ips "
															+ "WHERE ips.service_id = s.service_id AND ips.rating_id = r.rating_id AND ips.individualprovider_id = ? "
															+ "ORDER BY service_id ASC;");
		 
		 nativeQuery.setParameter(1, id);
		 return nativeQuery.getResultList();
	}

}