package com.edifixio.amine.application;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.ElasticReturn;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.application.elasticResults.HitObject;
import com.edifixio.amine.application.elasticResults.ReturnMetas;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.utils.ElasticClient;
import com.edifixio.amine.utils.JsonPathTree;
import com.edifixio.amine.utils.TreeNode;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;


public class SimpleRootConfig extends JsonObjectConfig {
	

	/**********************************************************/
	public static final String REQUEST = "_request";
	public static final String HOST = "_host";
	public static final String INDEX = "_indexes";
	public static final String RESPONSE = "_response";
	public static final String FACETS = "_facets";
	
	/******************************************************/
	public static final String POST_FILTER= "post_filter";
	public static final String AGGS="aggs";
	/**********************************************************/
	public static final String SOURCE="_source";
	public static final String EXCLUDE="exclude";
	/****************************************************/
	private List<HitObject> resultObject;
	private Map<String, FacetableAggr> basedFacets;
	private ElasticReturn elasticReturn;
	private Boolean isNewResultLock = false;
	public static JestClient CLIENT = null;
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	
	public SimpleRootConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	

	public Map<String, FacetableAggr> getBasedFacets() {
		return basedFacets;
	}



	public void setBasedFacets(Map<String, FacetableAggr> basedFacets) {
		this.basedFacets = basedFacets;
	}


	/*******************
	 * process with parameters
	 *******************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException 
	 * @throws IOException ************************************************************************************************************************/
	
	public void process(JsonObject query, Object request) throws ReflectiveOperationException, IOException  {

		
		/***************************** facets filter management ******************************************************************/
		if ((this.mapConfig.containsKey(FACETS))) {

			if (!(basedFacets == null )) {

				JsonObject facetFilters;

				facetFilters = ((SimpleFacetsConfig) this.mapConfig.get(FACETS)).process(query.getAsJsonObject(AGGS),
						this.basedFacets);

				facetFilters = ((SimpleFacetsConfig) this.mapConfig.get(FACETS)).process(query.getAsJsonObject(AGGS),
						basedFacets);

				if (!query.has(POST_FILTER)) {
					query.add(POST_FILTER, facetFilters);
				} else {
			
					Iterator<Entry<String, JsonElement>>  postFilterIter=query.getAsJsonObject(POST_FILTER).entrySet().iterator();
			
					IBuildJsonArray<IPutProprety<IPutProprety<IPutProprety<IRootJsonBuilder>>>> builder=
						JsonObjectBuilder.init()
							.begin()
								.putObject(POST_FILTER)
								.begin()
									.putObject(SimpleFacetsConfig.BOOL)
									.begin()
										.putArray(SimpleFacetsConfig.SHOULD)
										.begin();
					/***********************************/
					while (postFilterIter.hasNext()) {
						Entry<String, JsonElement> entry = postFilterIter.next();
						builder.putElement(JsonObjectBuilder.init()
													.begin()
														.putElement(entry.getKey(), entry.getValue())
													.end().getJsonElement());
					}

					builder.putElement(facetFilters);
					query.remove(POST_FILTER);
					query.add(POST_FILTER, builder.end().end().end().end().getJsonElement());
				}
			}

		}
		if(request!=null &&  mapConfig.containsKey(REQUEST)){
			((SimpleRequestConfig) mapConfig.get(REQUEST)).process(request, query);
		}
		System.out.println("---> final query : "+query);
		excludeLazyElement(query);
		exectute(query);
		isNewResultLock = true;
	
	}

	/***********************************************************************************/
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	public void excludeLazyElement(JsonObject query){
		Collection<String> result=new LinkedList<String>();
		JsonPathTree jpt=((SimpleResponseConfig)this.mapConfig.get(RESPONSE)).getLazyTree();
		putElementToExclude(jpt, result,false);
		IBuildJsonArray<IPutProprety<IRootJsonBuilder>> jso = JsonObjectBuilder.init()
																.begin()
																.putArray(EXCLUDE)
																	.begin();
		Iterator<String> resultIter=result.iterator();
		while(resultIter.hasNext()){
			jso.putValue(resultIter.next());
		}
		
		query.add(SOURCE, jso.end().end().getJsonElement());
		
	}
	/***********************************************************************************/
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	
	public static void putElementToExclude(JsonPathTree jpt,Collection<String> result,boolean exludeRoot){
		
		if(jpt.isLazy()&&exludeRoot){
			String element=jpt.getElement();
			String name=jpt.getName();
			String pathName=(!element.equals("") && !name.equals(""))	? 	element+"."+name 	: 	element+name;
			result.add(pathName);
			return;
		}
		
		Iterator<TreeNode<String>> childNodes=jpt.getChildsNode().iterator();
		
		while(childNodes.hasNext()){
				putElementToExclude((JsonPathTree) childNodes.next(), result,true);
		}
		
	}

	/********************
	 * execute request and put result
	 ************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws IOException ************************************************************************************************************************/
	
	protected final void exectute(JsonObject query) throws IOException  {

		Builder builder;
		JestResult jr;
		//System.out.println("------> exist ");
		if(CLIENT==null)
			CLIENT = ElasticClient.getElasticClient(((SimpleJsonStringConfig) mapConfig.get(HOST)).getValue())
										.getClient();
		
		builder = new Search.Builder(query.toString());
		((SimpleIndexConfig) mapConfig.get(INDEX)).process(builder);
		jr = CLIENT.execute(builder.build());
		//System.out.println(jr.getJsonObject());
		if(jr.getJsonObject().has("error")){
			System.out.println("Exception ~ syntax elastic error : verifie your query");
			return;
		}
//		System.out.println("----------------------------------------------------");
//		System.out.println(jr.getJsonObject());
//		System.out.println("----------------------------------------------------");
		elasticReturn = ElasticReturn.getElasticReturn(jr.getJsonObject());
//		System.out.println(elasticReturn);
//		System.out.println("----------------------------------------------------");

	}
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException ************************************************************************************************************************/
	
	public List<HitObject> getHitObjectList() throws ReflectiveOperationException  {
		refrechResult();
		return resultObject;
	}
	
	
	/*********************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException ********************************************************************************************************/
	
	public ReturnMetas getReturnMetas() throws ReflectiveOperationException{
		
		refrechResult();
		return this.elasticReturn.getReturnMetas();
	}
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException 
	  ************************************************************************************************************************/
	
	public Map<String, FacetableAggr> getFacets() throws ReflectiveOperationException   {

		this.refrechResult();
		/***************************************************************************************/
		if (elasticReturn.hasAggregations()){
			if(!this.mapConfig.containsKey(FACETS)){
				return new HashMap<String, FacetableAggr>();
			}
			this.basedFacets =((SimpleFacetsConfig)mapConfig.get(FACETS))
													 .getFacets( 
															elasticReturn.getAggregation()
																		 .getFacetableAggrs());
		}
		return basedFacets;
	}

	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException ************************************************************************************************************************/
	
	private final  void refrechResult() throws ReflectiveOperationException  {
		if (this.isNewResultLock) {
			resultObject = ((SimpleResponseConfig) mapConfig.get(RESPONSE))
					.getHitObjectList(elasticReturn.getSetSources());
			this.isNewResultLock = false;
		}
	}

	/***********************************************************************************/
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/

	@Override
	public String toString() {
		return "SimpleRootConfig [resultObject=" + resultObject + ", basedFacets=" + basedFacets + ", mapConfig="
				+ mapConfig + "]";
	}

	

	/***********************************************************************************/
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
}
