package test.com.edifixio.simplElastic.applicatif;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.edifixio.simplElastic.application.SimpleJsonBooleanConfig;
import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleResponseConfig;
import com.edifixio.simplElastic.application.SimpleResponseConfigUnit;
import com.edifixio.simplElastic.application.SimpleResponseMappingConfig;
import com.edifixio.simplElastic.application.SimpleRootConfig;
import com.edifixio.simplElastic.config.JsonElementConfig;
import com.google.gson.JsonObject;

public class LazyTreeTest {
	private SimpleResponseMappingConfig srmc;
	
	public static SimpleResponseConfigUnit ProduiceSRCUnit(String name, Boolean lazy,SimpleResponseMappingConfig mapping){
		Map<String, JsonElementConfig> config=new HashMap<String, JsonElementConfig>();
		config.put("name", new SimpleJsonStringConfig(name));
		if(lazy!=null)config.put("lazy", new SimpleJsonBooleanConfig(lazy));
		if(mapping!=null)config.put("mapping", mapping);
		return new SimpleResponseConfigUnit(config);
	}

	/****** test a refaire */////////////////////////////
	@Before
	public void init(){
		Map<String,Map<String, JsonElementConfig>> mapConfigs=new HashMap<String, Map<String,JsonElementConfig>>();
		
		String mapUnit1="mapUnit1";
 		mapConfigs.put(mapUnit1, new HashMap<String, JsonElementConfig>());
 		mapConfigs.get(mapUnit1).put("f1", new SimpleJsonStringConfig("field1"));
 		/*************************************************************************************************/
 		mapConfigs.get(mapUnit1).put("f2",ProduiceSRCUnit("field2", true,null) );
 		/*********************************************************************************************************/
 		/*********************************************************************************************************/
 		String mapping3="mapping3";
 		mapConfigs.put(mapping3, new HashMap<String, JsonElementConfig>());
 		/***************************************************************************************************/
 		String mapping311="mapping311";
 		mapConfigs.put(mapping311, new HashMap<String, JsonElementConfig>());
 		mapConfigs.get(mapping311).put("f311", ProduiceSRCUnit("field311",true,null));
 		mapConfigs.get(mapping3).put("f31", ProduiceSRCUnit("field31",true,new SimpleResponseMappingConfig(mapConfigs.get(mapping311))));
 		mapConfigs.get(mapUnit1).put("f3", ProduiceSRCUnit("field3", false, new SimpleResponseMappingConfig(mapConfigs.get(mapping3))));
 
 		srmc=new SimpleResponseMappingConfig(mapConfigs.get(mapUnit1));
 		
	}
	
	//@Test
	public void test1(){
		
		System.out.println(srmc.getLazyTree());
		System.out.println(srmc);
		
	}
	
	@Test
	public void test2(){
		Map<String, JsonElementConfig> responseMappping=new HashMap<String, JsonElementConfig>();
		responseMappping.put("mapping", srmc);
		Map<String, JsonElementConfig> rootMappping=new HashMap<String, JsonElementConfig>();
		rootMappping.put("_response", new SimpleResponseConfig(responseMappping));
		SimpleRootConfig src=new SimpleRootConfig(rootMappping);
		System.out.println(src);
		JsonObject js=new JsonObject();
		src.excludeLazyElement(js);
		System.out.println(js);
		
		
	}
	
	
	


}
