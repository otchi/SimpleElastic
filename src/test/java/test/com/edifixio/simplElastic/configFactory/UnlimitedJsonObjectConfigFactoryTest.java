package test.com.edifixio.simplElastic.configFactory;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleRootConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.configFactory.JsonArrayConfigFactory;
import com.edifixio.simplElastic.configFactory.JsonObjectConfigFactory;
import com.edifixio.simplElastic.configFactory.JsonPrimitiveConfigFactory;
import com.edifixio.simplElastic.configFactory.UnlimitedJsonObjectConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class UnlimitedJsonObjectConfigFactoryTest {
	
	
	private JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory;
	private JsonArrayConfigFactory jArrayConfigFactory;
	private JsonObjectConfigFactory jObjectConfigFactory;
	private JsonPrimitiveConfigFactory jPremitiveConfigFactory;
	private Class<? extends JsonObjectConfig> classToFactory;
	
	public UnlimitedJsonObjectConfigFactoryTest(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory, JsonArrayConfigFactory jArrayConfigFactory,
			JsonObjectConfigFactory jObjectConfigFactory, JsonPrimitiveConfigFactory jPremitiveConfigFactory) {
		super();
		this.classToFactory = classToFactory;
		this.jsonPrimitiveConfigFactory = jsonPrimitiveConfigFactory;
		this.jArrayConfigFactory = jArrayConfigFactory;
		this.jObjectConfigFactory = jObjectConfigFactory;
		this.jPremitiveConfigFactory = jPremitiveConfigFactory;
	}
	
	@Parameterized.Parameters
	public static Collection<? > injectValus(){
		
		JsonPrimitiveConfigFactory jsPremitiveConfigFactory=
				new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class);
		
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,jsPremitiveConfigFactory,null,null,jsPremitiveConfigFactory}	
		});
	}
	
	@Test
	public void teston(){
		try {
			System.out.println(new UnlimitedJsonObjectConfigFactory(classToFactory,
					jsonPrimitiveConfigFactory,
					jArrayConfigFactory,
					jObjectConfigFactory,
					jPremitiveConfigFactory).getJsonElementConfig(new JsonParser().parse("{bt:\"tt\",btt:\"tt\"}")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
		
		
	
	
	

}
