package com.edifixio.simplElastic.configFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class DeclaredMapConfigFactory extends AbstractMapConfigFactory {

	private Map<String, ElementConfigFactoryState> childFactories=
								new HashMap<String, ElementConfigFactoryState>();
	
	/*********************************************************************************************/
	public DeclaredMapConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactory,// not used for the moment (in unlimited always)  
			Map<String, ElementConfigFactory> childFactories) {
		
		super(classToFactory, jsonPrimitiveConfigFactory);
		putElement(childFactories);
	}

	/*********************************************************************************************/
	public DeclaredMapConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			Map<String, ElementConfigFactory> childFactories) {
		
		super(classToFactory);
		putElement(childFactories);
	}
	
	
	
	/***********************************************************************************************/
	public void addRecursiveChild(String key,ElementConfigFactory childFactory){
		if(this.childFactories.containsKey(key)){
			System.out.println("DeclaredJsonObjectConfigFactory duplicate :"+key+" element in recursive insertion");
		}
			
			
		if(!childFactory.getClass().equals(ElementConfigFactoryState.class))
			this.childFactories.put(key,(ElementConfigFactoryState) new ElementConfigFactoryState(childFactory));
		else
			this.childFactories.put(key,(ElementConfigFactoryState) childFactory);
	}
	
	/*public void addRecursiveChilds(Map<String, JsonElementConfigFactory> childFactories){
		putElement(childFactories);
	}
	*/
	/*********************************************************************************************/
	private void putElement(Map<String, ElementConfigFactory> childs){
		Iterator<Entry<String, ElementConfigFactory>> childsIter= childs.entrySet().iterator();
		Entry<String, ElementConfigFactory> entry;
		while(childsIter.hasNext()){
			entry=childsIter.next();
			if(!entry.getValue().getClass().equals(ElementConfigFactoryState.class)){
				this.childFactories.put(entry.getKey(), 
						(ElementConfigFactoryState) new ElementConfigFactoryState(entry.getValue()));
			}else{
				this.childFactories.put(entry.getKey(), (ElementConfigFactoryState)entry.getValue());
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
				ElementConfigFactoryState jecfs = childFactories.get(entry.getKey());
				mapConfig.put(entry.getKey(), jecfs.getJecf().getJsonElementConfig(entry.getValue()));
				
			} else {
				throw new QuickElasticException(
						"the element :( " + entry.getKey() + " )can't be an element of this object");
			}
		}

		/******************************************************/
		Iterator<Entry<String,ElementConfigFactoryState>> jsefsIter = childFactories.entrySet().iterator();

		while (jsefsIter.hasNext()) {
			Entry<String,ElementConfigFactoryState> element = jsefsIter.next();
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
