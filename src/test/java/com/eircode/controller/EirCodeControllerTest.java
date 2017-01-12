package com.eircode.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eircode.AbstractControllerTest;
import com.eircode.model.Address;
import com.eircode.service.EirCodeServiceImpl;
import com.eircode.util.JsonUtil;

public class EirCodeControllerTest extends AbstractControllerTest {
	
	@Mock
	private EirCodeServiceImpl eriCodeService;
	
	@InjectMocks
	private EirCodeController eriCodeController;
	
	@Value("${mock.address.lookup.path}")
	private String mockAddressLookupPath;
	
	@Before
	public void setUp() {
		   // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        // Prepare the Spring MVC Mock components for standalone testing
        setUp(eriCodeController);
	}
	
	@Test
	public void testEriCodes() throws Exception {
		
		final List<Address> mockAddressList = Arrays.asList(JsonUtil.loadJsonFromFile(
				new File(getClass().getClassLoader().getResource(mockAddressLookupPath).getFile()),
				Address[].class));
		
		when(eriCodeService.retrieveAddress("D02X285")).thenReturn(mockAddressList);
		
	       // Perform the behavior being tested
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/eir/postcoder/address/D02X285").accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();
        
        final List<Address> serviceAddressList = Arrays.asList(JsonUtil.loadJsonFromString(content, Address[].class));

        // Verify the eriCodeService.retrieveAddress method was invoked once
        verify(eriCodeService, times(1)).retrieveAddress("D02X285");

       // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		
	}

}
