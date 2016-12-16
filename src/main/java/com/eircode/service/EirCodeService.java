package com.eircode.service;

import java.util.List;

import com.eircode.model.Address;

public interface EirCodeService {
	
	List<Address> retrieveAddress(String addressFragment);

}
