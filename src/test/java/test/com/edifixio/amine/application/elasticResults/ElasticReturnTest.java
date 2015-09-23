package test.com.edifixio.amine.application.elasticResults;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.application.elasticResults.ElasticReturn;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;

public class ElasticReturnTest {
	private JsonObject jsonObject;
	
	
	@Before
	public void init() throws IOException{
	
		jsonObject=JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(this.getClass(),
				"response/my_response1.json")).getAsJsonObject();
		
	}
	
	@Test
	public void test(){
		ElasticReturn elasticReturn=ElasticReturn.getElasticReturn(jsonObject);
		System.out.println("-->"+elasticReturn.getAggregation().getFacetableAggrs());
		Assert.assertTrue(true);
		
	}

}
