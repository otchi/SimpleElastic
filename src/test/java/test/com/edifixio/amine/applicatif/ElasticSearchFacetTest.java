package test.com.edifixio.amine.applicatif;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.application.ResultObject;
import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;

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
	ResultObject ro=APPLI_CONFIG.search(JsonHandleUtil.jsonFile(
				TestRessourcesLoader.loadRessource(this.getClass(),
				"query/nested_facet_query.json")).getAsJsonObject());
	System.out.println(ro.getFacets());
			
	}

}
