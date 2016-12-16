package com.eircode.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eircode.model.Address;
import com.eircode.proxy.EirProxyImpl;
import com.eircode.repository.CacheRepository;
import com.eircode.util.JsonUtil;

@Service
public class EirCodeServiceImpl implements EirCodeService {
	
    private static final Logger log = LoggerFactory.getLogger(EirCodeServiceImpl.class);
	
	@Autowired
	private EirProxyImpl eriProxy;
	
	@Autowired
	private CacheRepository cacheRepository;
	
	@Value("${mock.address.lookup.path}")
	private String mockAddressLookupPath;
	
	@Value("${spring.application.isProd}")
	private boolean isProduction;
	
	@Override
	public List<Address> retrieveAddress(final String addressFragment) {
		log.info("Retrieving address details for ::  " + addressFragment);
		
		List<Address> addressList = null;
		String addressDetails = cacheRepository.getData(addressFragment);
		
		try {
			if (addressDetails != null) {
				log.debug("Address details found in cache for the key ::  " + addressFragment);
				addressList = Arrays.asList(JsonUtil.loadJsonFromString(addressDetails, Address[].class));
			} else {
				log.debug("Address details not found in cache for the key ::  " + addressFragment);
				if(isProduction) {
					log.debug("Requesting address details from web service");
					addressDetails = eriProxy.findAddress(addressFragment);
					addressList = Arrays.asList(JsonUtil.loadJsonFromString(addressDetails, Address[].class));
				} else {
					log.debug("Requesting address details from mock service");
					addressList = Arrays.asList(JsonUtil.loadJsonFromFile(
							new File(getClass().getClassLoader().getResource(mockAddressLookupPath).getFile()),
							Address[].class));
				}
				log.debug("Storing address details in cache for the key :: " + addressFragment);
				cacheRepository.putData(addressFragment, addressDetails);
			}
		} catch(Exception e) {
			log.error("Service Address Exception: " + e);
		}
		
		return addressList;
	}
}
