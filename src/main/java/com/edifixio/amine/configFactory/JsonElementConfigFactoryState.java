package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class JsonElementConfigFactoryState implements JsonElementConfigFactory  {
	
	private JsonElementConfigFactory  jecf;
	private Boolean isRequired=true;
/*********************************************************************************************/	
	public JsonElementConfigFactoryState (JsonElementConfigFactory jecf, Boolean isRequired) {
		super();
		this.jecf = jecf;
		this.isRequired = isRequired;
	}
/*********************************************************************************************/
	public JsonElementConfigFactoryState (JsonElementConfigFactory jecf) {
		super();
		this.jecf = jecf;
		
	}

	public JsonElementConfigFactory getJecf() {
		return jecf;
	}

	public void setJecf(JsonElementConfigFactory jecf) {
		this.jecf = jecf;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	


	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException, QuickElasticException {
		// TODO Auto-generated method stub
		return this.jecf.getJsonElementConfig(jsonElement);
	}
	/*public JsonElementConfigFactory duplicate() {
		// TODO Auto-generated method stub
		return new JsonElementConfigFactoryState(jecf.duplicate(), isPut.booleanValue(), isRequired.booleanValue());
	}*/
}
