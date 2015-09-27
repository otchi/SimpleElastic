package test.com.edifixio.amine.applicatif;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleResponseConfigUnit;
import com.edifixio.amine.application.SimpleResponseMappingConfig;
import com.edifixio.amine.application.elasticResults.HitObject;
import com.edifixio.amine.application.elasticResults.Hits;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.ComplexTestResponseObject;
import com.edifixio.amine.object.TestObject;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;


@RunWith(Parameterized.class)
public class SimpleResponseMappingConfigTest {
	private Map<String, JsonElementConfig> mapConfig;
	private Class<?> responseClass;
	private Hits setSources;
	
	
	

	public SimpleResponseMappingConfigTest(Map<String, JsonElementConfig> mapConfig, Class<?> responseClass,
			Hits setSources) {
		super();
		this.mapConfig = mapConfig;
		this.responseClass = responseClass;
		this.setSources = setSources;
	}

	
	@Parameterized.Parameters
	public static Collection<?> parametere(){
		return Arrays.asList(new Object[][]{
			SimpleParams(),
			complexParams(),
			simpleCompoundParam()
		});
	}
	
	@Test
	public void test() throws ReflectiveOperationException{
		
		SimpleResponseMappingConfig srmc=new SimpleResponseMappingConfig(mapConfig);
		List<HitObject> obj=srmc.getHitObject(responseClass, setSources);
		System.out.println(obj);
		
	}
	
	/***********************************************************/
	public static Object[] SimpleParams(){
		
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		mapConfig.put("f1",new SimpleJsonStringConfig("field1"));
		mapConfig.put("f2",new SimpleJsonStringConfig("field2"));
		mapConfig.put("f3",new SimpleJsonStringConfig("field3"));
		
		Class<?> responseClass=TestObject.class;
		
		Hits setSources=Hits.getHits(JsonHandleUtil.jsonString(
				 "{"
				+ "hits:["
				+ "{_source:"
				+ "{"
				+ "f1:\"katia\","
				+ "f2:true,"
				+ "f3:55"
				+ "}"
				+ "}"
				+ "]"
				+ "}")
				.getAsJsonObject());
		return new Object[]{mapConfig,responseClass,setSources};
	}
	/**************************************************************/
	public static Object[] simpleCompoundParam(){
		Map<String, JsonElementConfig> mapConfig1=new HashMap<String, JsonElementConfig>();
		Map<String, JsonElementConfig> mapConfig2=new HashMap<String, JsonElementConfig>();
		mapConfig2.put("name",new SimpleJsonStringConfig("field1"));
		mapConfig1.put("f1",new SimpleResponseConfigUnit(mapConfig2));
		
		Class<?> responseClass=TestObject.class;
		
		Hits setSources=Hits.getHits(JsonHandleUtil.jsonString(
				 "{"
				+ "hits:["
				+ "{_source:"
				+ "{"
				+ "f1:\"katia\""
				+ "}"
				+ "}"
				+ "]"
				+ "}")
				.getAsJsonObject());
		
		return new Object[]{mapConfig1,responseClass,setSources};
	}
	
	/***********************************************************/
	public static Object[] complexParams(){
		
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		
		
		
		mapConfig.put("f1",new SimpleJsonStringConfig("str"));
		mapConfig.put("f2",new SimpleJsonStringConfig("nbr"));
		mapConfig.put("f4",responseUnit1());
		
		Class<?> responseClass=ComplexTestResponseObject.class;
		
		Hits setSources=Hits.getHits(JsonHandleUtil.jsonString(
				 "{"
				+ "hits:["
				+ "{_source:"
				+ "{"
				+ "f1:\"katia\","
				+ "f2:50,"
				+ "f4:"
				+ "{"
				+ "f1:\"katia\","
				+ "f2:true,"
				+ "f3:55"
				+ "}"
				+ "}"
				+ "}"
				+ "]"
				+ "}")
				.getAsJsonObject());
		return new Object[]{mapConfig,responseClass,setSources};
	}
	
	/***********************************************************/
	public static SimpleResponseConfigUnit responseUnit1(){
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		mapConfig.put("name", new SimpleJsonStringConfig("to"));
		
		Map<String, JsonElementConfig> mapConfig1=new HashMap<String, JsonElementConfig>();
		mapConfig1.put("f1",new SimpleJsonStringConfig("field1"));
		mapConfig1.put("f2",new SimpleJsonStringConfig("field2"));
		
		
		Map<String, JsonElementConfig> mapConfig2=new HashMap<String, JsonElementConfig>();
		mapConfig2.put("name",new SimpleJsonStringConfig("field3"));
		mapConfig1.put("f3",new SimpleResponseConfigUnit(mapConfig2));
		//mapConfig1.put("f3",new SimpleJsonStringConfig("field3"));
		
		SimpleResponseMappingConfig srmc=new SimpleResponseMappingConfig(mapConfig1);
		
		mapConfig.put("mapping", srmc);
		System.out.println(mapConfig);
		
		return new SimpleResponseConfigUnit(mapConfig);
	
	}
	
	
	


}
