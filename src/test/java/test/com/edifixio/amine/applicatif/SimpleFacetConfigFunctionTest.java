package test.com.edifixio.amine.applicatif;

import org.junit.Assert;
import org.junit.Test;

import com.edifixio.amine.application.SimpleFacetsConfig;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SimpleFacetConfigFunctionTest {
	private FacetableAggr aggr;
	private String fieldName;
	private JsonObject jsonObject;
	
	
	public void initTermAggr(){
		this.aggr=FacetableAggr.getFacetableAggr(
				JsonHandleUtil.jsonString( "["
            +"{"
            +"\"key\": 4,"
            +"   \"doc_count\": 5"
            +"},"
            +"{"
            +"   \"key\": 5,"
            +"   \"doc_count\": 2"
            +"}"
            +"]").getAsJsonArray()).getAsTermAggr();
		
		this.jsonObject=JsonHandleUtil.jsonString(
		"{"
		+"\"terms\":" 
		+"{"
		+"\"field\": \"cylendres\","
		+"\"size\": 10"
		+"}}").getAsJsonObject();
		
		this.fieldName ="test";
		
	}
	@Test
	public void testTermAggr(){
		this.initTermAggr();
		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse = JsonObjectBuilder.init().begin()
				.putObject(SimpleFacetsConfig.BOOL).begin().putArray(SimpleFacetsConfig.SHOULD).begin();

		//System.out.println(jsonObject.getAsJsonObject(SimpleFacetsConfig.TERMS).get(SimpleFacetsConfig.FIELD).getAsString());
		SimpleFacetsConfig.BuildTermFacet(aggr.getAsTermAggr(),jsonObject, fieldName,buildResponse);
		JsonArray ja=JsonHandleUtil.seletor(SimpleFacetsConfig.BOOL+"::"+SimpleFacetsConfig.SHOULD,
									buildResponse.end().end().end().getJsonElement()).getAsJsonArray();
		Assert.assertEquals(ja.size(),2);
		System.out.println(buildResponse.end().end().end().getJsonElement());
	}
	

	public void initRangeAggr(){
		
		this.jsonObject=JsonHandleUtil.jsonString("{"
					    +"\"range\": {"
					     + "\"field\": \"model\","
					      +"\"ranges\": ["
					       +" {"
					        +"\"from\": 50,"
					         +" \"to\": 100"
					        +"}"
					      +"]"
					    +"}"
					  +"}").getAsJsonObject();
		this.aggr=FacetableAggr.getFacetableAggr(
				JsonHandleUtil.jsonString(
						"["
				        +"{"
				         +"\"key\": \"50.0-100.0\","
				         +"\"from\": 50,"
				     //    +"\"from_as_string\": \"50.0\","
				         +"\"to\": 100,"
				      //   +"\"to_as_string\": \"100.0\","
				         +"\"doc_count\": 2"
				         +"},"
				         +"{"
				         +"\"key\": \"100.0-150.0\","
				         +"\"from\": 100,"
				       //  +"\"from_as_string\": \"100.0\","
				         +"\"to\": 150,"
				      //   +"\"to_as_string\": \"150.0\","
				         +"\"doc_count\": 5"
				         +"}"
				         +"]").getAsJsonArray());
		this.fieldName ="ff";
		
	}
	@Test
	public void testRangeAggr(){
		this.initRangeAggr();
		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse = JsonObjectBuilder.init().begin()
				.putObject(SimpleFacetsConfig.BOOL).begin().putArray(SimpleFacetsConfig.SHOULD).begin();
		SimpleFacetsConfig.BuildRangeFacet(aggr.getAsRangeAggr(),jsonObject,fieldName,buildResponse);
		System.out.println(buildResponse.end().end().end().getJsonElement());
	}
}
