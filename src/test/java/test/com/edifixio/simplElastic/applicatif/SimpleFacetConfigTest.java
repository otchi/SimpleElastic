package test.com.edifixio.simplElastic.applicatif;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.SimpleFacetConfigUnit;
import com.edifixio.simplElastic.application.SimpleFacetsConfig;
import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.elasticResults.Aggregations;
import com.edifixio.simplElastic.application.elasticResults.FacetableAggr;
import com.edifixio.simplElastic.config.JsonElementConfig;
import com.google.gson.JsonObject;

import test.com.edifixio.simplElastic.AOPandCGlib.TestRessourcesLoader;

@RunWith(Parameterized.class)
public class SimpleFacetConfigTest {

	private JsonObject jsQuery;
	private SimpleFacetsConfig simpleFacetsConfig;
	private Map<String, FacetableAggr> facetsData;

	public SimpleFacetConfigTest(JsonObject jsQuery, SimpleFacetsConfig simpleFacetsConfig,
			Map<String, FacetableAggr> facetsData) {
		super();
		this.jsQuery = jsQuery;
		this.simpleFacetsConfig = simpleFacetsConfig;
		this.facetsData = facetsData;
	}

	@Parameterized.Parameters
	public static Collection<?> inputs() throws IOException {
		/*************** test 1 parameters ********/
		JsonObject jsonObject1 = JsonHandleUtil
				.jsonFile(TestRessourcesLoader.loadRessource(
									SimpleFacetConfigTest.class,
									"test/JsonToFacetsConfigTest1.json"))
				.getAsJsonObject();
		Map<String, FacetableAggr> facetsData1 = Aggregations
				.getAggregations(jsonObject1.getAsJsonObject("_aggregation")).getFacetableAggrs();
		
		SimpleFacetsConfig simpleFacetsConfig1 = new SimpleFacetsConfig();
		simpleFacetsConfig1.addJsonElementConfig(new SimpleJsonStringConfig("test"));
		simpleFacetsConfig1.addJsonElementConfig(new SimpleJsonStringConfig("rng"));
		/***************
		 * test 1 nested term[range] parameters 
		 ****************************************************************************/
		
		JsonObject jsonObject2 = JsonHandleUtil
				.jsonFile(TestRessourcesLoader.loadRessource(
						SimpleFacetConfigTest.class,"test/JsonToFacetsConfigTest2.json"))
				.getAsJsonObject();
		
		Map<String, FacetableAggr> facetsData2 = Aggregations
				.getAggregations(jsonObject2.getAsJsonObject("_aggregation")).getFacetableAggrs();
		Map<String, JsonElementConfig> mapConfig2 = new HashMap<String, JsonElementConfig>();
		mapConfig2.put("facet_name", new SimpleJsonStringConfig("test"));
		SimpleFacetsConfig simpleFacetsConfig21 = new SimpleFacetsConfig();
		simpleFacetsConfig21.addJsonElementConfig(new SimpleJsonStringConfig("rng"));
		mapConfig2.put("sub_facets", simpleFacetsConfig21);
		SimpleFacetConfigUnit sfcu1 = new SimpleFacetConfigUnit(mapConfig2);
		SimpleFacetsConfig simpleFacetsConfig2 = new SimpleFacetsConfig();
		simpleFacetsConfig2.addJsonElementConfig(sfcu1);
		
		/***************
		 * test 1 nested range[term] parameters 
		 ****************************************************************************/
		
		JsonObject jsonObject3 = JsonHandleUtil
				.jsonFile(TestRessourcesLoader.loadRessource(
							SimpleFacetConfigTest.class,"test/JsonToFacetsConfigTest3.json"))
				.getAsJsonObject();
		Map<String, FacetableAggr> facetsData3 = Aggregations
				.getAggregations(jsonObject3.getAsJsonObject("_aggregation")).getFacetableAggrs();
		Map<String, JsonElementConfig> mapConfig3 = new HashMap<String, JsonElementConfig>();
		mapConfig3.put("facet_name", new SimpleJsonStringConfig("rng"));
		SimpleFacetsConfig simpleFacetsConfig31 = new SimpleFacetsConfig();
		simpleFacetsConfig31.addJsonElementConfig(new SimpleJsonStringConfig("test"));
		mapConfig3.put("sub_facets", simpleFacetsConfig31);
		SimpleFacetConfigUnit sfcu3 = new SimpleFacetConfigUnit(mapConfig3);
		SimpleFacetsConfig simpleFacetsConfig3 = new SimpleFacetsConfig();
		simpleFacetsConfig3.addJsonElementConfig(sfcu3);

		/*
		Aggregations.getAggregations(jsonObject2.getAsJsonObject("_aggregation"));
		//simpleFacetsConfig2.addJsonElementConfig(sfcu2);
		//System.out.println("config :---->"+simpleFacetsConfig2);*/
		return Arrays.asList(new Object[][] {
				{jsonObject1.getAsJsonObject("_query"),	simpleFacetsConfig1, facetsData1},
				{jsonObject2.getAsJsonObject("_query"), simpleFacetsConfig2, facetsData2} ,
				{jsonObject3.getAsJsonObject("_query"), simpleFacetsConfig3, facetsData3}
			});
	}

	@Test
	public void test() throws IOException {
		//System.out.println(" input query :---> " +jsQuery);
		System.out.println(" appel test :---> " + simpleFacetsConfig.process(jsQuery, facetsData));
	}
}
