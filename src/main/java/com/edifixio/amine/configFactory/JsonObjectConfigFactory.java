package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public abstract class JsonObjectConfigFactory extends JsonCompoundConfigFactory {

	protected Class<? extends JsonObjectConfig> classToFactory;

	/***************************************************************************************************/
	public JsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory) {
		super();
		this.classToFactory = classToFactory;
	}

	public JsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory) {

		super(jsonPrimitiveConfigFactory);
		this.classToFactory = classToFactory;
	}

	public JsonElementConfig getPrimitiveConfig(JsonElement jsonElement)
			throws ReflectiveOperationException, QuickElasticException {

		if (jsonElement.isJsonPrimitive()) {
			if (jpcf != null) {
				return jpcf.getJsonElementConfig(jsonElement);
			} else {
				throw new QuickElasticException(
						"the premitive type is not supproted (is null) and the json element is premitive !");
			}
		} else {
			throw new QuickElasticException("uncompatible jsonType and Config Object must be object or primitive ! :"+jsonElement);
		}
	}
	
	

}
