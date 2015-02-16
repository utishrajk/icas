package com.feisystems.icas.domain.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.feisystems.icas.domain.provider.FeedbackTestContext;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;
import com.feisystems.icas.domain.reference.RaceCode;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;
import com.feisystems.icas.service.provider.services.IndividualProviderFeedbackServicesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FeedbackTestContext.class})
@WebAppConfiguration
public class IndividualProviderFeedbackControllerTest {

    @Autowired
    IndividualProviderFeedbackServicesService serviceMock;
    
    IndividualProviderFeedbackServiceDto first, second;
	RaceCode mockAfricanRaceCode, mockAsianRaceCode;
	
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(serviceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		first = new FeedbackDtoBuilder()
	      .id(1L)
	      .name("ServiceOne")
	      .ratingId(5L)
	      .serviceId(1L)
	      .individualproviderId(1L)
	      .build();
		
		second = new FeedbackDtoBuilder()
		.id(2L)
		.name("ServiceTwo")
		.ratingId(4L)
		.serviceId(2L)
		.individualproviderId(2L)
		.build();
		
    }
 
	@Test
    public void testFindAll_ShouldReturnFoundFeedbackEntries() throws Exception {
		
        when(serviceMock.findServicesByIndividualProviderId(1L)).thenReturn(Arrays.asList(first, second));
		mockMvc.perform(get("/individualprovidersfeedback/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("ServiceOne")))
				.andExpect(jsonPath("$[0].rating_id", is(5)))
                .andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].individualprovider_id", is(2)));
        verify(serviceMock, times(1)).findServicesByIndividualProviderId(1L);
        verifyNoMoreInteractions(serviceMock);
        
    }
	
	@Test
    public void testPutOne_ShouldUpdateSpecificFeedbackEntry() throws Exception {
		
        when(serviceMock.updateIndividualProviderService(any(IndividualProviderFeedbackServiceDto.class))).thenReturn(new IndividualProviderFeedbackServices());
        
		mockMvc.perform(put("/individualprovidersfeedback/5", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(first))
		)
                .andExpect(status().isOk());
        ArgumentCaptor<IndividualProviderFeedbackServiceDto> dtoCaptor = ArgumentCaptor.forClass(IndividualProviderFeedbackServiceDto.class);

        verify(serviceMock, times(1)).updateIndividualProviderService(dtoCaptor.capture());
        verifyNoMoreInteractions(serviceMock);
        
    }
	
	@Test
	public void testPutOne_ShouldAssignFeedbackServicesToIndividualProvider() throws Exception {
		
		mockMvc.perform(post("/individualprovidersfeedback/assign")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(first))
        )
              	.andExpect(status().isCreated());

		ArgumentCaptor<IndividualProviderFeedbackServiceDto> dtoCaptor = ArgumentCaptor.forClass(IndividualProviderFeedbackServiceDto.class);
		
		verify(serviceMock, times(1)).saveIndividualProviderFeedbackServices(dtoCaptor.capture());
		verifyNoMoreInteractions(serviceMock);
		
	}
	
}
