package com.edifixio.simplElastic.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.elasticResults.ApplicationReturn;
import com.edifixio.simplElastic.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonObject;
/**
 * 
 * @author amine
 * class to simplify API use
 */
public class SearchInElasctic {
	// Spring configuration file of application building
	private static final String SPRING_CONFIG=SearchInElasctic.class.getClassLoader()
																	.getResource("BeansApplication.xml")
																	.toString();
	//name of a root object  "root config"
	private static final String MAIN_CONFIG_FACTORY="main_config";
	/****************************************************************************************************************************/
	//loading of root object
	private final static   DeclaredJsonObjectConfigFactory  META_APPLI_CONFIG=
					( DeclaredJsonObjectConfigFactory )new FileSystemXmlApplicationContext(SPRING_CONFIG)
																			.getBean(MAIN_CONFIG_FACTORY);
	
	private SimpleRootConfig application;
	
	/*******************************************************************************************************************/
	/**
	 * 
	 * @param config : configuration of application
	 * @throws ReflectiveOperationException
	 * @throws QuickElasticException
	 */
	public SearchInElasctic(final JsonObject config) throws ReflectiveOperationException, QuickElasticException{
		application = (SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(config);
	}
	/**
	 * 
	 * @param fileConfig : application configuration file
	 * @throws ReflectiveOperationException
	 * @throws QuickElasticException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public SearchInElasctic(final File fileConfig) throws ReflectiveOperationException, QuickElasticException, FileNotFoundException, IOException{
		application = (SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(JsonHandleUtil.jsonFile(JsonObject.class, fileConfig));
	}


	
	/****************************************************************************************************************/
	/**
	 * 
	 * @param jsonQuery : JSON template
	 * @param requestObject : object to replace a template variables with values by using mapping in configuration  
	 * @return ApplicationReturn results which contain especially list of his and facets 
	 */
	public ApplicationReturn search(JsonObject jsonQuery,final Object requestObject){
			ApplicationReturn resultObject=null;
		try {
			this.application.process(jsonQuery, requestObject);
			//System.out.println(this.application.getResultObject());
			//System.out.println(this.application.getFacets());
			return new ApplicationReturn(this.application.getReturnMetas()
										,this.application.getHitObjectList()
										,this.application.getFacets());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultObject;
	}
	/******************************************************************************************************************************/
	/**
	 * 
	 * @param jsonQuery : JSON template
	 * @return results which contain especially list of his and facets 
	 */
	public ApplicationReturn search( JsonObject jsonQuery){
		return search(jsonQuery,null);
	}
}
