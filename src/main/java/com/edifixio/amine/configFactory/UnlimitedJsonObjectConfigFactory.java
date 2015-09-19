package com.edifixio.amine.configFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class UnlimitedJsonObjectConfigFactory extends JsonObjectConfigFactory {

	private JsonElementConfigFactory jConfigFactory[] = new JsonElementConfigFactory[3];

	/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild,
			JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(classToFactory, jsonPrimitiveConfigFactory);
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
		this.jConfigFactory[1] = jsonObjectConfigFactoryChild;
		this.jConfigFactory[2] = jsonPrimitiveConfigFactoryChild;
	}

	/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[2] = jsonPrimitiveConfigFactoryChild;
		// TODO Auto-generated constructor stub
	}

	/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
	}

	/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonObjectConfigFactory jsonObjectConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[1] = jsonObjectConfigFactoryChild;
	}
	
	
	public void addRecursiveChild(JsonElementConfigFactory jConfigFactory){
		if(jConfigFactory.getClass().equals(JsonArrayConfigFactory.class))
			this.jConfigFactory[0]=jConfigFactory;
		else
			if(jConfigFactory.getClass().equals(JsonObjectConfigFactory.class))
				this.jConfigFactory[1]=jConfigFactory;
			else
				if(jConfigFactory.getClass().equals(JsonPrimitiveConfigFactory.class))
					this.jConfigFactory[2]=jConfigFactory;
			
	}
	/******************************************************************************************/

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException, QuickElasticException {
		if (!jsonElement.isJsonObject())
			return super.getPrimitiveConfig(jsonElement);
		/*****************************************************************/
		Iterator<Entry<String, JsonElement>> jsonObjectIterator = jsonElement.getAsJsonObject().entrySet().iterator();
		Entry<String, JsonElement> jse;
		byte index = -1;
		Map<String, JsonElementConfig> mapConfig = new HashMap<String, JsonElementConfig>();

		while (jsonObjectIterator.hasNext()) {
			jse = jsonObjectIterator.next();
			index = (jse.getValue().isJsonArray()) ? (this.jConfigFactory[0] != null) ? (byte) 0 : (byte) -1
					: (jse.getValue().isJsonObject()) ? (this.jConfigFactory[1] != null) ? (byte) 1 : (byte) -2
							: (jse.getValue().isJsonPrimitive())
									? (this.jConfigFactory[2] != null) ? (byte) 2 : (byte) -3 : (byte) -4;

			if (index >= 0) {
				mapConfig.put(jse.getKey(), this.jConfigFactory[index].getJsonElementConfig(jse.getValue()));
				continue;
			} 
			if (index == -1)
				throw new QuickElasticException("json array not supported as child :"+jse.getKey());
			if (index == -2)
				throw new QuickElasticException("json object not supported as child "+jse.getKey());
			if(index==-3) {
					
				if(this.jConfigFactory[0]!=null&&((JsonCompoundConfigFactory)this.jConfigFactory[0]).isPremitive()){
					mapConfig.put(jse.getKey(),this.jConfigFactory[0].getJsonElementConfig(jse.getValue()));
					continue;
				}
					
				if(this.jConfigFactory[1]!=null&&((JsonCompoundConfigFactory)this.jConfigFactory[1]).isPremitive()){
					mapConfig.put(jse.getKey(),this.jConfigFactory[1].getJsonElementConfig(jse.getValue()));
					continue;
				}
				throw new QuickElasticException("json premitive not supported as child : "+jse.getKey());
			}
				if (index == -4)
					throw new QuickElasticException("json null not supported as child :" +jse.getKey());
				throw new QuickElasticException("undefined exception provoqued by UnlimitedJsonObjectConfigFactory :"+jse.getKey());
			
		}
		/*********************************************************************************************/
		return classToFactory.getConstructor(Map.class).newInstance(mapConfig);
	}

	/*public JsonElementConfigFactory duplicate() {
		// TODO Auto-generated method stub
		return new UnlimitedJsonObjectConfigFactory(this.classToFactory,super.jpcf,
				(JsonArrayConfigFactory)jConfigFactory[0].duplicate(),
				(JsonObjectConfigFactory)jConfigFactory[1].duplicate(),
				(JsonPrimitiveConfigFactory)jConfigFactory[2].duplicate());
	}*/
}
