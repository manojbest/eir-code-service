package com.eircode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eircode.exception.ValidationException;
import com.eircode.model.Address;
import com.eircode.service.EriCodeServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/postcoder")
@Api(value = "Eri Code Service", produces = MediaType.APPLICATION_JSON_VALUE)
public class EriCodeController {
	
	@Autowired
	private EriCodeServiceImpl eriCodeService;
	
	@RequestMapping(value = "/address/{eircode-or-address-fragment}", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "eircode-or-address-fragment", value = "eircode or address to be set", required = false, dataType = "string", paramType = "query")})
	@ApiOperation(value = "Get Address Details", response = Address.class)
	@ResponseBody
	public ResponseEntity<List<Address>> lookupAddress(@PathVariable("eircode-or-address-fragment") final String inputValue) {
		if (inputValue.equalsIgnoreCase("12")){
			throw new ValidationException("No Address defined");
		}
		
		final List<Address> adderss = eriCodeService.retrieveAddress(inputValue);
		return new ResponseEntity<List<Address>>(adderss, HttpStatus.OK);
	}
}
