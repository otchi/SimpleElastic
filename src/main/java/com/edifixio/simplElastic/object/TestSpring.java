package com.edifixio.simplElastic.object;

import com.edifixio.simplElastic.config.JsonStringConfig;

public class TestSpring {
	
	private String message;
	private Class<? extends JsonStringConfig> c;

	public Class<? extends JsonStringConfig> getC() {
		return c;
	}

	public void setC(Class<? extends JsonStringConfig> c) {
		this.c = c;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void start(TestSpring ts){
		System.out.println("start : "+ts.toString());
	}

	@Override
	public String toString() {
		return "TestSpring [message=" + message + ", c=" + c + "]";
	}
	
	
	
	

}
