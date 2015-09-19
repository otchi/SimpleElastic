package test.com.edifixio.amine.applicatif;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.application.ResultObject;
import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.exception.QuickElasticException;
import com.edifixio.amine.object.ComplexTestResponseObject;
import com.edifixio.amine.object.TestObject;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;

public class ElasticSearchTest {
	private static final String SPRING_CONFIG="BeansApplication.xml";
	private static final String MAIN_CONFIG_FACTORY="main_config";
	private final static   DeclaredJsonObjectConfigFactory  META_APPLI_CONFIG=
			( DeclaredJsonObjectConfigFactory )
			new FileSystemXmlApplicationContext(
					TestRessourcesLoader.getPathRessource(ElasticSearchTest.class,SPRING_CONFIG))
										.getBean(MAIN_CONFIG_FACTORY);
	
	/*********************************************************************************************************************/
	@Test
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
		System.out.println(application.getResultObject());
		System.out.println("+!+!+!+!+!+!+!+!+!+!>"+((TestObject)application.getResultObject().get(0)).getField3());
		System.out.println(application.getResultObject());
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getFacets());
	
		/***********************************************************************************************/
		application.getBasedFacets().get("test").getBuckets().get("europe").setIsCheked(false);
		
		System.out.println(application.getResultObject());
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
	@Test
	public void complexLazyModeTest() throws ReflectiveOperationException, QuickElasticException, IOException{
		SearchInElasctic es=new SearchInElasctic(
						JsonHandleUtil.jsonFile(
								TestRessourcesLoader.loadRessource(
										this.getClass(),"query/ids_complex_app_config.json"))
													.getAsJsonObject());
		
		JsonObject jsonObject=JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(
				this.getClass(),"query/ids_app_query.json")).getAsJsonObject();
		ResultObject ro=es.search(jsonObject);
		System.out.println(ro);
		ro=es.search(jsonObject);
		System.out.println(jsonObject);
		System.out.println(ro);	
		ComplexTestResponseObject ctro=(ComplexTestResponseObject) ro.getResultList().get(0);
		System.out.println(ctro);
		ctro.getTo();
		System.out.println(ctro);
		ctro.getTo().getField1();
		System.out.println(ctro);
		
	}
	
	/*************************************************************************************************************************/
	@Test
	public void test() throws ReflectiveOperationException, QuickElasticException, IOException{
		
		SearchInElasctic es=new SearchInElasctic(
				JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(this.getClass(),
						"query/application_match_all_config.json")).getAsJsonObject());
		TestObject to=new TestObject();
		to.setField1("2");
		JsonObject jsonObject=JsonHandleUtil.jsonFile(TestRessourcesLoader.loadRessource(this.getClass(),
				"query/application_match_all.json")).getAsJsonObject();
		
		ResultObject ro=es.search(jsonObject, to);
		System.out.println(ro);
		
		ro.getFacets().get("test").getBuckets().get("us").setIsCheked(false);
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
		ResultObject ro=es.search(jsonObject, to);
		System.out.println(jsonObject);
		System.out.println(ro);
		ro.getFacets().get("test").getBuckets().get("us").setIsCheked(false);
		ro=es.search(jsonObject);
		System.out.println(jsonObject);
		System.out.println(ro);	
	}
}

