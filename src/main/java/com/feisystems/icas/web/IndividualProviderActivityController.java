package com.feisystems.icas.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.icas.service.dto.ActivityDto;
import com.feisystems.icas.service.provider.IndividualProviderActivityService;

@Controller
@RequestMapping("/individualprovidersactivity")
public class IndividualProviderActivityController {
	
	@Autowired
	IndividualProviderActivityService activityService;


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public  List<ActivityDto> listJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<ActivityDto> result = activityService.findActivitiesByIndividualProviderId(id);
        return result;
    }

}
