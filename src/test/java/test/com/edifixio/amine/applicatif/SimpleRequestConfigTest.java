package test.com.edifixio.amine.applicatif;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleFacetsConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleRequestConfig;
import com.edifixio.amine.application.SimpleRequestMappingConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.TestObject;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@RunWith(Parameterized.class)
public class SimpleRequestConfigTest {
	private static final String CLASS="class";
	private static final String MAPPING="mapping";
	
	private Object object;
	private Map<String, JsonElementConfig> mapConfig;
	private JsonObject query;
	
	
	
	public SimpleRequestConfigTest(Object object,
			Map<String, JsonElementConfig> mapConfig, JsonObject query) {
		super();
		this.object = object;
		this.mapConfig = mapConfig;
		this.query = query;
	}

	@Parameterized.Parameters
	public static Collection<?> parameters() {
		Map<String, JsonElementConfig> mapConf=
				new HashMap<String, JsonElementConfig>();
		SimpleFacetsConfig sac=new SimpleFacetsConfig();
		sac.addJsonElementConfig(new SimpleJsonStringConfig("monfilm"));
		sac.addJsonElementConfig(new SimpleJsonStringConfig("film"));
		mapConf.put("field1", sac);
		mapConf.put("field2", new SimpleJsonStringConfig("statham"));
		
		
		
		
		TestObject to=new TestObject();
		to.setField1("snatch");
		to.setField2(false);
		
		
		
		JsonElementConfig classJsc=new SimpleJsonStringConfig("com.edifixio.amine.object.TestObject");
		JsonElementConfig mappingJse=new SimpleRequestMappingConfig(mapConf);
		
		Map<String, JsonElementConfig> map=
				new HashMap<String, JsonElementConfig>();
		
		map.put(CLASS, classJsc);
		map.put(MAPPING, mappingJse);
		
		
		
		JsonObject jo=JsonHandleUtil.jsonString("{"
				+ "ds:{"
				+ "cc:[\"  ${statham}  cc ${monfilm}\"]"
				+ "}"
				+ ",dc:\"dcbel : ${film}\""
				+ "}")
				
				.getAsJsonObject();
		
		return Arrays.asList(new Object[][]{
			{to,map,jo}
		});
		
	}
	
	@Test
	public void myTest(){
		System.out.println("*************************************** begin SimpleRequestConfig"
				+ " ************************************");
		SimpleRequestConfig src=new SimpleRequestConfig(mapConfig);
		try {
			src.process(object, query);
			System.out.println("result---->"+query);
			//System.out.println(src);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(true);
		System.out.println("*************************************** end SimpleRequestConfig"
				+ " ************************************");

		
	}
	
	public static SimpleRequestConfig daraSet() throws IOException{
		Map<String, JsonElementConfig> mapConf=
				new HashMap<String, JsonElementConfig>();
		SimpleFacetsConfig sac=new SimpleFacetsConfig();	
		sac.addJsonElementConfig(new SimpleJsonStringConfig("voiture"));
		mapConf.put("field1", sac);
		
	
		
		
		
		JsonElementConfig classJsc=new SimpleJsonStringConfig("com.edifixio.amine.object.TestObject");
		JsonElementConfig mappingJse=new SimpleRequestMappingConfig(mapConf);
		
		Map<String, JsonElementConfig> map=
				new HashMap<String, JsonElementConfig>();
		
		map.put(CLASS, classJsc);
		map.put(MAPPING, mappingJse);
		
		

		
		return new SimpleRequestConfig(map);
		
	}
	
	
	
}
