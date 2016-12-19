package com.eircode.util;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.eircode.exception.InvalidJSONException;


public final class JsonUtil {
	
	private JsonUtil() {
		
	}

	public static <T> T loadJsonFromFile(final File file, final Class<T> valueType) throws InvalidJSONException {
		T t = null;
		try {
			t = new ObjectMapper().readValue(file,valueType);
		} catch (IOException ex) {
			throw new InvalidJSONException("Could not parse JSON content", ex);
		}
		return t;
	}
	
	public static <T> T loadJsonFromString(final String jsonString, final Class<T> valueType) throws InvalidJSONException {		
		T t = null;
		try {
			t = new ObjectMapper().readValue(jsonString,valueType);
		} catch (IOException ex) {
			throw new InvalidJSONException("Could not parse JSON content", ex);
		}
		return t;
	}
}
