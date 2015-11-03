package com.edifixio.simplElastic.application;

import java.util.Map;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.config.JsonStringConfig;
import com.google.gson.JsonObject;

public class SimpleRequestConfig extends JsonObjectConfig {
	private static final String CLASS = "class";
	private static final String MAPPING = "mapping";

	/***********************************************************************************/
	public SimpleRequestConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	/*********************************************************************************/
	public void process(Object object, JsonObject query) throws ReflectiveOperationException {
		String str =((JsonStringConfig) mapConfig.get(CLASS)).getValue();
		if (object.getClass().equals(Class.forName(str)) ){
			JsonElementConfig jec = mapConfig.get(MAPPING);
			((SimpleRequestMappingConfig) jec).process(query, object);
		} else {
			System.out.println("exception SimpleRequestConfig ~ 25");
			return;
		}
	}

	@Override
	public String toString() {
		return "SimpleRequestConfig [mapConfig=" + mapConfig + "]";
	}
	
	

}
