package com.edifixio.simplElastic.configFactory;

import java.util.Iterator;

import com.edifixio.simplElastic.config.JsonArrayConfig;
import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class JsonArrayConfigFactory extends JsonCompoundConfigFactory {

	private Class<? extends JsonArrayConfig> classToFactory;
	
	// in array we must indicate procedure if the term is array or object or primary 
	// if we ignore one of this  or chose a constructor who ignore its this
	// kind of element become prohibit in this array
	private JsonElementConfigFactory jConfigFactory[]=new JsonElementConfigFactory[3];
	
	/********************************************************************************************************/
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild, 
			JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(jsonPrimitiveConfigFactory);
		this.jConfigFactory[0]= (JsonElementConfigFactory) jsonArrayConfigFactoryChild;
		this.jConfigFactory[1]= (JsonElementConfigFactory) jsonObjectConfigFactoryChild;
		this.jConfigFactory[2] =jsonPrimitiveConfigFactoryChild;
		this.classToFactory = classToFactory;
	}
	
	/***********************************************************************************************************/
	public void addRecursiveChilds(JsonElementConfigFactory jConfigFactory){
		if(jConfigFactory.getClass().equals(JsonArrayConfigFactory.class))
			this.jConfigFactory[0]=jConfigFactory;
		else
			if(jConfigFactory.getClass().equals(JsonObjectConfigFactory.class))
				this.jConfigFactory[1]=jConfigFactory;
			else
				if(jConfigFactory.getClass().equals(JsonPrimitiveConfigFactory.class))
					this.jConfigFactory[2]=jConfigFactory;
			
	}
	/*********************************************************************************************************/	
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild) {
		super();
		this.classToFactory = classToFactory;
		this.jConfigFactory[0] = (JsonElementConfigFactory) jsonArrayConfigFactoryChild;
	}
	
	/********************************************************************************************************/
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonObjectConfigFactory jsonObjectConfigFactoryChild) {
		super();
		this.classToFactory = classToFactory;
		this.jConfigFactory[1]= (JsonElementConfigFactory) jsonObjectConfigFactoryChild;
	}
	
	/********************************************************************************************************/
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {
		super();
		this.classToFactory = classToFactory;
		this.jConfigFactory[2] =jsonPrimitiveConfigFactoryChild;
	}

	/*********************************************************************************************************/

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws 
	ReflectiveOperationException, QuickElasticException {
		
		if (!jsonElement.isJsonArray()) {
			if(jsonElement.isJsonPrimitive()&&jpcf!=null)
				return jpcf
						.getJsonElementConfig(jsonElement);
			else { System.out.println("JsonArrayConfigFactory~exception 66");return null;}
		}

		JsonArrayConfig jsonArrayConfigResult = this.classToFactory	.getConstructor()
																	.newInstance();
		Iterator<JsonElement> jsonArrayIterator = jsonElement	.getAsJsonArray()
																.iterator();
		JsonElement jse;
		byte index=-1;
		
		/***********************************************************/
		while (jsonArrayIterator.hasNext()) {
			jse = jsonArrayIterator.next();
			
			index=(jse.isJsonArray())?
				(this.jConfigFactory[0] != null)? (byte)0 :(byte) -1 
								 	 : (jse.isJsonObject())? 
										(this.jConfigFactory[1] != null)? (byte)1 : (byte)-2
										 			   :(jse.isJsonPrimitive())?
										 					  (this.jConfigFactory[2] != null)?(byte) 2 :(byte)-3 :(byte)-4;
			if(index>=0){
				jsonArrayConfigResult.addJsonElementConfig(this	.jConfigFactory[index]
															.getJsonElementConfig(jse));
				continue;
			} 
				
			if(index==-1) throw new QuickElasticException("json array not supported as child");
			if(index==-2) throw new QuickElasticException("json object not supported as child");
			if(index==-3) {	
					if(this.jConfigFactory[0]!=null&&((JsonCompoundConfigFactory)this.jConfigFactory[0]).isPremitive()){
						jsonArrayConfigResult.addJsonElementConfig(this.jConfigFactory[0].getJsonElementConfig(jse));
						continue;
					}
					
					if(this.jConfigFactory[1]!=null&&((JsonCompoundConfigFactory)this.jConfigFactory[1]).isPremitive()){
						jsonArrayConfigResult.addJsonElementConfig(this.jConfigFactory[1].getJsonElementConfig(jse));
						continue;
					}
					
					throw new QuickElasticException("json premitive not supported as child");
					
			}
			if (index == -4)
				throw new QuickElasticException("json null not supported as child");
				
			throw new QuickElasticException("undefined exception provoqued by UnlimitedJsonObjectConfigFactory");
		}

		
		return jsonArrayConfigResult;
	}



	/*public JsonElementConfigFactory duplicate() {
		// TODO Auto-generated method stub
		return new JsonArrayConfigFactory(this.classToFactory,super.jpcf,
				(JsonArrayConfigFactory)jConfigFactory[0].duplicate(),
				(JsonObjectConfigFactory)jConfigFactory[1].duplicate(),
				(JsonPrimitiveConfigFactory)jConfigFactory[2].duplicate());
	}*/

}
