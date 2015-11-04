package test.com.edifixio.simplElastic.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleRootConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.configFactory.DeclaredMapConfigFactory;
import com.edifixio.simplElastic.configFactory.ElementConfigFactory;
import com.edifixio.simplElastic.configFactory.PrimitiveConfigFactory;
import com.google.gson.JsonParser;



@RunWith(Parameterized.class)
public class DeclaredJsonObjectConfigFactoryTest {
	private static final JsonParser JP=new JsonParser();
	private Class<? extends JsonObjectConfig> classToFactory;
	private PrimitiveConfigFactory jsonPrimitiveConfigFactory;
	private	Map<String, ElementConfigFactory> childFactories;
	private String jsonString;

/**********************************************************************************************************************/	
	public DeclaredJsonObjectConfigFactoryTest(
			Class<? extends JsonObjectConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactory, 
			Map<String, ElementConfigFactory> childFactories,
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

		Map<String, ElementConfigFactory> childFactories =
				new HashMap<String, ElementConfigFactory>();
		
		childFactories.put("kaka",
						new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class)
				);
		
		childFactories.put("dd",
				new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class));
		
		Map<String, ElementConfigFactory> childFactories1 =
				new HashMap<String, ElementConfigFactory>();
		
		childFactories1.put("kaka",
				new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class)
				);
		
		childFactories1.put("dd",
				new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class));
		
		DeclaredMapConfigFactory subObject=
				new DeclaredMapConfigFactory(SimpleRootConfig.class, 
						new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class),
										childFactories1);
		
		childFactories.put("tab", subObject);
		
		
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class),childFactories,
				"{kaka:\"tt\",dd:\"popo\",tab:{kaka:\"tt\",dd:\"popo\"}}"}
		});
	}

/******************************************************************************************************************/
	
	@Test
	public void Test(){
		
		try {
			new DeclaredMapConfigFactory(
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
