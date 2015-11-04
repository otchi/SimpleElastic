package test.com.edifixio.simplElastic.applicatif;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.SearchInElasctic;
import com.edifixio.simplElastic.application.SimpleRootConfig;
import com.edifixio.simplElastic.application.elasticResults.ApplicationReturn;
import com.edifixio.simplElastic.configFactory.DeclaredMapConfigFactory;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.edifixio.simplElastic.object.ComplexTestResponseObject;
import com.edifixio.simplElastic.object.TestObject;
import com.google.gson.JsonObject;

import test.com.edifixio.simplElastic.AOPandCGlib.TestRessourcesLoader;

public class ElasticSearchTest {
	private static final String SPRING_CONFIG="BeansApplication.xml";
	private static final String MAIN_CONFIG_FACTORY="main_config";
	private final static   DeclaredMapConfigFactory  META_APPLI_CONFIG=
			( DeclaredMapConfigFactory )
			new FileSystemXmlApplicationContext(
					TestRessourcesLoader.getPathRessource(ElasticSearchTest.class,SPRING_CONFIG))
										.getBean(MAIN_CONFIG_FACTORY);
	
	/*********************************************************************************************************************/
	//@Test
	public void matchAllTestRoot() throws ReflectiveOperationException, QuickElasticException, IOException{
		//System.out.println(this.getClass().getClassLoader().getResource("BeansApplication.xml"));
		
		/***********************************************************************************************/

		SimpleRootConfig application = (SimpleRootConfig) 
							META_APPLI_CONFIG.getJsonElementConfig(
									JsonHandleUtil.jsonFile(
											TestRessourcesLoader.loadRessource(this.getClass(),
											"query/application_match_all_config.json")));
		

		JsonObject query=JsonHandleUtil.jsonFile(
							TestRessourcesLoader.loadRessource(this.getClass(),
							"query/application_match_all.json")).getAsJsonObject();
		TestObject to=new TestObject();
		to.setField1("1");
		
		/**********************************************************************************************/
		application.process(query,to);
		//application.
		System.out.println(query);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getHitObjectList());
		System.out.println("+!+!+!+!+!+!+!+!+!+!>"+((TestObject)application.getHitObjectList().get(0).getSourceObject()).getField3());
		System.out.println(application.getHitObjectList());
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getFacets());
	
		/***********************************************************************************************/
		application.getBasedFacets().get("test").getBuckets().get("europe").setIsChecked(false);
		
		System.out.println(application.getHitObjectList());
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getFacets());
		
		/************************************************************************************************/
		application.process(query,null);
		
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(query);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getBasedFacets());
		
	}
	/**
	 * @throws IOException 
	 * @throws QuickElasticException 
	 * @throws ReflectiveOperationException ***********************************************************************************************************************/
	//@Test
	public void complexLazyModeTest() throws ReflectiveOperationException, QuickElasticException, IOException{
		SearchInElasctic es=new SearchInElasctic(
						JsonHandleUtil.jsonFile(
								TestRessourcesLoader.loadRessource(
										this.getClass(),"query/ids_complex_app_config.json"))
													.getAsJsonObject());
		
		JsonObject jsonObject=JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(
				this.getClass(),"query/ids_app_query.json")).getAsJsonObject();
		ApplicationReturn ro=es.search(jsonObject);
		System.out.println(ro);
		ro=es.search(jsonObject);
		System.out.println(jsonObject);
		System.out.println(ro);	
		ComplexTestResponseObject ctro=(ComplexTestResponseObject) ro.getHitObjectList().get(0).getSourceObject();
		System.out.println(ctro);
		ctro.getTo();
		System.out.println(ctro);
		ctro.getTo().getField1();
		System.out.println(ctro);
		
	}
	
	/*************************************************************************************************************************/
	//@Test
	public void test() throws ReflectiveOperationException, QuickElasticException, IOException{
		
		SearchInElasctic es=new SearchInElasctic(
				JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(this.getClass(),
						"query/application_match_all_config.json")).getAsJsonObject());
		TestObject to=new TestObject();
		to.setField1("2");
		JsonObject jsonObject=JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(this.getClass(),
				"query/application_match_all.json")).getAsJsonObject();
		
		ApplicationReturn ro=es.search(jsonObject, to);
		System.out.println(ro);
		
		ro.getAggrs().getFacets().get("test").getBuckets().get("us").setIsChecked(false);
		ro=es.search(jsonObject);
		
		System.out.println(jsonObject);
		System.out.println(ro);	
	}
	
	/**********************************************************************************************************************/
	@Test
	public void testWithBean() throws IOException{

	
		@SuppressWarnings( "resource" )
		ApplicationContext context	=	
					new FileSystemXmlApplicationContext(
							TestRessourcesLoader.getPathRessource(this.getClass(), 
								"BeansPing.xml"));
		SearchInElasctic es= (SearchInElasctic) context.getBean("search_in");
		TestObject to=new TestObject();
		to.setField1("2");
		JsonObject jsonObject	=
				JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(this.getClass(),
						"query/application_match_all.json")).getAsJsonObject();
		ApplicationReturn ro=es.search(jsonObject, to);
		System.out.println(jsonObject);
		System.out.println(ro);
		ro.getAggrs().getFacets().get("test").getBuckets().get("us").setIsChecked(false);
		ro=es.search(jsonObject);
		System.out.println(jsonObject);
		System.out.println(ro);	
	}
}

