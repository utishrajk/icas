package com.feisystems.icas.domain.patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaSpecificationExecutor<Patient>, JpaRepository<Patient, Long> {
}
