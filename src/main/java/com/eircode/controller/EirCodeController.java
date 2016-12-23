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

import com.eircode.exception.BusinessException;
import com.eircode.exception.ValidationException;
import com.eircode.model.Address;
import com.eircode.model.Coordinate;
import com.eircode.model.GeoAddress;
import com.eircode.service.EirCodeServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/eir/postcoder")
@Api(value = "Eri Code Service", produces = MediaType.APPLICATION_JSON_VALUE)
public class EirCodeController {

	@Autowired
	private EirCodeServiceImpl eriCodeService;

	@RequestMapping(value = "/address/{eircode-or-address-fragment}", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "eircode-or-address-fragment", value = "eircode or address to be set", required = false, dataType = "string", paramType = "query") })
	@ApiOperation(value = "Get Address Details", response = Address.class)
	@ResponseBody
	public ResponseEntity<List<Address>> handleAddressLookup(
			@PathVariable("eircode-or-address-fragment") final String inputValue) throws BusinessException {
		if ("".equalsIgnoreCase(inputValue)) {
			throw new ValidationException("No Address or Eir Code defined");
		}

		final List<Address> adderssList = eriCodeService.retrieveAddress(inputValue);
		return new ResponseEntity<List<Address>>(adderssList, HttpStatus.OK);
	}

	@RequestMapping(value = "/addressgeo/{eircode-or-address-fragment}", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "eircode-or-address-fragment", value = "eircode or address to be set", required = false, dataType = "string", paramType = "query") })
	@ApiOperation(value = "Get Address Details with Geo Info", response = GeoAddress.class)
	@ResponseBody
	public ResponseEntity<List<GeoAddress>> handleGeoAddressLookup(
			@PathVariable("eircode-or-address-fragment") final String inputValue) throws BusinessException {
		if ("".equalsIgnoreCase(inputValue)) {
			throw new ValidationException("No Address or Eir Code defined");
		}
		final List<GeoAddress> geoAdderssList = eriCodeService.retrieveAddressWithGeoIfo(inputValue);
		return new ResponseEntity<List<GeoAddress>>(geoAdderssList, HttpStatus.OK);
	}

	@RequestMapping(value = "/position/{eircode-or-address-fragment}", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "eircode-or-address-fragment", value = "eircode or address to be set", required = false, dataType = "string", paramType = "query") })
	@ApiOperation(value = "Get Coordinate for address", response = Coordinate.class)
	@ResponseBody
	public ResponseEntity<List<Coordinate>> handleCoordinateLookup(
			@PathVariable("eircode-or-address-fragment") final String inputValue) throws BusinessException {
		if ("".equalsIgnoreCase(inputValue)) {
			throw new ValidationException("No Address or Eir Code defined");
		}
		final List<Coordinate> coordinateList = eriCodeService.retrieveCoordinatesForAddress(inputValue);
		return new ResponseEntity<List<Coordinate>>(coordinateList, HttpStatus.OK);
	}

	@RequestMapping(value = "/rgeoaddress/{latitude}/{longitude}/{distance}", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "latitude", value = "ETRS89 Latitude Co-ordinate", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "longitude", value = "ETRS89 Longitude Co-ordinate", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "distance", value = "distance to be set", required = false, dataType = "string", paramType = "query") })
	@ApiOperation(value = "Get Address Details from coordinates", response = Address.class)
	@ResponseBody
	public ResponseEntity<List<Address>> handleReverseAddressLookup(@PathVariable("latitude") final String latitude,
			@PathVariable("longitude") final String longitude, @PathVariable("distance") final String distance) throws BusinessException {
		final List<Address> addressList = eriCodeService.retrieveAddressFromCoordinates(latitude, longitude, distance);
		if ("".equalsIgnoreCase(latitude) || "".equalsIgnoreCase(longitude) || "".equalsIgnoreCase(distance)) {
			throw new ValidationException("No Latitude,Longitude and Distance defined");
		}
		return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
	}

}
