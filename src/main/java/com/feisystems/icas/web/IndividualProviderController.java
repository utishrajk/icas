package com.feisystems.icas.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.feisystems.icas.domain.provider.VerificationToken;
import com.feisystems.icas.service.dto.IndividualProviderDto;
import com.feisystems.icas.service.provider.IndividualProviderService;
import com.feisystems.icas.service.provider.VerificationTokenService;
import com.feisystems.icas.service.reference.AdministrativeGenderCodeService;
import com.feisystems.icas.service.reference.ProviderTaxonomyCodeService;

@Controller
@RequestMapping("/individualproviders")
public class IndividualProviderController {

    @Autowired
    IndividualProviderService individualProviderService;

    @Autowired
    AdministrativeGenderCodeService administrativeGenderCodeService;

    @Autowired
    ProviderTaxonomyCodeService providerTaxonomyCodeService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public IndividualProviderDto showJson(@PathVariable("id") Long id) {
        IndividualProviderDto individualProviderDto = individualProviderService.findIndividualProvider(id);
        individualProviderDto.setCredential("");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return individualProviderDto;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody IndividualProviderDto individualProviderDto, @PathVariable("username") Long username) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        isValidRequest(individualProviderDto);
        if (individualProviderService.updateIndividualProvider(individualProviderDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<IndividualProviderDto> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<IndividualProviderDto> result = individualProviderService.findAllIndividualProviders();
        return result;
    }

    public void isValidRequest(IndividualProviderDto dto) {
        if (dto.getFirstName() == null || dto.getLastName() == null) {
            throw new IllegalArgumentException("Both FirstName and LastName are Required");
        }
    }
}
