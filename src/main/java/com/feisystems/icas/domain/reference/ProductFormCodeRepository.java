package com.feisystems.icas.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFormCodeRepository extends JpaSpecificationExecutor<ProductFormCode>, JpaRepository<ProductFormCode, Long> {
	public abstract ProductFormCode findByCode(String code);
}
