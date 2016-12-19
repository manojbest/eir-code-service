package com.eircode.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eircode.exception.BusinessException;
import com.eircode.exception.HashGenerationException;
import com.eircode.exception.InvalidJSONException;
import com.eircode.model.Address;
import com.eircode.model.Coordinate;
import com.eircode.model.GeoAddress;
import com.eircode.proxy.EirProxyImpl;
import com.eircode.repository.CacheRepository;
import com.eircode.util.HashGeneratorUtil;
import com.eircode.util.JsonUtil;

@Service
public class EirCodeServiceImpl implements EirCodeService {
	
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EirProxyImpl eriProxy;
	
	@Autowired
	private CacheRepository cacheRepository;
	
	@Value("${mock.address.lookup.path}")
	private String mockAddressLookupPath;
	
	@Value("${mock.geo.address.lookup.path}")
	private String mockGeoAddressLookupPath;
	
	@Value("${mock.coordinate.lookup.path}")
	private String mockCoordinateLookupPath;
	
	@Value("${mock.reverse.address.lookup.path}")
	private String mockReverseAddressLookupPath;
	
	@Value("${spring.application.isProd}")
	private boolean isProduction;
	
	@Override
	public List<Address> retrieveAddress(final String addressFragment) throws BusinessException {
		log.info("Retrieving address details for ::  " + addressFragment);
	
		List<Address> addressList = null;
		try {
			if (isProduction) {
				final String cacheKey = HashGeneratorUtil.hashString("address" + addressFragment);
				String addressDetails = cacheRepository.getData(cacheKey);
				if (addressDetails != null) {
					log.debug("Address details found in cache for the key ::  " + cacheKey);
					addressList = Arrays.asList(JsonUtil.loadJsonFromString(addressDetails, Address[].class));
				} else {
					log.debug("Address details not found in cache for the key ::  " + addressFragment);
					log.debug("Requesting address details from web service");
					addressDetails = eriProxy.findAddress(addressFragment);
					addressList = Arrays.asList(JsonUtil.loadJsonFromString(addressDetails, Address[].class));
					log.info("Storing address details in cache for the key :: " + cacheKey);
					cacheRepository.putData(cacheKey, addressDetails);
				}
			} else {
				log.debug("Requesting address details from mock service");
				addressList = Arrays.asList(JsonUtil.loadJsonFromFile(
						new File(getClass().getClassLoader().getResource(mockAddressLookupPath).getFile()),
						Address[].class));
			}
		} catch(HashGenerationException | InvalidJSONException ex) {
			throw new BusinessException("Retrieving address failed ", ex);
		}
		return addressList;
	}

	@Override
	public List<GeoAddress> retrieveAddressWithGeoIfo(final String addressFragment) throws BusinessException {
		log.info("Retrieving Geo address details for ::  " + addressFragment);
		
		List<GeoAddress> geoAddressList = null;
		try {
			if (isProduction) {
				final String cacheKey = HashGeneratorUtil.hashString("geoaddress" + addressFragment);
				String geoAddressDetails = cacheRepository.getData(cacheKey);
				if (geoAddressDetails != null) {
					log.debug("Geo Address details found in cache for the key ::  " + cacheKey);
					geoAddressList = Arrays.asList(JsonUtil.loadJsonFromString(geoAddressDetails, GeoAddress[].class));
				} else {
					log.debug("Geo Address details not found in cache for the key ::  " + addressFragment);
					log.debug("Requesting Geo address details from web service");
					geoAddressDetails = eriProxy.findAddressWithGeoInfo(addressFragment);
					geoAddressList = Arrays.asList(JsonUtil.loadJsonFromString(geoAddressDetails, GeoAddress[].class));
					log.debug("Storing Geo address details in cache for the key :: " + cacheKey);
					cacheRepository.putData(cacheKey, geoAddressDetails);
				}
			} else {
				log.debug("Requesting Geo address details from mock service");
				geoAddressList = Arrays.asList(JsonUtil.loadJsonFromFile(
						new File(getClass().getClassLoader().getResource(mockGeoAddressLookupPath).getFile()),
						GeoAddress[].class));
			}
		} catch(HashGenerationException | InvalidJSONException ex) {
			throw new BusinessException("Retrieving geo address failed ", ex);
		}
		return geoAddressList;
	}

	@Override
	public List<Coordinate> retrieveCoordinatesForAddress(final String addressFragment) throws BusinessException {
		log.info("Coordinate details for ::  " + addressFragment);
		
		List<Coordinate> coordinateList = null;
		try {
			if (isProduction) {
				final String cacheKey = HashGeneratorUtil.hashString("coordinate" + addressFragment);
				String coordinateDetails = cacheRepository.getData(cacheKey);
				if (coordinateDetails != null) {
					log.debug("Coordinate details found in cache for the key ::  " + cacheKey);
					coordinateList = Arrays.asList(JsonUtil.loadJsonFromString(coordinateDetails, Coordinate[].class));
				} else {
					log.debug("Coordinate details not found in cache for the key ::  " + cacheKey);
					log.debug("Requesting Coordinate details from web service");
					coordinateDetails = eriProxy.findCoordinatesForAddress(addressFragment);
					coordinateList = Arrays.asList(JsonUtil.loadJsonFromString(coordinateDetails, Coordinate[].class));
					log.debug("Storing Coordinate details in cache for the key :: " + cacheKey);
					cacheRepository.putData(cacheKey, coordinateDetails);
				}
			} else {
				log.debug("Requesting Coordinate details from mock service");
				coordinateList = Arrays.asList(JsonUtil.loadJsonFromFile(
						new File(getClass().getClassLoader().getResource(mockCoordinateLookupPath).getFile()),
						Coordinate[].class));
			}
		} catch(HashGenerationException | InvalidJSONException ex) {
			throw new BusinessException("Retrieving coordinate failed ", ex);
		}
		return coordinateList;
	}

	@Override
	public List<Address> retrieveAddressFromCoordinates(final String latitude, final String longitude, final String distance) throws BusinessException {
		log.info("Address details for ::  Latitude : " + latitude + " , Longitude : " + longitude + " , Distance : " + distance);
		
		List<Address> addressList = null;
		try {
			if (isProduction) {
				final String cacheKey = HashGeneratorUtil.hashString("raddress" + latitude + longitude + distance);
				String coordinateDetails = cacheRepository.getData(cacheKey);
				if (coordinateDetails != null) {
					log.debug("Address details found in cache for the key ::  " + cacheKey);
					addressList = Arrays.asList(JsonUtil.loadJsonFromString(coordinateDetails, Address[].class));
				} else {
					log.debug("Address details not found in cache for the key ::  " + cacheKey);
					log.debug("Requesting Address details from web service");
					coordinateDetails = eriProxy.findAddressFromCoordinates(latitude, longitude, distance);
					addressList = Arrays.asList(JsonUtil.loadJsonFromString(coordinateDetails, Address[].class));
					log.debug("Storing Coordinate details in cache for the key :: " + cacheKey);
					cacheRepository.putData(cacheKey, coordinateDetails);
				}
			} else {
				log.debug("Requesting Address details from mock service");
				addressList = Arrays.asList(JsonUtil.loadJsonFromFile(
						new File(getClass().getClassLoader().getResource(mockReverseAddressLookupPath).getFile()),
						Address[].class));
			}
		} catch(HashGenerationException | InvalidJSONException ex) {
			throw new BusinessException("Retrieving address from coordinate failed ", ex);
		}
		return addressList;
	}
}
