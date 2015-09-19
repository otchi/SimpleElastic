package com.edifixio.amine.application;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.edifixio.amine.application.elasticResults.ElasticReturn;
import com.edifixio.amine.application.elasticResults.Source;
import com.edifixio.amine.config.JsonBooleanConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.utils.JsonPathTree;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ArrayBuilder.JsonArrayBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

public class SimpleResponseConfigUnit extends SimpleResponseMappingConfig{
	
	private static final String NAME="name";
	private static final String MAPPING="mapping";
	private static final String LAZY="lazy";
	/**************************************************************/
	private static final String QUERY="query";
	private static final String IDS="ids";
	private static final String VALUES="values";
	private static final String INCLUDE="include";
	private static final String SOURCE="_source";
	private static final String EXCLUDE="exclude";
	/**************************************************************/
	
	
	private JsonPathTree jsonPathTree;

	
	/************************************************************/
	public SimpleResponseConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	/*****************************************************************/
	public void putJsonInObject(JsonElement jsonElement, Object object,String sourceId,String index,String type) throws ReflectiveOperationException {
		
		String fieldName=((JsonStringConfig)this.mapConfig.get(NAME)).getValue();
		
		Method method=SimpleResponseMappingConfig.getSetter(object.getClass(), fieldName);
		String fieldGetterMethod="get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		Class<?> classOfField=object.getClass().getMethod(fieldGetterMethod).getReturnType();
		//System.out.println(classOfField+"///"+object+"//"+jsonObject);
		
		
		if ((this.mapConfig.containsKey(MAPPING))) {

			// System.out.println(method+"//--"+fieldName+"//--"+object);
			method.invoke(object, ((SimpleResponseMappingConfig) this.mapConfig.get(MAPPING))
					.getSourceObject(classOfField, jsonElement.getAsJsonObject(), sourceId, index, type));
			return;

		}
		
//			System.out.println(method);
//			System.out.println(jsonElement);
//			System.out.println(object);
		super.putField(method, fieldName, jsonElement, object, null, sourceId, index, type);
		
	}
	/************************************************************************************************************/
	public JsonPathTree getLazyTree(String name,String parentPath){
		
		JsonPathTree jsonPathTree;
		Boolean isLazy=(this.mapConfig.containsKey(LAZY)	&&	((JsonBooleanConfig)this.mapConfig.get(LAZY)).getValue());
		
		jsonPathTree=new JsonPathTree(name, parentPath, isLazy);
		
		if((this.mapConfig.containsKey(MAPPING))){
			((SimpleResponseMappingConfig)this.mapConfig.get(MAPPING)).lazyTreeProcess(jsonPathTree);
		}
		this.jsonPathTree=jsonPathTree;
		return this.jsonPathTree;
	}
	
	/**
	 * @throws IOException 
	 * @throws ReflectiveOperationException ************************************************************************************************************/
	public void lazyModeLoading(Object object,String sourceId,String index,String type,JestClient jestClient) throws IOException, ReflectiveOperationException{
		
		String element=this.jsonPathTree.getElement();
		String name=this.jsonPathTree.getName();
	
		String selector=(!element.equals("") && !name.equals(""))? element+"."+name : element+name;
		String jsonSelector=selector.replaceAll("\\.", "::");
		Collection<String> result=new LinkedList<String>();
		SimpleRootConfig.putElementToExclude(jsonPathTree, result,false);
		
		IBuildJsonArray<IRootJsonBuilder> jsa = JsonArrayBuilder.init()
				.begin();
		Iterator<String> resultIter=result.iterator();
		
		while(resultIter.hasNext()){
			jsa.putValue(resultIter.next());
		}

		
		/************************************************************/
		JsonObject query=JsonObjectBuilder.init()
							.begin()
								
								.putObject(QUERY)
								.begin()
									.putObject(IDS)
									.begin()
										.putArray(VALUES)
										.begin()
											.putValue(sourceId)
										.end()
									.end()	
								.end()
								
								.putObject(SOURCE)
								.begin()
									.putArray(INCLUDE)
									.begin()
										.putValue(selector)
									.end()
									.putElement(EXCLUDE, jsa.end().getJsonElement())
								.end()
								
							.end()
							.getJsonElement()
							.getAsJsonObject();
		/*************************************************************************************/
		System.out.println("---> final query : "+query);
		Builder builder = new Search.Builder(query.toString());
		builder.addIndex(index);
		builder.addType(type);
	
		//System.out.println(jestClient.execute(builder.build()));
		
		JestResult jestResult=jestClient.execute(builder.build());
		/*************************************************************************************/
		if(jestResult.getJsonObject().has("error")){
			System.out.println("Exception ~ syntax elastic error : verifie your query");
			return;
		}
		/***************************************************************************************/
		ElasticReturn elasticReturn = ElasticReturn.getElasticReturn(jestResult.getJsonObject());
		List<Source> sources=elasticReturn.getSetSources().getSources();
		//System.out.println(sources);
		/***************************************************************************************/
		if(sources==null || sources.isEmpty()){
			System.out.println("exception 151 ~ no result"+ this.getClass());
			return;
		}
		
		JsonElement je=JsonHandleUtil.seletor(jsonSelector, sources.get(0).getSources());
		//System.out.println(je);
		this.putJsonInObject(je, object, sourceId, index, type);
	}

	/********************************************************************************************/
	@Override
	public String toString() {
		return "SimpleResponseConfigUnit [jsonPathTree=" + jsonPathTree + ", mapConfig=" + mapConfig + "]";
	}
	/**************************************************************************************************/
	public String getName(){
		return ((SimpleJsonStringConfig)mapConfig.get(NAME)).getValue();
	}
	/*************************************************************************************************/
	public boolean getIsLazy(){
		return (this.mapConfig.containsKey(LAZY)	&&	((JsonBooleanConfig)this.mapConfig.get(LAZY)).getValue());
	}

}
