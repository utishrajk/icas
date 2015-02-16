package com.feisystems.icas.web;
import com.feisystems.icas.service.dto.OrganizationalProviderDto;
import com.feisystems.icas.service.provider.OrganizationalProviderService;
import com.feisystems.icas.service.reference.ProviderTaxonomyCodeService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/organizationalproviders")
public class OrganizationalProviderController {
	
	@Autowired
    OrganizationalProviderService organizationalProviderService;

	@Autowired
    ProviderTaxonomyCodeService providerTaxonomyCodeService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public OrganizationalProviderDto showJson(@PathVariable("id") Long id) {
        OrganizationalProviderDto organizationalProviderDto = organizationalProviderService.findOrganizationalProvider(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return organizationalProviderDto;
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public List<OrganizationalProviderDto> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<OrganizationalProviderDto> result = organizationalProviderService.findAllOrganizationalProviders();
        return result;
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody OrganizationalProviderDto organizationalProviderDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpStatus httpStatus = HttpStatus.CREATED;
        organizationalProviderService.saveOrganizationalProvider(organizationalProviderDto);
        
        return new ResponseEntity<String>(headers, httpStatus);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody OrganizationalProviderDto organizationalProviderDto, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (organizationalProviderService.updateOrganizationalProvider(organizationalProviderDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
//    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
////        OrganizationalProviderDto organizationalProviderDto = organizationalProviderService.findOrganizationalProvider(id);
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Type", "application/json");
////        if (organizationalProviderDto == null) {
////            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
////        }
////        organizationalProviderService.deleteOrganizationalProvider(organizationalProviderDto);
////        return new ResponseEntity<String>(headers, HttpStatus.OK);
//    }

}
