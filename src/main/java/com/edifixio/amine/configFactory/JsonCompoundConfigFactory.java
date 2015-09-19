package com.edifixio.amine.configFactory;

public abstract class JsonCompoundConfigFactory implements JsonElementConfigFactory{

	protected JsonPrimitiveConfigFactory jpcf;
	
	
/*********************************************************************************************/	
	public JsonCompoundConfigFactory(JsonPrimitiveConfigFactory jpcf) {
		this.jpcf=jpcf;
	}
/*********************************************************************************************/	
	public JsonCompoundConfigFactory() {}
	public boolean isPremitive(){
		if(jpcf!=null) return true;
		return false;
	}

}
