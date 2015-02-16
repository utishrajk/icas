package com.feisystems.icas.domain.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.feisystems.icas.domain.patient.PatientTestContext;
import com.feisystems.icas.service.dto.LookupDto;
import com.feisystems.icas.service.dto.PatientProfileDto;
import com.feisystems.icas.service.reference.RaceCodeService;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PatientTestContext.class})
@WebAppConfiguration
public class RaceCodeControllerTest {

    @Autowired
	RaceCodeService raceCodeServiceMock;

	PatientProfileDto first, second;
	LookupDto mockAfricanRaceCode, mockAsianRaceCode;
	
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {

    	Mockito.reset(raceCodeServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockAsianRaceCode = new LookupDtoBuilder()
        						.displayName("Asian")
    							.code("2028-9")
        						.build();

        mockAfricanRaceCode = new LookupDtoBuilder()
								.displayName("Black or African American")
								.code("2054-5")
								.build();

    }
 
	@Test
    public void testFindAll_ShouldReturnFoundPatientEntries() throws Exception {
		
        when(raceCodeServiceMock.findAllRaceCodes()).thenReturn(Arrays.asList(mockAsianRaceCode, mockAfricanRaceCode));

		mockMvc.perform(get("/racecodes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", is("2028-9")))
				.andExpect(jsonPath("$[0].displayName", is("Asian")))
                .andExpect(jsonPath("$[1].code", is("2054-5")))
				.andExpect(jsonPath("$[1].displayName", is("Black or African American")));
                
        verify(raceCodeServiceMock, times(1)).findAllRaceCodes();
        verifyNoMoreInteractions(raceCodeServiceMock);
        
    }
	
}
