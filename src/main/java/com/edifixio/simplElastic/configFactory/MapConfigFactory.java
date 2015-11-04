package com.edifixio.simplElastic.configFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class MapConfigFactory extends AbstractMapConfigFactory {

	private ElementConfigFactory jConfigFactory[] = new ElementConfigFactory[3];

	/*********************************************************************************************/
	public MapConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactory,
			ArrayConfigFactory jsonArrayConfigFactoryChild,
			AbstractMapConfigFactory jsonObjectConfigFactoryChild,
			PrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(classToFactory, jsonPrimitiveConfigFactory);
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
		this.jConfigFactory[1] = jsonObjectConfigFactoryChild;
		this.jConfigFactory[2] = jsonPrimitiveConfigFactoryChild;
	}

	/*********************************************************************************************/
	public MapConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[2] = jsonPrimitiveConfigFactoryChild;
		// TODO Auto-generated constructor stub
	}

	/*********************************************************************************************/
	public MapConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			ArrayConfigFactory jsonArrayConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
	}

	/*********************************************************************************************/
	public MapConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			AbstractMapConfigFactory jsonObjectConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[1] = jsonObjectConfigFactoryChild;
	}
	
	
	public void addRecursiveChild(ElementConfigFactory jConfigFactory){
		if(jConfigFactory.getClass().equals(ArrayConfigFactory.class))
			this.jConfigFactory[0]=jConfigFactory;
		else
			if(jConfigFactory.getClass().equals(AbstractMapConfigFactory.class))
				this.jConfigFactory[1]=jConfigFactory;
			else
				if(jConfigFactory.getClass().equals(PrimitiveConfigFactory.class))
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
					
				if(this.jConfigFactory[0]!=null&&((CompoundConfigFactory)this.jConfigFactory[0]).isPremitive()){
					mapConfig.put(jse.getKey(),this.jConfigFactory[0].getJsonElementConfig(jse.getValue()));
					continue;
				}
					
				if(this.jConfigFactory[1]!=null&&((CompoundConfigFactory)this.jConfigFactory[1]).isPremitive()){
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
