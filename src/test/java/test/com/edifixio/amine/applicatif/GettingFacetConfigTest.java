package test.com.edifixio.amine.applicatif;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleFacetConfigUnit;
import com.edifixio.amine.application.SimpleFacetsConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.elasticResults.Aggregations;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;

@RunWith(Parameterized.class)
public class GettingFacetConfigTest {
	
	private SimpleFacetsConfig sfc;
	private JsonObject jsAggs;

	
	
	
	public GettingFacetConfigTest(SimpleFacetsConfig sfc, JsonObject jsAggs) {
		super();
		this.sfc = sfc;
		this.jsAggs = jsAggs;

	}

	@Parameterized.Parameters
	public static Collection<?> parameters() throws IOException{
		/**********************************************************************************************************/
		JsonObject jsonObject1 = JsonHandleUtil
				.jsonFile(TestRessourcesLoader.loadRessource(
						  GettingFacetConfigTest.class,
						  "test/JsonToFacetsConfigTest1.json"))
							.getAsJsonObject()
				.getAsJsonObject("_aggregation");
		SimpleFacetsConfig simpleFacetsConfig1 = new SimpleFacetsConfig();
		simpleFacetsConfig1.addJsonElementConfig(new SimpleJsonStringConfig("test"));
		/**********************************************************************************************************/
		JsonObject jsonObject2 = JsonHandleUtil.jsonFile(
									TestRessourcesLoader.loadRessource(
										GettingFacetConfigTest.class, 
										"test/JsonToFacetsConfigTest2.json"))
						.getAsJsonObject()
						.getAsJsonObject("_aggregation");
	
		Map<String, JsonElementConfig> mapConfig2 = new HashMap<String, JsonElementConfig>();
		mapConfig2.put("facet_name", new SimpleJsonStringConfig("test"));
		SimpleFacetsConfig simpleFacetsConfig21 = new SimpleFacetsConfig();
		simpleFacetsConfig21.addJsonElementConfig(new SimpleJsonStringConfig("rng"));
		mapConfig2.put("sub_facets", simpleFacetsConfig21);
		SimpleFacetConfigUnit sfcu1 = new SimpleFacetConfigUnit(mapConfig2);
		SimpleFacetsConfig simpleFacetsConfig2 = new SimpleFacetsConfig();
		simpleFacetsConfig2.addJsonElementConfig(sfcu1);
		
		
		return Arrays.asList(new Object[][]{
			{simpleFacetsConfig1,jsonObject1},
			{simpleFacetsConfig2,jsonObject2}
		});
	}
	
	@Test
	public void test(){
		//System.out.println(Aggregations.getAggregations(jsAggs).getFacetableAggregations());
		System.out.println(sfc.getFacets(Aggregations.getAggregations(jsAggs).getFacetableAggregations())
							);
		
	}
	

}
