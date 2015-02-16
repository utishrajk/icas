package com.feisystems.icas.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageAbilityCodeRepository extends JpaSpecificationExecutor<LanguageAbilityCode>, JpaRepository<LanguageAbilityCode, Long> {
}
