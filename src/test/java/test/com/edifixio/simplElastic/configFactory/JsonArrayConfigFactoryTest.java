package test.com.edifixio.simplElastic.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.simplElastic.application.SimpleFacetsConfig;
import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleRootConfig;
import com.edifixio.simplElastic.config.JsonArrayConfig;
import com.edifixio.simplElastic.configFactory.DeclaredMapConfigFactory;
import com.edifixio.simplElastic.configFactory.ArrayConfigFactory;
import com.edifixio.simplElastic.configFactory.ElementConfigFactory;
import com.edifixio.simplElastic.configFactory.AbstractMapConfigFactory;
import com.edifixio.simplElastic.configFactory.PrimitiveConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class JsonArrayConfigFactoryTest {
	private Class<? extends JsonArrayConfig> classToFactory;
	private PrimitiveConfigFactory jsonPrimitiveConfigFactory;
	private ArrayConfigFactory jArrayConfigFactory;
	private AbstractMapConfigFactory jObjectConfigFactory;
	private PrimitiveConfigFactory jPremitiveConfigFactory;
/****************************************************************************************************************************/	
	public JsonArrayConfigFactoryTest(Class<? extends JsonArrayConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactory, ArrayConfigFactory jArrayConfigFactory,
			AbstractMapConfigFactory jObjectConfigFactory, PrimitiveConfigFactory jPremitiveConfigFactory) {
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

		PrimitiveConfigFactory jConfigFactory=new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class);
		Map<String, ElementConfigFactory> childFactories=new HashMap<String, ElementConfigFactory>();
		
		childFactories.put("dd",jConfigFactory);
			
		
		AbstractMapConfigFactory jObjectConfigFactory=new DeclaredMapConfigFactory(SimpleRootConfig.class, 
																				jConfigFactory, childFactories);
		return Arrays.asList(new Object[][]{
			{SimpleFacetsConfig.class,jConfigFactory,null,jObjectConfigFactory,jConfigFactory}
		});
	}

/*****************************************************************************************************************************/

	@Test
	public void SimpleTest(){
		String ja="[\"amine\",{dd:\"kk\"}]";
		ArrayConfigFactory jacf=new ArrayConfigFactory(
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
