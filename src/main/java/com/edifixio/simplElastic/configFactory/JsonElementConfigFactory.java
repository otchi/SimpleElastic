package com.edifixio.simplElastic.configFactory;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.exception.QuickElasticException;
import com.google.gson.JsonElement;

public interface JsonElementConfigFactory {

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) 
			throws ReflectiveOperationException, QuickElasticException;

	

}
