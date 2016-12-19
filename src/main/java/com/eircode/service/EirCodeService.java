package com.eircode.service;

import java.util.List;

import com.eircode.exception.BusinessException;
import com.eircode.model.Address;
import com.eircode.model.Coordinate;
import com.eircode.model.GeoAddress;

public interface EirCodeService {
	
	List<Address> retrieveAddress(String addressFragment) throws BusinessException;
	
	List<GeoAddress> retrieveAddressWithGeoIfo(String addressFragment) throws BusinessException;
	
	List<Coordinate> retrieveCoordinatesForAddress(String addressFragment) throws BusinessException;
	
	List<Address> retrieveAddressFromCoordinates(String latitude, String longitude, String distance) throws BusinessException;

}
