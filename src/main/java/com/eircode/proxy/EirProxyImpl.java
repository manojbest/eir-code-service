package com.eircode.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;


@Repository
public class EirProxyImpl implements EirProxy {
	
	private static final Logger log = LoggerFactory.getLogger(EirProxyImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${eir.code.service.address.lookup.uri}")
	private String addressLookupUri;
	
	@Value("${eir.code.service.geo.address.lookup.uri}")
	private String geoAddressLookupUri;
	
	@Value("${eir.code.service.coordinate.lookup.uri}")
	private String coordinateLookupUri;
	
	@Value("${eir.code.service.reverse.address.lookup.uri}")
	private String reverseAddressLookupUri;
	
	private String getRemoteServiceResults(final String url) {
		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
		return result.getBody();
	}

	@Override
	public String findAddress(final String addressFragment) {
		log.info("Requesting address details from postcoder web service ...");		
		return getRemoteServiceResults(addressLookupUri.replace("{address-fragment}", addressFragment));
	}

	@Override
	public String findAddressWithGeoInfo(final String addressFragment) {
		log.info("Requesting address details with Geo info from postcoder web service ...");		
		return getRemoteServiceResults(geoAddressLookupUri.replace("{address-fragment}", addressFragment));
	}

	@Override
	public String findCoordinatesForAddress(final String addressFragment) {
		log.info("Requesting coordinates for address from postcoder web service ...");		
		return getRemoteServiceResults(geoAddressLookupUri.replace("{address-fragment}", addressFragment));
	}

	@Override
	public String findAddressFromCoordinates(final String latitude, final String longitude, final String distance) {
		log.info("Requesting address for coordinatesfrom postcoder web service ...");		
		return getRemoteServiceResults(geoAddressLookupUri.replace("{latitude}", latitude)
				.replace("{latitude}", longitude).replace("{distance}", distance));
	}

}
