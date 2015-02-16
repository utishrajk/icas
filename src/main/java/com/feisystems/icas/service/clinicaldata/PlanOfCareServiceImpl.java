package com.feisystems.icas.service.clinicaldata;

import com.feisystems.icas.domain.clinicaldata.PlanOfCare;
import com.feisystems.icas.domain.clinicaldata.PlanOfCareRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlanOfCareServiceImpl implements PlanOfCareService {

	@Autowired
    PlanOfCareRepository planOfCareRepository;

	public long countAllPlanOfCares() {
        return planOfCareRepository.count();
    }

	public void deletePlanOfCare(PlanOfCare planOfCare) {
        planOfCareRepository.delete(planOfCare);
    }

	public PlanOfCare findPlanOfCare(Long id) {
        return planOfCareRepository.findOne(id);
    }

	public List<PlanOfCare> findAllPlanOfCares() {
        return planOfCareRepository.findAll();
    }

	public List<PlanOfCare> findPlanOfCareEntries(int firstResult, int maxResults) {
        return planOfCareRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void savePlanOfCare(PlanOfCare planOfCare) {
        planOfCareRepository.save(planOfCare);
    }

	public PlanOfCare updatePlanOfCare(PlanOfCare planOfCare) {
        return planOfCareRepository.save(planOfCare);
    }
}
