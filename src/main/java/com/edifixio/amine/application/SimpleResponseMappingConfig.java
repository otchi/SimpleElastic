package com.edifixio.amine.application;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.Hit;
import com.edifixio.amine.application.elasticResults.HitObject;
import com.edifixio.amine.application.elasticResults.Hits;
import com.edifixio.amine.application.elasticResults.MetaSource;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.utils.ConfigFactoryUtiles;
import com.edifixio.amine.utils.ElasticObjectProxyInterceptor;
import com.edifixio.amine.utils.EntryImp;
import com.edifixio.amine.utils.JsonPathTree;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class SimpleResponseMappingConfig extends JsonObjectConfig{
	
	private Map<String, SimpleResponseConfigUnit> lazyModeUnit=new HashMap<String, SimpleResponseConfigUnit>();
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	// solution to lazy mode is to put proxy in this class , with a map of units who is lazy and call lazy load from heres 
	public SimpleResponseMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	/******************************************************************************************************************/
	public JsonPathTree getLazyTree(){
		JsonPathTree jsonPathTree=new JsonPathTree("", "", false);
		this.lazyTreeProcess(jsonPathTree);
		return jsonPathTree;
	}
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	protected void lazyTreeProcess(JsonPathTree jsonPathTree){
		String oldParentPath	=	jsonPathTree.getElement();
		String name	=	jsonPathTree.getName();
		String prentPath	=
				(!oldParentPath.equals("") && !name.equals("") )	
				?	oldParentPath+"."+name
				:	oldParentPath+name;
		
		Iterator<Entry<String, JsonElementConfig>> mapConfigIter=mapConfig.entrySet().iterator();
		Entry<String, JsonElementConfig> entry;
		/*****************************************/
		while(mapConfigIter.hasNext()){
			entry=mapConfigIter.next();
			JsonElementConfig jsc=entry.getValue();
			if(jsc.isObjectConfig()){
		
				jsonPathTree.addChild(
						(((SimpleResponseConfigUnit)jsc).getLazyTree(entry.getKey(),prentPath)));
			}
		}	
	}
	
	
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public List<HitObject> getHitObject(Class<?> responseClass,Hits hits) throws ReflectiveOperationException{
		
		List<HitObject> responseList=new LinkedList<HitObject>();
		Iterator<Hit> hitsIter=hits.getHits().iterator();
	
		while(hitsIter.hasNext()){
			Hit hit=hitsIter.next();
			MetaSource ms=hit.getMetasSources();
			responseList.add(new HitObject(ms,putJsonInObject(hit.getSources(), responseClass,ms.getId(),ms.getIndex(),ms.getType())));
		}

		return responseList;
	}
	/*****************************************************************************************************************************/
	/*****************************************************************************************************************************/
	public Object getHitObject(Class<?> responseClass,JsonObject jsonObject,String sourceId,String index,String type) throws ReflectiveOperationException{
		
	
		//this code is executed several times for same result
		//System.out.println("--->>"+mapMethod+"////"+responseClass);
		//System.out.println(jsonObject+"//"+responseClass+"//"+mapMethod);
		return putJsonInObject(jsonObject,  responseClass, sourceId, index, type);
		
	}
	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/
	public Object putJsonInObject(JsonObject jsonObject, Class<?>  responseClass,
		String sourceId,String index,String type) throws ReflectiveOperationException {
		putLazyUnits();

		Object resultObj = ElasticObjectProxyInterceptor.createProxy(responseClass.newInstance(),lazyModeUnit,sourceId,index,type);
		Map<String,Entry<String,Method>> mapMethod= getSetters(resultObj.getClass());
		
		/*********/
		
		Iterator<Entry<String, JsonElement>> jsonSourceIter;
		Entry<String, JsonElement> entry;
		Entry<String, Method> entryMethod;
		
		jsonSourceIter = jsonObject.entrySet().iterator();
		/***********************************/
		while (jsonSourceIter.hasNext()) {

			entry = jsonSourceIter.next();

			if (!mapMethod.containsKey(entry.getKey())) {
				System.out.println("exception 57 ~field :" + entry.getKey() + " --> not mapped");
				continue;
			}
			
			entryMethod = mapMethod.get(entry.getKey());
		
			if(entryMethod!=null){
				putField(entryMethod.getValue(), entryMethod.getKey(), entry.getValue(), resultObj,null,sourceId,index, type);
			}else{
				putField( null, null, entry.getValue(),resultObj,entry.getKey(),sourceId,index, type);
			}
		}

		return resultObj;
	}
	
	/*****************************************************************************************************************/
	/****************************************************************************************************************/
	public void putLazyUnits(){
//		System.out.println("--------------------- begin put lazy unit------------------------------------------------");
		Iterator<JsonElementConfig> elementIter=this.mapConfig.values().iterator();
		JsonElementConfig entryElement;
		while(elementIter.hasNext()){
			entryElement=elementIter.next();
			
			if(entryElement.getClass().equals(SimpleResponseConfigUnit.class)){
//				System.out.println(entryElement+"-+-+-"+entryElement.getClass());
				SimpleResponseConfigUnit srcu=(SimpleResponseConfigUnit) entryElement;
				if(srcu.getIsLazy())
					this.lazyModeUnit.put(srcu.getName(), srcu);
			}
		}
//		System.out.println("---------------------- end put lazy unit ----------------------------------------------");
	}
	
	
	/****************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public Map<String,Entry<String,Method>> getSetters(Class<?>  responseClass)throws ReflectiveOperationException{
		//System.out.println("++"+mapConfig);
		Map<String,Entry<String,Method>> map=new HashMap<String, Map.Entry<String,Method>>();
		Iterator<Entry<String, JsonElementConfig>> confIter=mapConfig.entrySet().iterator();
		Entry<String, JsonElementConfig> entry;
		String  fieldname;
		JsonElementConfig value;
		
		/********************************************/
		while(confIter.hasNext()){
			entry=confIter.next();
			value=entry.getValue();
			
			/**********************************************************************************/
			if(!((value.isPremitiveConfig()&&((JsonPrimitiveConfig)value).isStringConfig()))){
				if(!value.isObjectConfig()){
					System.out.println("not supported");
					return null;
				}
				map.put(entry.getKey(), null);
				continue;
			}
			
			fieldname=((JsonStringConfig)value).getValue();
			map.put(entry.getKey(), new EntryImp<String, Method>(fieldname,getSetter(responseClass, fieldname)));
		}
		return map;
	}
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	public static  Method getSetter(Class<?>  responseClass,String fieldname) throws NoSuchMethodException, SecurityException, NoSuchFieldException{
		
		String fieldMethodeName=fieldname.substring(0, 1).toUpperCase()+fieldname.substring(1);
		return responseClass.getMethod("set"+fieldMethodeName, 
					responseClass.getMethod("get"+fieldMethodeName).getReturnType());
		
	} 
	
	/********************************************************************************************************************/
	/*******************************************************************************************************************/
	/********************************************************************************************************************/
	public void putField(Method method, String fieldName, JsonElement jsonElement, Object obj,String jsonField,
										String sourceId,String index,String type)
						throws ReflectiveOperationException {

		Class<?> objClass = obj.getClass();
	
		/****************************************************************************/
		if (!jsonElement.isJsonPrimitive() || fieldName==null) {
			
			if(jsonElement.isJsonArray()){
				System.out.println("SimpleResponseMappingConfig ~ 144 : not supported");// change here code to process
				// complex object
				return;
			}
			
			SimpleResponseConfigUnit srcu=(SimpleResponseConfigUnit)mapConfig.get(jsonField);
			if(!srcu.getIsLazy())
				srcu.putJsonInObject(jsonElement,obj,sourceId,index, type);
			
			
			return;
		}
		/*******************************************************************/
		String fieldMethodName=fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		String getterMethodName="get"+fieldMethodName;
		Method getterMethod=objClass.getMethod(getterMethodName);
		//System.out.println(objClass.getDeclaredMethod("get"++"////"+fieldName);
		if(getterMethod==null)return;
		Class<?> fieldClass = getterMethod.getReturnType();
		//System.out.println("--->"+fieldClass);
		
		JsonPrimitive jp = jsonElement.getAsJsonPrimitive();
		/*********************************************************************/
		if (ConfigFactoryUtiles.isOfType(fieldClass, int.class, Integer.class)) {
			if (!jp.isNumber()) {
				System.out.println("exception SimpleResponseMappingConfig ~ can't put" + jsonElement
						+ " in integer filed " + fieldName);
				return;
			}
			method.invoke(obj, jp.getAsInt());
			return;

		}
		/****************************************************************************/
		if (ConfigFactoryUtiles.isOfType(fieldClass, double.class, Double.class)) {
			if (!jp.isNumber()) {
				System.out.println("exception SimpleResponseMappingConfig ~ can't put" + jsonElement
						+ " in double filed " + fieldName);
				return;
			}
			method.invoke(obj, jp.getAsDouble());
			return;
		}
		/**********************************************************************************/
		if (fieldClass == String.class) {
			if (!jp.isString()) {
				System.out.println("exception SimpleResponseMappingConfig ~ can't put" + jsonElement
						+ " in String filed " + fieldName);
				return;
			}
			//System.out.println(method+"/////"+jp);
			//System.out.println(Arrays.asList(method.getParameterTypes()));
			//System.out.println(obj.getClass());
			
			method.invoke(obj, jp.getAsString());
			return;
		}
		/********************************************************************************/
		if (ConfigFactoryUtiles.isOfType(fieldClass, boolean.class, Boolean.class)) {
			if (!jp.isBoolean()) {
				System.out.println("exception");
				return;
			}
			method.invoke(obj, jp.getAsBoolean());
		}
	}
	/***********************************************************************************************************/
	@Override
	public String toString() {
		return "SimpleResponseMappingConfig [lazyModeUnit=" + lazyModeUnit + ", mapConfig=" + mapConfig + "]";
	}
	
	
	
}
