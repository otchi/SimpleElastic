package com.edifixio.simplElastic.configFactory;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class ElementConfigFactoryState implements ElementConfigFactory  {
	
	private ElementConfigFactory  jecf;
	private Boolean isRequired=true;
/*********************************************************************************************/	
	public ElementConfigFactoryState (ElementConfigFactory jecf, Boolean isRequired) {
		super();
		this.jecf = jecf;
		this.isRequired = isRequired;
	}
/*********************************************************************************************/
	public ElementConfigFactoryState (ElementConfigFactory jecf) {
		super();
		this.jecf = jecf;
		
	}

	public ElementConfigFactory getJecf() {
		return jecf;
	}

	public void setJecf(ElementConfigFactory jecf) {
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
