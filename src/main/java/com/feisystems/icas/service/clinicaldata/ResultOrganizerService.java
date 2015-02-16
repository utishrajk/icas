package com.feisystems.icas.service.clinicaldata;
import com.feisystems.icas.domain.clinicaldata.ResultOrganizer;
import com.feisystems.icas.service.dto.OutcomeDto;
import com.feisystems.icas.service.dto.ResultOrganizerDto;

import java.util.List;

public interface ResultOrganizerService {

	public abstract long countAllResultOrganizers();


	public abstract void deleteResultOrganizer(ResultOrganizerDto resultOrganizerDto);


	public abstract ResultOrganizerDto findResultOrganizer(Long id);


	public abstract List<ResultOrganizerDto> findAllResultOrganizers();


	public abstract List<ResultOrganizer> findResultOrganizerEntries(int firstResult, int maxResults);


	public abstract void saveResultOrganizer(ResultOrganizerDto resultOrganizerDto);


	public abstract ResultOrganizer updateResultOrganizer(ResultOrganizerDto resultOrganizerDto);
	
	public abstract List<ResultOrganizerDto> findAllResultOrganizerByPatientId(Long patientId);
}
