package com.edifixio.simplElastic.config;

public abstract  class  JsonStringConfig implements JsonPrimitiveConfig, JsonElementConfig{
	
	protected String value;
	
	/*********************************************************************/
	public JsonStringConfig(String value) {
		super();
		this.value = value;
	}
	/********************************************************************/
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/****************************************************************/	
	public  Boolean isPremitiveConfig(){
		return true;
	}
	public  Boolean isArrayConfig(){
		return false;
	}
	public  Boolean isObjectConfig(){
		return false;
	}
	/******************************************************************/
	
	public  Boolean isStringConfig(){
		return true;
	}
	public Boolean isBooleanConfig() {
		// TODO Auto-generated method stub
		return null;
	}








	
	

}
