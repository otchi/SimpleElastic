package test.com.edifixio.simplElastic.applicatif;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.SearchInElasctic;
import com.edifixio.simplElastic.application.elasticResults.AggrsReturnObject;
import com.edifixio.simplElastic.application.elasticResults.ApplicationReturn;

import test.com.edifixio.simplElastic.AOPandCGlib.TestRessourcesLoader;

public class ElasticSearchFacetTest {
	private static final String SPRING_CONFIG="BeansPing.xml";
	private static final String MAIN_CONFIG_FACTORY="nested_facet_search_in";
	private final static   SearchInElasctic APPLI_CONFIG=
			(  SearchInElasctic  )
			new FileSystemXmlApplicationContext(
					TestRessourcesLoader.getPathRessource(ElasticSearchFacetTest.class,SPRING_CONFIG))
										.getBean(MAIN_CONFIG_FACTORY);
	
	@Test
	public void nestedFacetTest() throws FileNotFoundException, IOException{
	ApplicationReturn ro=APPLI_CONFIG.search(JsonHandleUtil.jsonFile(
				TestRessourcesLoader.loadRessource(this.getClass(),
				"query/nested_facet_query.json")).getAsJsonObject());
	AggrsReturnObject aro=ro.getAggrs();
	AggrsReturnObject arocp= aro.getCopy();
	arocp.getFacets().get("test").getBuckets().get("japan").setIsChecked(false);
	System.out.println(aro);
	System.out.println(arocp);
	aro.update(arocp);
	System.out.println(aro);
	}

}
