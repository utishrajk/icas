package com.feisystems.icas.domain.controller;
//package com.feisystems.icas.domain.controller;
//
//import com.feisystems.icas.domain.provider.OrganizationalProviderTestContext;
//import com.feisystems.icas.domain.reference.ServiceCode;
//import com.feisystems.icas.service.reference.ServiceCodeService;
//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {OrganizationalProviderTestContext.class})
//@WebAppConfiguration
//public class ServiceCodeControllerTest {
//
//    @Autowired
//	ServiceCodeService serviceCodeServiceMock;
//
//    ServiceCode mockServiceCode1, mockServiceCode2;
//	
//	private MockMvc mockMvc;
////	
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Before
//    public void setUp() {
//
//    	Mockito.reset(serviceCodeServiceMock);
//
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        mockServiceCode1 = new ServiceCode();
//        mockServiceCode1.setDisplayName("Behavioral health counseling and therapy, per 15 minutes");
//		mockServiceCode1.setCode("H0018");
//
//        mockServiceCode2 = new ServiceCode();
//        mockServiceCode2.setDisplayName("Alcohol and/or drug services; case management");
//		mockServiceCode2.setCode("H0006");
//
//    }
// 
//	@Test
//    public void testFindAll_ShouldReturnFoundServiceEntries() throws Exception {
//		
//        when(serviceCodeServiceMock.findAllServiceCodes()).thenReturn(Arrays.asList(mockServiceCode1, mockServiceCode2));
//
//		mockMvc.perform(get("/servicecodes"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].code", is("H0018")))
//				.andExpect(jsonPath("$[0].displayName", is("Behavioral health counseling and therapy, per 15 minutes")))
//                .andExpect(jsonPath("$[1].code", is("H0006")))
//				.andExpect(jsonPath("$[1].displayName", is("Alcohol and/or drug services; case management")));
//                
//        verify(serviceCodeServiceMock, times(1)).findAllServiceCodes();
//        verifyNoMoreInteractions(serviceCodeServiceMock);
//        
//    }
//	
//}
