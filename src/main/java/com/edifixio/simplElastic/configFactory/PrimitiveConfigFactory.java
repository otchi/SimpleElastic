package com.edifixio.simplElastic.configFactory;

import com.edifixio.simplElastic.config.JsonBooleanConfig;
import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.config.JsonStringConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class PrimitiveConfigFactory implements ElementConfigFactory{
	 
	private Class<? extends JsonStringConfig> stringConfig;
	private Class<? extends JsonBooleanConfig> booleanConfig;
	/**********************************************************************************/
	public Class<? extends JsonStringConfig> getStringConfig() {
		return stringConfig;
	}
	
	/************************************************************************************/
	public void setStringConfig(Class<? extends JsonStringConfig> stringConfig) {
		this.stringConfig = stringConfig;
	}
	
	public PrimitiveConfigFactory setStringConfigAndReturn(Class<? extends JsonStringConfig> stringConfig){
		this.stringConfig=stringConfig;
		return this;
	}
	public PrimitiveConfigFactory setBooleanConfigAndReturn(Class<? extends JsonBooleanConfig> booleanConfig){
		this.booleanConfig=booleanConfig;
		return this;
	}
	
	public Class<? extends JsonBooleanConfig> getBooleanConfig() {
		return booleanConfig;
	}

	public void setBooleanConfig(Class<? extends JsonBooleanConfig> booleanConfig) {
		this.booleanConfig = booleanConfig;
	}

	/*********************************************************************************************/
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException, QuickElasticException {
		// TODO Auto-generated method stub
		if(!jsonElement.isJsonPrimitive())
			throw new QuickElasticException("this json element is not a primary key");
		
		JsonPrimitive jp=jsonElement.getAsJsonPrimitive();
		int index=(Integer) (
					(jp.isString())? 1:
						(jp.isBoolean())?2:-1);
			
		switch (index) {
			case 1:
				 	Class<? extends JsonStringConfig> sjsc=stringConfig;
				 	if(sjsc==null) 
				 		throw new QuickElasticException("this json element :"+jsonElement+" can't  be a string");
				 	return sjsc.getConstructor(String.class).newInstance(jp.getAsString());
			case 2:
			 	Class<? extends JsonBooleanConfig> bjsc=booleanConfig;
			 	if(bjsc==null) 
			 		throw new QuickElasticException("this json element : "+jsonElement+" can't  be a boolean");
			 	return bjsc.getConstructor(Boolean.class).newInstance(jp.getAsBoolean());
			
			default:
				throw new QuickElasticException(""+jsonElement+" :this version not allow to process not string or boolean conf");
		}
				
	}

	public ElementConfigFactory duplicate() {
		// TODO Auto-generated method stub
		return this;
	}
		
	

}


