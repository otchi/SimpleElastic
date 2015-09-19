package test.com.edifixio.amine.configFactory;

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
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonArrayConfigFactory;
import com.edifixio.amine.configFactory.JsonElementConfigFactory;
import com.edifixio.amine.configFactory.JsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class JsonArrayConfigFactoryTest {
	private Class<? extends JsonArrayConfig> classToFactory;
	private JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory;
	private JsonArrayConfigFactory jArrayConfigFactory;
	private JsonObjectConfigFactory jObjectConfigFactory;
	private JsonPrimitiveConfigFactory jPremitiveConfigFactory;
/****************************************************************************************************************************/	
	public JsonArrayConfigFactoryTest(Class<? extends JsonArrayConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory, JsonArrayConfigFactory jArrayConfigFactory,
			JsonObjectConfigFactory jObjectConfigFactory, JsonPrimitiveConfigFactory jPremitiveConfigFactory) {
		super();
		this.classToFactory = classToFactory;
		this.jsonPrimitiveConfigFactory = jsonPrimitiveConfigFactory;
		this.jArrayConfigFactory = jArrayConfigFactory;
		this.jObjectConfigFactory = jObjectConfigFactory;
		this.jPremitiveConfigFactory = jPremitiveConfigFactory;
	}
	
/*********************************************************************************************************************************/

	@Parameterized.Parameters
	public static Collection<?> inputParam(){

		JsonPrimitiveConfigFactory jConfigFactory=new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class);
		Map<String, JsonElementConfigFactory> childFactories=new HashMap<String, JsonElementConfigFactory>();
		
		childFactories.put("dd",jConfigFactory);
			
		
		JsonObjectConfigFactory jObjectConfigFactory=new DeclaredJsonObjectConfigFactory(SimpleRootConfig.class, 
																				jConfigFactory, childFactories);
		return Arrays.asList(new Object[][]{
			{SimpleFacetsConfig.class,jConfigFactory,null,jObjectConfigFactory,jConfigFactory}
		});
	}

/*****************************************************************************************************************************/

	@Test
	public void SimpleTest(){
		String ja="[\"amine\",{dd:\"kk\"}]";
		JsonArrayConfigFactory jacf=new JsonArrayConfigFactory(
				classToFactory, jsonPrimitiveConfigFactory, jArrayConfigFactory, 
				jObjectConfigFactory, jPremitiveConfigFactory);
		try {
							jacf.getJsonElementConfig(
									new JsonParser().parse(ja));
							Assert.assertTrue(true);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
