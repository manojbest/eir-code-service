package com.eircode.proxy;

public interface EirProxy {
	
	String findAddress(String addressFragment);
	
	String findAddressWithGeoInfo(String addressFragment);
	
	String findCoordinatesForAddress(String addressFragment);
	
	String findAddressFromCoordinates(String latitude, String longitude, String distance);

}
