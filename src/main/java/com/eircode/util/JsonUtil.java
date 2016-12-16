package com.eircode.util;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.File;


public final class JsonUtil {
	
	private JsonUtil() {
		
	}

	public static <T> T loadJsonFromFile(final File file, final Class<T> valueType) throws Exception {		
		return new ObjectMapper().readValue(file,valueType);
	}
	
	public static <T> T loadJsonFromString(final String jsonString, final Class<T> valueType) throws Exception {		
		return new ObjectMapper().readValue(jsonString,valueType);
	}
	
}
