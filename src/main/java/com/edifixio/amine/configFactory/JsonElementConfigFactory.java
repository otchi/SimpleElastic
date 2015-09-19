package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public interface JsonElementConfigFactory {

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) 
			throws ReflectiveOperationException, QuickElasticException;

	

}
