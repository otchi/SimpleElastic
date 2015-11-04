package com.edifixio.simplElastic.configFactory;

public abstract class CompoundConfigFactory implements ElementConfigFactory{

	protected PrimitiveConfigFactory jpcf;
	
	
/*********************************************************************************************/	
	public CompoundConfigFactory(PrimitiveConfigFactory jpcf) {
		this.jpcf=jpcf;
	}
/*********************************************************************************************/	
	public CompoundConfigFactory() {}
	public boolean isPremitive(){
		if(jpcf!=null) return true;
		return false;
	}

}
