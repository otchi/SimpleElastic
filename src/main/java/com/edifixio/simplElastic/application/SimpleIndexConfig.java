package com.edifixio.simplElastic.application;

import java.util.Map;

import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.config.JsonObjectConfig;

import io.searchbox.core.Search.Builder;

public class SimpleIndexConfig extends JsonObjectConfig {
	private static final String INDEXES_NAMES="names";
	private static final String TYPES_NAMES="types";
	
	public SimpleIndexConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	public void process(Builder builder){
		SimpleTypeIndexConfig indexesJEC=
				(SimpleTypeIndexConfig) mapConfig.get(INDEXES_NAMES);
		SimpleTypeIndexConfig typesJEC=
				(SimpleTypeIndexConfig) mapConfig.get(TYPES_NAMES);
		
		if(indexesJEC!=null){
			builder.addIndex(indexesJEC.getStringListConfigs());
		}
		if(typesJEC!=null){
			builder.addType(typesJEC.getStringListConfigs());
		}
	}

	@Override
	public String toString() {
		return "SimpleIndexConfig [mapConfig=" + mapConfig + "]";
	}
	
	

}  
