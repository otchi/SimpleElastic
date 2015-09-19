package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;

public class SimpleTypeIndexConfig extends JsonArrayConfig {
	/***********************************************************************************************/
	public List<String> getStringListConfigs(){
		
		List<String> result=new LinkedList<String>();
		Iterator<JsonElementConfig> typesIter=this.jsonElementConfigs.iterator();
		
		while(typesIter.hasNext()){
			JsonElementConfig jsc=typesIter.next();
			if(jsc.isPremitiveConfig()){
				JsonPrimitiveConfig jpc=(JsonPrimitiveConfig) jsc;
				if(jpc.isStringConfig()){
					result.add(((JsonStringConfig)jpc).getValue());
				}else{
					System.out.println("not supported");}
			}else{
				System.out.println("not supported");}
		}
		return result;
	}

	@Override
	public String toString() {
		return "SimpleTypeIndexConfig [jsonElementConfigs=" + jsonElementConfigs + "]";
	}
	
	
}
