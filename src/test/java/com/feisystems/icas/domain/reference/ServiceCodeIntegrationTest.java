package com.feisystems.icas.domain.reference;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ServiceCode;
import com.feisystems.icas.domain.reference.ServiceCodeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
@Configurable
public class ServiceCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ServiceCodeDataOnDemand dod;

	@Autowired
    ServiceCodeRepository serviceCodeRepository;

	@Test
    public void testCountAllServiceCodes() {
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", dod.getRandomServiceCode());
        long count = serviceCodeRepository.count();
        Assert.assertTrue("Counter for 'ServiceCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindServiceCode() {
        ServiceCode obj = dod.getRandomServiceCode();
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to provide an identifier", id);
        obj = serviceCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ServiceCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ServiceCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllServiceCodes() {
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", dod.getRandomServiceCode());
        long count = serviceCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ServiceCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ServiceCode> result = serviceCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ServiceCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ServiceCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindServiceCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", dod.getRandomServiceCode());
        long count = serviceCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ServiceCode> result = serviceCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ServiceCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ServiceCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ServiceCode obj = dod.getRandomServiceCode();
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to provide an identifier", id);
        obj = serviceCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ServiceCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyServiceCode(obj);
        Integer currentVersion = obj.getVersion();
        serviceCodeRepository.flush();
        Assert.assertTrue("Version for 'ServiceCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateServiceCodeUpdate() {
		ServiceCode obj = dod.getRandomServiceCode();
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to provide an identifier", id);
        obj = serviceCodeRepository.findOne(id);
        boolean modified =  dod.modifyServiceCode(obj);
        Integer currentVersion = obj.getVersion();
        ServiceCode merged = (ServiceCode)serviceCodeRepository.save(obj);
        serviceCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ServiceCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveServiceCode() {
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to initialize correctly", dod.getRandomServiceCode());
        ServiceCode obj = dod.getNewTransientServiceCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ServiceCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ServiceCode' identifier to be null", obj.getId());
        serviceCodeRepository.save(obj);
        serviceCodeRepository.flush();
        Assert.assertNotNull("Expected 'ServiceCode' identifier to no longer be null", obj.getId());
    }

}
