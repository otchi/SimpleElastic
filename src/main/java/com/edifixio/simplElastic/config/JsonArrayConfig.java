package com.edifixio.simplElastic.config;

import java.util.LinkedList;
import java.util.List;

public abstract class JsonArrayConfig implements JsonElementConfig {
	
	
	protected List<JsonElementConfig> jsonElementConfigs;
	
	
	public JsonArrayConfig(){
		jsonElementConfigs=new LinkedList<JsonElementConfig>();
	}
		
	/****************************************************************/	
	public  Boolean isPremitiveConfig(){
		return false;
	}
	public  Boolean isArrayConfig(){
		return true;
	}
	public  Boolean isObjectConfig(){
		return false;
	}
	/******************************************************************/
	/*********************************************************************************************/	
	public void addJsonElementConfig(JsonElementConfig jsonElementConfig){
		this.jsonElementConfigs.add(jsonElementConfig);
	}
	/**********************************************************************************************/


	public List<JsonElementConfig> getJsonElementConfigs() {
		return jsonElementConfigs;
	}


	
}
