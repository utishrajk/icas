package com.feisystems.icas.service.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.feisystems.icas.domain.client.Entry;
import com.feisystems.icas.domain.client.Feed;
import com.feisystems.icas.domain.client.Link;
import com.feisystems.icas.domain.client.MedlinePlus;
import com.feisystems.icas.domain.reference.AllergenCode;
import com.feisystems.icas.domain.reference.AllergenCodeRepository;
import com.feisystems.icas.domain.reference.MedicationCode;
import com.feisystems.icas.domain.reference.MedicationCodeRepository;
import com.feisystems.icas.domain.reference.ProblemCode;
import com.feisystems.icas.domain.reference.ProblemCodeRepository;
import com.feisystems.icas.domain.reference.ProcedureTypeCode;
import com.feisystems.icas.domain.reference.ProcedureTypeCodeRepository;
import com.feisystems.icas.domain.reference.ResultOrganizerCode;
import com.feisystems.icas.domain.reference.ResultOrganizerCodeRepository;
import com.feisystems.icas.domain.reference.SocialHistoryTypeCode;
import com.feisystems.icas.domain.reference.SocialHistoryTypeCodeRepository;
import com.feisystems.icas.service.clinicaldata.ProblemService;

@Service
@Transactional
public class MedlinePlusServiceImpl implements MedlinePlusService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ProblemCodeRepository problemCodeRepository;
	
	@Autowired
	AllergenCodeRepository allergenCodeRepository;
	
	@Autowired
	MedicationCodeRepository medicationCodeRepository;
	
	@Autowired
	
	ResultOrganizerCodeRepository resultOrganizerCodeRepository;
	
	@Autowired
	SocialHistoryTypeCodeRepository socialHistoryTypeCodeRepository;
	
	@Autowired
	ProcedureTypeCodeRepository procedureTypeCodeRepository;
	 
	@Override
	public List<Link> getLinks(String code, String type) {
		    String url = null;	
		    String codeSystem = null;
		    List<Link> links = new ArrayList<Link>();
		    
		    if( type.equalsIgnoreCase("Procedure" )  && (code != null) ){
		    	ProcedureTypeCode procedureCode = procedureTypeCodeRepository.findByCode(code);
		    	codeSystem = procedureCode.getCodeSystem();
		    }else if( type.equalsIgnoreCase("SocialHistory")  && (code != null) ){
		    	SocialHistoryTypeCode sCode = socialHistoryTypeCodeRepository.findByCode(code);
		    	codeSystem = sCode.getCodeSystem();
		    }else if(  type.equalsIgnoreCase("Condition")  && (code != null) ){
		    	ProblemCode pCode = problemCodeRepository.findByCode(code);
		    	codeSystem = pCode.getCodeSystem();
		    }else if(  type.equalsIgnoreCase("Allergy")  && (code != null) ){
		    	AllergenCode pCode = allergenCodeRepository.findByCode(code);
		    	codeSystem = pCode.getCodeSystem();
		    }else if(  type.equalsIgnoreCase("Medication")  && (code != null) ){
		    	MedicationCode pCode = medicationCodeRepository.findByCode(code);
		    	codeSystem = pCode.getCodeSystem();
		    }else if(  type.equalsIgnoreCase("LabResult")  && (code != null) ){
		    	ResultOrganizerCode pCode = resultOrganizerCodeRepository.findByCode(code);
		    	codeSystem = pCode.getCodeSystem();
		    }
		     
		    if(codeSystem != null){
		    	 url = "http://apps2.nlm.nih.gov/medlineplus/services/mpconnect_service.cfm?mainSearchCriteria.v.cs=" +
		    			codeSystem + "&mainSearchCriteria.v.c=" + code + "&knowledgeResponseType=application/json";
		    	 
		    	MedlinePlus medlinePlus = restTemplate.getForObject(url, MedlinePlus.class);			
				Feed feed = medlinePlus.getFeed();
				
				if(feed != null){				
					Collection<Entry> entries =  feed.getEntry();
					
					if (entries != null &&  entries.size() > 0) {
						for(Entry entry: entries){
							links.addAll(entry.getLink());
						}	
						return links;
					}					
				}
				//Creating an empty link
				Link link = new Link();
				link.setHref("");
				link.setRel("");
				link.setTitle("No entry");
				link.setType("");
				links.add(link);
				
				return links;
		    }
			
			throw new IllegalArgumentException("No MedlinePlus Link Found");
	}
	
	
}
