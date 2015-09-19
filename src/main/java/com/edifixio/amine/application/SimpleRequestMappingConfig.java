package com.edifixio.amine.application;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonObject;

public class SimpleRequestMappingConfig extends JsonObjectConfig {

	/****************************************************************************************************/
	public SimpleRequestMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	/******************************************************************************************************/
	public void process(JsonObject query,Object request)
		throws ReflectiveOperationException {
		
		MappingRequestResolver mrr=new MappingRequestResolver(query);
		Iterator<Entry<String, JsonElementConfig>> mapConfigIter=mapConfig.entrySet().iterator();
		mrr.parsing();
		
		while(mapConfigIter.hasNext()){
			
			Entry<String, JsonElementConfig> entry=mapConfigIter.next();
			String key=entry.getKey();
			JsonElementConfig value=entry.getValue();
			
			// here to extends object field (can detect a nested object )
			Method method=request.getClass().getMethod("get"+key.substring(0, 1).toUpperCase()
															+key.substring(1));
			Object attr=method.invoke(request);
			if(value.isPremitiveConfig()){
				primitiveElementProcess(mrr,(JsonPrimitiveConfig) value, attr.toString());
			}else{
				if(value.isArrayConfig()){
					JsonArrayConfig jac=(JsonArrayConfig) value;
					for(JsonElementConfig jec:jac.getJsonElementConfigs()){
						if(jec.isPremitiveConfig()){
							primitiveElementProcess(mrr,(JsonPrimitiveConfig) jec, attr.toString());
						}else{ System.out.println("not supported ");}
					}	
				}else{ System.out.println("not supported ");}
			}
		}
	}
	
	/**********************************************************************************************************/
	private void primitiveElementProcess(MappingRequestResolver mrr,JsonPrimitiveConfig jpc,String input){
		//System.out.println(mrr.getCorresp());
		if(jpc.isStringConfig()){
			JsonStringConfig jsc=(JsonStringConfig)jpc;
			mrr.varResolver(jsc.getValue(), input);
			
		}else{
			System.out.println("exception - not supported");
		}
		
	}

	@Override
	public String toString() {
		return "SimpleRequestMappingConfig [mapConfig=" + mapConfig + "]";
	}
	
	/****************************************************************************************************************/
}
