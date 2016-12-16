package com.eircode.repository;

public interface CacheRepository {

	void putData(String key, String jsonString);
	
	String getData(String key);
}
