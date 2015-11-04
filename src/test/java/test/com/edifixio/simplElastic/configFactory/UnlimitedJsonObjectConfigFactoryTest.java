package test.com.edifixio.simplElastic.configFactory;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleRootConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.configFactory.ArrayConfigFactory;
import com.edifixio.simplElastic.configFactory.AbstractMapConfigFactory;
import com.edifixio.simplElastic.configFactory.PrimitiveConfigFactory;
import com.edifixio.simplElastic.configFactory.MapConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class UnlimitedJsonObjectConfigFactoryTest {
	
	
	private PrimitiveConfigFactory jsonPrimitiveConfigFactory;
	private ArrayConfigFactory jArrayConfigFactory;
	private AbstractMapConfigFactory jObjectConfigFactory;
	private PrimitiveConfigFactory jPremitiveConfigFactory;
	private Class<? extends JsonObjectConfig> classToFactory;
	
	public UnlimitedJsonObjectConfigFactoryTest(Class<? extends JsonObjectConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactory, ArrayConfigFactory jArrayConfigFactory,
			AbstractMapConfigFactory jObjectConfigFactory, PrimitiveConfigFactory jPremitiveConfigFactory) {
		super();
		this.classToFactory = classToFactory;
		this.jsonPrimitiveConfigFactory = jsonPrimitiveConfigFactory;
		this.jArrayConfigFactory = jArrayConfigFactory;
		this.jObjectConfigFactory = jObjectConfigFactory;
		this.jPremitiveConfigFactory = jPremitiveConfigFactory;
	}
	
	@Parameterized.Parameters
	public static Collection<? > injectValus(){
		
		PrimitiveConfigFactory jsPremitiveConfigFactory=
				new PrimitiveConfigFactory().setStringConfigAndReturn(SimpleJsonStringConfig.class);
		
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,jsPremitiveConfigFactory,null,null,jsPremitiveConfigFactory}	
		});
	}
	
	@Test
	public void teston(){
		try {
			System.out.println(new MapConfigFactory(classToFactory,
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
