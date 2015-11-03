package test.com.edifixio.simplElastic.configFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.config.JsonStringConfig;
import com.edifixio.simplElastic.configFactory.JsonPrimitiveConfigFactory;
import com.google.gson.JsonParser;

public class JsonPrimitiveConfigFactoryTest {
	private JsonPrimitiveConfigFactory jpcf;
	private JsonParser jp=new JsonParser();
	
	@Before
	public void  initPrimaryFactry(){
		jpcf=new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class);
	}
	
	@Test
	public void testPrimaryFactoryWithString(){
		
		try {
			JsonStringConfig jsc=(JsonStringConfig) jpcf.getJsonElementConfig(jp.parse("amine"));
			Assert.assertEquals("amine", jsc.getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
