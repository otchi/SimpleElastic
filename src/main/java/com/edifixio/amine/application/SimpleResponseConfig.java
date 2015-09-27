package com.edifixio.amine.application;

import java.util.List;
import java.util.Map;

import com.edifixio.amine.application.elasticResults.HitObject;
import com.edifixio.amine.application.elasticResults.Hits;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.utils.JsonPathTree;

public class SimpleResponseConfig extends JsonObjectConfig {
	
	private static final String CLASS="class";
	private static final String MAPPING="mapping";
	

	public SimpleResponseConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}
	
	/***********************************************************************************************/
	public List<HitObject> getHitObjectList(Hits setSources)throws ReflectiveOperationException{
		
		Class<?> c=Class.forName(((JsonStringConfig)mapConfig.get(CLASS)).getValue());
		return ((SimpleResponseMappingConfig)mapConfig.get(MAPPING))
													  .getHitObject(c, setSources);
	}
	
	public JsonPathTree getLazyTree(){
		return ((SimpleResponseMappingConfig)this.mapConfig.get(MAPPING)).getLazyTree();
	}

	@Override
	public String toString() {
		return "SimpleResponseConfig [mapConfig=" + mapConfig + "]";
	}
	
	

}
