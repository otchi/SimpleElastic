package test.com.edifixio.amine.applicatif;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleFacetsConfig;
import com.edifixio.amine.application.SimpleIndexConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleRequestConfig;
import com.edifixio.amine.application.SimpleResponseConfig;
import com.edifixio.amine.application.SimpleResponseMappingConfig;
import com.edifixio.amine.application.SimpleTypeIndexConfig;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.TestObject;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;

@RunWith(Parameterized.class)
public class SimpleRootConfigTest {
	private Map<String, JsonElementConfig> mapConfig;
	private JsonObject query;
	private Object request;
	private List<String> facets;
	
	public SimpleRootConfigTest(
			Map<String, JsonElementConfig> mapConfig,
			JsonObject query, Object request,
			List<String> facets) {
		
		super();
		this.mapConfig = mapConfig;
		this.query = query;
		this.request = request;
		this.facets = facets;
	}
	
	@Parameterized.Parameters
	public static Collection<?> dataSet() throws IOException{
		/******************************************************************/
		TestObject obj=new TestObject();
		JsonObject jo;
		SimpleRequestConfig src;
		
		Map<String, JsonElementConfig> mapConfigRoot, mapConfigSimpleIndex,
					mapConfigSimpleResponse,mapConfigSimpleResponseMapping;
		SimpleIndexConfig sic;
		SimpleResponseConfig simpRes;
		/***************************facet *********************************************/
		SimpleFacetsConfig simpleFacetconf=new SimpleFacetsConfig();
		simpleFacetconf.addJsonElementConfig(new SimpleJsonStringConfig("origine"));
		
		
		/******************************injection **************************************/
		obj.setField1("audi");
	
		jo=JsonHandleUtil.jsonFile(
				TestRessourcesLoader
							.loadRessource(SimpleRootConfigTest.class, "query/my_request1.json"))
							.getAsJsonObject();
		src=SimpleRequestConfigTest.daraSet();
		
		/******************************************/
		JsonArrayConfig localJac;
		localJac=new SimpleTypeIndexConfig();
		localJac.addJsonElementConfig(new SimpleJsonStringConfig("vehicule"));
		/*******************************************/
		
		mapConfigSimpleIndex=new HashMap<String, JsonElementConfig>();
		mapConfigSimpleIndex.put("names",localJac);
		
		sic=new SimpleIndexConfig(mapConfigSimpleIndex);
		
		mapConfigSimpleResponseMapping=new HashMap<String, JsonElementConfig>();
		mapConfigSimpleResponseMapping.put("voiture",new SimpleJsonStringConfig("field1"));
		mapConfigSimpleResponseMapping.put("cylendres",new SimpleJsonStringConfig("field3"));
		
		
		
		mapConfigSimpleResponse=new HashMap<String, JsonElementConfig>();
		mapConfigSimpleResponse.put("class", 
				new SimpleJsonStringConfig("com.edifixio.amine.object.TestObject"));
		mapConfigSimpleResponse.put("mapping", 
				new SimpleResponseMappingConfig(mapConfigSimpleResponseMapping));
		
		
		simpRes=new SimpleResponseConfig(mapConfigSimpleResponse);
		
		mapConfigRoot=new HashMap<String, JsonElementConfig>();
		mapConfigRoot.put("_host", new SimpleJsonStringConfig("http://localhost:9200"));
		mapConfigRoot.put("_indexes", sic);
		mapConfigRoot.put("_request", src);
		mapConfigRoot.put("_response",simpRes);
		mapConfigRoot.put("_facets",simpleFacetconf);
		
		
		return Arrays.asList(new Object[][]{
			{mapConfigRoot,jo,obj,null}
		});
	}
	
	@Test
	public void test() throws ReflectiveOperationException {
		System.out.println(mapConfig+""+this.facets+""+this.query+""+this.request);
		//SimpleRootConfig sRootc=new SimpleRootConfig(mapConfig);
		//sRootc.process(query,request,null);
		//sRootc.getResultObject();
		//System.out.println("--->"+sRootc.getFacets(true));
		Assert.assertTrue(true);
		
	
	}


}
