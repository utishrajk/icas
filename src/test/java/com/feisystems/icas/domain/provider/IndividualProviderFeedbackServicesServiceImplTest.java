package com.feisystems.icas.domain.provider;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.feisystems.icas.domain.provider.CustomFeedbackRepositoryImpl;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServicesRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;
import com.feisystems.icas.service.provider.services.CreateIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper;
import com.feisystems.icas.service.provider.services.IndividualProviderFeedbackServicesService;
import com.feisystems.icas.service.provider.services.IndividualProviderFeedbackServicesServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class IndividualProviderFeedbackServicesServiceImplTest {

	@InjectMocks
	private IndividualProviderFeedbackServicesService sut = new IndividualProviderFeedbackServicesServiceImpl();
	
	@Mock
	private IndividualProviderFeedbackServicesRepository feedbackServicesRepository;

	@Mock
	private CustomFeedbackRepositoryImpl individualProviderFeedbackServices;
	
	@Mock
	private DtoToDomainEntityMapper<IndividualProviderFeedbackServiceDto, IndividualProviderFeedbackServices> individualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper;

	@Mock
	private CreateIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper createIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper;

	
	@Test
	public void testFindFeedbackEntries() {
		
		// Arrange
		List<Object[]> feedbacks = new ArrayList<Object[]>();
		Object[] resultObjects = new Object[10]; 
		
		IndividualProviderFeedbackServiceDto serviceOne = new IndividualProviderFeedbackServiceDto();
		serviceOne.setId(1L);
		serviceOne.setIndividualprovider_id(1L);
		serviceOne.setName("ServiceOne");
		serviceOne.setRating_id(5L);
		serviceOne.setService_id(1L);
		
		resultObjects[0] = BigInteger.valueOf(serviceOne.getService_id());
		resultObjects[1] = serviceOne.getName();
		resultObjects[2] = BigInteger.valueOf(serviceOne.getRating_id());
		resultObjects[3] = BigInteger.valueOf(serviceOne.getId());
		resultObjects[4] = BigInteger.valueOf(serviceOne.getIndividualprovider_id());
		feedbacks.add(resultObjects);
		
		when(individualProviderFeedbackServices.findIps(1L)).thenReturn(feedbacks);
		
		// Act
		List<IndividualProviderFeedbackServiceDto> result = sut.findServicesByIndividualProviderId(1L);

		// Assert
		assertEquals(feedbacks.size(), result.size());
	}

	@Test
	public void testSaveIndividualProviderFeedbackServices() {
		// Arrange
		IndividualProviderFeedbackServiceDto individualProviderFeedbackServiceDto = mock(IndividualProviderFeedbackServiceDto.class);
		List<IndividualProviderFeedbackServices> feedbackServices = new ArrayList<IndividualProviderFeedbackServices>();
		
		when(createIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper.createAndAssignServices(individualProviderFeedbackServiceDto))
				.thenReturn(feedbackServices);

		// Act
		sut.saveIndividualProviderFeedbackServices(individualProviderFeedbackServiceDto);

		// Assert
		verify(feedbackServicesRepository, times(1)).save(feedbackServices);
	}

	@Test
	public void testUpdateIndividualProviderFeedbackServices() {
		// Arrange
		IndividualProviderFeedbackServiceDto individualProviderFeedbackServiceDto = mock(IndividualProviderFeedbackServiceDto.class);
		IndividualProviderFeedbackServices feedbackService = new IndividualProviderFeedbackServices();
		
		when(individualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper.map(individualProviderFeedbackServiceDto))
				.thenReturn(feedbackService);

		// Act
		sut.updateIndividualProviderService(individualProviderFeedbackServiceDto);

		// Assert
		verify(feedbackServicesRepository, times(1)).save(feedbackService);
	}

}
