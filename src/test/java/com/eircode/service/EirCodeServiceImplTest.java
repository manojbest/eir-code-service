package com.eircode.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import com.eircode.exception.BusinessException;
import com.eircode.model.Address;
import com.eircode.proxy.EirProxyImpl;
import com.eircode.repository.CacheRepository;
import com.eircode.util.HashGeneratorUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HashGeneratorUtil.class)
public class EirCodeServiceImplTest {

	@Mock
	private EirProxyImpl eriProxy;
	
	@Mock
	private CacheRepository cacheRepository;
	
	@InjectMocks
	@Autowired
	private EirCodeServiceImpl eirCodeService;
	
	@Value("${mock.address.lookup.path}")
	private String mockAddressLookupPath;
	
	@Before
	public void setUp() {
		   // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HashGeneratorUtil.class);
	}
	
	@Test
	public void testRetrieveAddressFromService() throws Exception {
		
		ReflectionTestUtils.setField(eirCodeService, "isProduction", true);
		Mockito.when(HashGeneratorUtil.hashString("addressD02X285")).thenReturn("ahgdf12222");
		String addressDetails = "[\r\n\t{\r\n\t    \"addressline1\": \"Dept of Communications, Climate Change and Natural Resources\",\r\n\t    \"addressline2\": \"29-31 Adelaide Road\",\r\n\t    \"summaryline\": \"Dept of Communications, Climate Change and Natural Resources, 29-31 Adelaide Road, Dublin 2, D02 X285\",\r\n\t    \"organisation\": \"Dept of Communications, Climate Change and Natural Resources\",\r\n\t    \"street\": \"Adelaide Road\",\r\n\t    \"posttown\": \"Dublin 2\",\r\n\t    \"county\": \"Dublin\",\r\n\t    \"postcode\": \"D02 X285\"\r\n\t }\r\n ]";
		when(eriProxy.findAddress("D02X285")).thenReturn(addressDetails);
		
		List<Address> serviceAddressList = eirCodeService.retrieveAddress("D02X285");
		
		verify(eriProxy, times(1)).findAddress("D02X285");
		verify(cacheRepository, times(1)).putData("ahgdf12222", addressDetails);
		PowerMockito.verifyStatic();
		Assert.assertTrue("failure - expected service should return address list", serviceAddressList.size() > 0);
	}
	
	@Test
	public void testRetrieveAddressFromCache() throws Exception {
		
		ReflectionTestUtils.setField(eirCodeService, "isProduction", true);
		Mockito.when(HashGeneratorUtil.hashString("addressD02X285")).thenReturn("ahgdf12222");
		
		String addressDetails = "[\r\n\t{\r\n\t    \"addressline1\": \"Dept of Communications, Climate Change and Natural Resources\",\r\n\t    \"addressline2\": \"29-31 Adelaide Road\",\r\n\t    \"summaryline\": \"Dept of Communications, Climate Change and Natural Resources, 29-31 Adelaide Road, Dublin 2, D02 X285\",\r\n\t    \"organisation\": \"Dept of Communications, Climate Change and Natural Resources\",\r\n\t    \"street\": \"Adelaide Road\",\r\n\t    \"posttown\": \"Dublin 2\",\r\n\t    \"county\": \"Dublin\",\r\n\t    \"postcode\": \"D02 X285\"\r\n\t }\r\n ]";
		when(cacheRepository.getData("ahgdf12222")).thenReturn(addressDetails);
		
		List<Address> serviceAddressList = eirCodeService.retrieveAddress("D02X285");
		
		verify(cacheRepository, times(1)).getData("ahgdf12222");
		PowerMockito.verifyStatic();
		Assert.assertTrue("failure - expected service should return address list", serviceAddressList.size() > 0);
	}
	
	@Test
	public void testRetrieveAddressInLocal() throws Exception {
		
		ReflectionTestUtils.setField(eirCodeService, "isProduction", false);
		ReflectionTestUtils.setField(eirCodeService, "mockAddressLookupPath", "mock-responses/address-lookup.json");
		
		List<Address> serviceAddressList = eirCodeService.retrieveAddress("D02X285");
	
		Assert.assertTrue("failure - expected service should return address list", serviceAddressList.size() > 0);
	}
	
	@Test(expected=BusinessException.class)
	public void testRetrieveAddressException() throws Exception {
		
		ReflectionTestUtils.setField(eirCodeService, "isProduction", true);
		Mockito.when(HashGeneratorUtil.hashString("addressD02X285")).thenReturn("ahgdf12222");
		
		String addressDetails = "[\r\n\t\r\n\t    \"addressline1\": \"Dept of Communications, Climate Change and Natural Resources\",\r\n\t    \"addressline2\": \"29-31 Adelaide Road\",\r\n\t    \"summaryline\": \"Dept of Communications, Climate Change and Natural Resources, 29-31 Adelaide Road, Dublin 2, D02 X285\",\r\n\t    \"organisation\": \"Dept of Communications, Climate Change and Natural Resources\",\r\n\t    \"street\": \"Adelaide Road\",\r\n\t    \"posttown\": \"Dublin 2\",\r\n\t    \"county\": \"Dublin\",\r\n\t    \"postcode\": \"D02 X285\"\r\n\t }\r\n ]";
		when(cacheRepository.getData("ahgdf12222")).thenReturn(addressDetails);
		eirCodeService.retrieveAddress("D02X285");
	}
}
