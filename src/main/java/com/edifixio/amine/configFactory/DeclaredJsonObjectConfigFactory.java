package com.edifixio.amine.configFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class DeclaredJsonObjectConfigFactory extends JsonObjectConfigFactory {

	private Map<String, JsonElementConfigFactoryState> childFactories=
								new HashMap<String, JsonElementConfigFactoryState>();
	
	/*********************************************************************************************/
	public DeclaredJsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory,// not used for the moment (in unlimited always)  
			Map<String, JsonElementConfigFactory> childFactories) {
		
		super(classToFactory, jsonPrimitiveConfigFactory);
		putElement(childFactories);
	}

	/*********************************************************************************************/
	public DeclaredJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			Map<String, JsonElementConfigFactory> childFactories) {
		
		super(classToFactory);
		putElement(childFactories);
	}
	
	
	
	/***********************************************************************************************/
	public void addRecursiveChild(String key,JsonElementConfigFactory childFactory){
		if(this.childFactories.containsKey(key)){
			System.out.println("DeclaredJsonObjectConfigFactory duplicate :"+key+" element in recursive insertion");
		}
			
			
		if(!childFactory.getClass().equals(JsonElementConfigFactoryState.class))
			this.childFactories.put(key,(JsonElementConfigFactoryState) new JsonElementConfigFactoryState(childFactory));
		else
			this.childFactories.put(key,(JsonElementConfigFactoryState) childFactory);
	}
	
	/*public void addRecursiveChilds(Map<String, JsonElementConfigFactory> childFactories){
		putElement(childFactories);
	}
	*/
	/*********************************************************************************************/
	private void putElement(Map<String, JsonElementConfigFactory> childs){
		Iterator<Entry<String, JsonElementConfigFactory>> childsIter= childs.entrySet().iterator();
		Entry<String, JsonElementConfigFactory> entry;
		while(childsIter.hasNext()){
			entry=childsIter.next();
			if(!entry.getValue().getClass().equals(JsonElementConfigFactoryState.class)){
				this.childFactories.put(entry.getKey(), 
						(JsonElementConfigFactoryState) new JsonElementConfigFactoryState(entry.getValue()));
			}else{
				this.childFactories.put(entry.getKey(), (JsonElementConfigFactoryState)entry.getValue());
			}
		}	
	}
	/*********************************************************************************************/

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException,QuickElasticException {

		/**********************************************/
		if (!jsonElement.isJsonObject())
			return  super.getPrimitiveConfig(jsonElement);
		/************************************************/

		Map<String, JsonElementConfig> mapConfig = new HashMap<String, JsonElementConfig>();
		Iterator<Entry<String, JsonElement>> jsonObjectIterator;
		jsonObjectIterator = jsonElement.getAsJsonObject().entrySet().iterator();
		Entry<String, JsonElement> entry;

		while (jsonObjectIterator.hasNext()) {

			entry = jsonObjectIterator.next();

			if (childFactories.containsKey(entry.getKey())) {
				JsonElementConfigFactoryState jecfs = childFactories.get(entry.getKey());
				mapConfig.put(entry.getKey(), jecfs.getJecf().getJsonElementConfig(entry.getValue()));
				
			} else {
				throw new QuickElasticException(
						"the element :( " + entry.getKey() + " )can't be an element of this object");
			}
		}

		/******************************************************/
		Iterator<Entry<String,JsonElementConfigFactoryState>> jsefsIter = childFactories.entrySet().iterator();

		while (jsefsIter.hasNext()) {
			Entry<String,JsonElementConfigFactoryState> element = jsefsIter.next();
			if (element.getValue().getIsRequired()&& (!mapConfig.containsKey(element.getKey()))) {
				throw new QuickElasticException(" it remains element : ("+element.getKey()+") required and not put in configuration");
			}
		}
		/******************************************************/

		return classToFactory.getConstructor(Map.class).newInstance(mapConfig);
	}

	
	
	
	

	
	
	/*public JsonElementConfigFactory duplicate() {
		Map<String, JsonElementConfigFactoryState> map=new HashMap<String, JsonElementConfigFactoryState>();
		Iterator<Entry<String, JsonElementConfigFactoryState>> configIter=this.childFactories.entrySet().iterator();
		
		while(configIter.hasNext()){
			Entry<String, JsonElementConfigFactoryState> entryToDuplicate=configIter.next();
			JsonElementConfigFactoryState elementToDuplicate=entryToDuplicate.getValue();
			map.put(entryToDuplicate.getKey(),(JsonElementConfigFactoryState) elementToDuplicate.duplicate());
		}
		
		return jpcf;
	}*/

	
	
}
