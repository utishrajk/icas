package com.feisystems.icas.domain.clinicaldata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonForVisitRepository extends JpaSpecificationExecutor<ReasonForVisit>, JpaRepository<ReasonForVisit, Long> {
}
