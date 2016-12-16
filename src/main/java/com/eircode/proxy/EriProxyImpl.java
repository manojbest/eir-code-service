package com.eircode.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;


@Repository
public class EriProxyImpl implements EriProxy {
	
	private static final Logger log = LoggerFactory.getLogger(EriProxyImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${eri.code.service.lookup.uri}")
	private String lookupUri;
	
	@Value("${eri.code.service.apikey}")	
	private String apiKey;

	@Override
	public String findAddress(final String addressFragment) {
		
		log.info("Requesting address details form postcoder web service ");
		final String addressLookupUri = lookupUri + apiKey + "/address/ie/" + addressFragment + "?lines=3&format=json";
		
		ResponseEntity<String> result = restTemplate.getForEntity(addressLookupUri, String.class);
		
		return result.getBody();

	}

}
