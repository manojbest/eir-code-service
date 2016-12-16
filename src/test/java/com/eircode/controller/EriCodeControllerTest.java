package com.eircode.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eircode.AbstractControllerTest;
import com.eircode.controller.EriCodeController;
import com.eircode.model.Address;
import com.eircode.service.EriCodeServiceImpl;

public class EriCodeControllerTest extends AbstractControllerTest {
	
	@Mock
	private EriCodeServiceImpl eriCodeService;
	
	@InjectMocks
	private EriCodeController eriCodeController;
	
	@Before
	public void setUp() {
		   // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        // Prepare the Spring MVC Mock components for standalone testing
        setUp(eriCodeController);
	}
	
	@Test
	public void testEriCodes() throws Exception {
		
		final List<Address> addressList = new ArrayList<>();
		final Address address = new Address();
		address.setAddressline1("test");
		
		when(eriCodeService.retrieveAddress("12")).thenReturn(addressList);
		
	       // Perform the behavior being tested
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/postcoder/address/12").accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(eriCodeService, times(1)).retrieveAddress("12");

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		
	}

}
