package com.edifixio.amine.application;

import com.edifixio.amine.config.JsonBooleanConfig;

public class SimpleJsonBooleanConfig extends JsonBooleanConfig{

	public SimpleJsonBooleanConfig(Boolean value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SimpleJsonBooleanConfig [value=" + value + "]";
	}
	
	

}
