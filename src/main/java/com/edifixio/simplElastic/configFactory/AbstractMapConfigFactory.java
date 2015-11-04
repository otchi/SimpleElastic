package com.edifixio.simplElastic.configFactory;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;

public abstract class AbstractMapConfigFactory extends CompoundConfigFactory {

	protected Class<? extends JsonObjectConfig> classToFactory;

	/***************************************************************************************************/
	public AbstractMapConfigFactory(Class<? extends JsonObjectConfig> classToFactory) {
		super();
		this.classToFactory = classToFactory;
	}

	public AbstractMapConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			PrimitiveConfigFactory jsonPrimitiveConfigFactory) {

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
