package com.edifixio.simplElastic.config;

import java.util.Map;

public abstract class JsonObjectConfig implements JsonElementConfig {
	
	protected  Map<String, JsonElementConfig> mapConfig;

	/*****************************************************************************/
	public JsonObjectConfig(Map<String, JsonElementConfig> mapConfig) {
	
		this.mapConfig=mapConfig;
	}

	/****************************************************************/	
	public  Boolean isPremitiveConfig(){
		return false;
	}
	public  Boolean isArrayConfig(){
		return false;
	}
	public  Boolean isObjectConfig(){
		return true;
	}
	/******************************************************************/

	public Map<String, JsonElementConfig> getMapConfig() {
		return mapConfig;
	}
	

	
	

}
