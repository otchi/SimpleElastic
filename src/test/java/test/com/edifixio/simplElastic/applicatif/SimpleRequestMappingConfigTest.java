package test.com.edifixio.simplElastic.applicatif;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.SimpleFacetsConfig;
import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleRequestMappingConfig;
import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.object.TestObject;
import com.google.gson.JsonObject;

@RunWith(Parameterized.class)
public class SimpleRequestMappingConfigTest {
	private Map<String, JsonElementConfig> mapConf;
	private Object request;
	private JsonObject jsonObject;
	
	public SimpleRequestMappingConfigTest(
			Map<String, JsonElementConfig> mapConf,
			Object request, 
			JsonObject jsonObject) {
		
		super();
		this.mapConf = mapConf;
		this.request = request;
		this.jsonObject = jsonObject;
	}
	@Parameterized.Parameters
	public static Collection<?> testValue(){
		
		Map<String, JsonElementConfig> mapConf=
				new HashMap<String, JsonElementConfig>();
		SimpleFacetsConfig sac=new SimpleFacetsConfig();
		sac.addJsonElementConfig(new SimpleJsonStringConfig("amine"));
		sac.addJsonElementConfig(new SimpleJsonStringConfig("katia"));
		mapConf.put("field1", sac);
		mapConf.put("field2", new SimpleJsonStringConfig("ouardia"));
		
		TestObject myTestObject=new TestObject();
		myTestObject.setField1("frére");
		myTestObject.setField2(true);
		
		
		
		JsonObject jo=JsonHandleUtil.jsonString(
				"{s:\"test1 ${ouardia}\"}")
				.getAsJsonObject();
		JsonObject jo1=JsonHandleUtil.jsonString(
				"{s:\"test1 ${ouardia}\","+
				 "object1:{"+
				 "array1:[\"test2 : ${amine}\",{katiaField:\"${katia} test3\"}]}"+
				  "}")
				.getAsJsonObject();
		return Arrays.asList(new Object[][]{
			{mapConf,myTestObject,jo}, /// you must have an exception here
			{mapConf,myTestObject,jo1}
		});
	}
	
	@Test
	public void test(){
		System.out.println("_______________________________________________________________");
		try {
			new SimpleRequestMappingConfig(mapConf).process( jsonObject,request);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Assert.assertTrue(true);
		System.out.println("_______________________________________________________________");
	}
	


	
}
