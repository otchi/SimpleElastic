package test.com.edifixio.simplElastic.application.elasticResults;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.elasticResults.Aggregations;
import com.google.gson.JsonObject;

import test.com.edifixio.simplElastic.AOPandCGlib.TestRessourcesLoader;


@RunWith(Parameterized.class)
public class AggregationsTest {
	private JsonObject jsonObject;
	
	
	
	public AggregationsTest(JsonObject jsonObject) {
		super();
		this.jsonObject = jsonObject;
	}


	@Parameterized.Parameters
	public static Collection<?> param() throws IOException{

		JsonObject joAggrTerm=JsonHandleUtil
								.jsonFile(TestRessourcesLoader
											.loadRessource(AggregationsTest.class,
													"response/simple_agg_term.json"))
								.getAsJsonObject();
		
		JsonObject joAggrTermRang=JsonHandleUtil.jsonFile(TestRessourcesLoader
															.loadRessource(AggregationsTest.class,
																	"response/simple_agg_term_rang.json"))
													.getAsJsonObject();
		
		JsonObject nesteTermAgg = JsonHandleUtil
				.jsonFile( TestRessourcesLoader
						.loadRessource(AggregationsTest.class,"test/JsonToFacetsConfigTest2.json"))
				.getAsJsonObject()
				.getAsJsonObject("_aggregation");
		//System.out.println(nesteTermAgg);
		return Arrays.asList(new Object[][]{
			{joAggrTerm},{joAggrTermRang},
			{nesteTermAgg}
		});
	}
	
	@Test
	public void test(){
		//try{
			System.out.println("\n---------------------------------------------------------------------------");
			Aggregations aggr=Aggregations.getAggregations(jsonObject);
			System.out.println("--->test1"+aggr.getFacetableAggrs());
			Assert.assertTrue(true);
			System.out.println("---------------------------------------------------------------------------");
		//}catch(Exception e){
			//Assert.assertTrue(e.getMessage(), false);
		//}
	}

}
