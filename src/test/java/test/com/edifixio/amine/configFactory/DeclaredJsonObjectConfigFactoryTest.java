package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonElementConfigFactory;
import com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory;
import com.google.gson.JsonParser;



@RunWith(Parameterized.class)
public class DeclaredJsonObjectConfigFactoryTest {
	private static final JsonParser JP=new JsonParser();
	private Class<? extends JsonObjectConfig> classToFactory;
	private JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory;
	private	Map<String, JsonElementConfigFactory> childFactories;
	private String jsonString;

/**********************************************************************************************************************/	
	public DeclaredJsonObjectConfigFactoryTest(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory, 
			Map<String, JsonElementConfigFactory> childFactories,
			String jsonString) {
		super();
		this.classToFactory = classToFactory;
		this.jsonPrimitiveConfigFactory =jsonPrimitiveConfigFactory;
		this.childFactories = childFactories;
		this.jsonString=jsonString;
	}

/**********************************************************************************************************************/
	
	@Parameterized.Parameters
	public static Collection<?> testValues(){

		Map<String, JsonElementConfigFactory> childFactories =
				new HashMap<String, JsonElementConfigFactory>();
		
		childFactories.put("kaka",
						new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class)
				);
		
		childFactories.put("dd",
				new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class));
		
		Map<String, JsonElementConfigFactory> childFactories1 =
				new HashMap<String, JsonElementConfigFactory>();
		
		childFactories1.put("kaka",
				new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class)
				);
		
		childFactories1.put("dd",
				new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class));
		
		DeclaredJsonObjectConfigFactory subObject=
				new DeclaredJsonObjectConfigFactory(SimpleRootConfig.class, 
						new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class),
										childFactories1);
		
		childFactories.put("tab", subObject);
		
		
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,new JsonPrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class),childFactories,
				"{kaka:\"tt\",dd:\"popo\",tab:{kaka:\"tt\",dd:\"popo\"}}"}
		});
	}

/******************************************************************************************************************/
	
	@Test
	public void Test(){
		
		try {
			new DeclaredJsonObjectConfigFactory(
	    		 					classToFactory, jsonPrimitiveConfigFactory, childFactories)
	    		 			.getJsonElementConfig(JP.parse(jsonString)).toString();
			Assert.assertTrue(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		} 
	}
	


}
