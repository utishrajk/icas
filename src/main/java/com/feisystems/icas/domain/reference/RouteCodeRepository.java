package com.feisystems.icas.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteCodeRepository extends JpaRepository<RouteCode, Long>, JpaSpecificationExecutor<RouteCode> {
	public abstract RouteCode findByCode(String code);	
}
