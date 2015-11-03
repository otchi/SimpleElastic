package com.edifixio.simplElastic.application.elasticResults;

import com.google.gson.JsonObject;

public class MetaSource {
	private final static String INDEX="_index";
	private final static String TYPE="_type";
	private final static String ID="_id";
	private final static String SCORE="_score";
	
	/*********************************************************************/
	private String index;
	private String type;
	private String id;
	private Double score;
	
	/******************************************************************************/
	public MetaSource() {
		super();

	}

	public MetaSource(String index, String type, String id, Double score) {
		super();
		this.index = index;
		this.type = type;
		this.id = id;
		this.score = score;
	}

	/***********************************************************************************/
	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public Double getScore() {
		return score;
	}
	
	public static MetaSource  getMetaSource(JsonObject jsonObject){
		
		return  new MetaSource(	(jsonObject.has(INDEX))?	jsonObject.get(INDEX).getAsString() : null,
								(jsonObject.has(TYPE))?	jsonObject.get(TYPE).getAsString() : null, 
								(jsonObject.has(ID))?	jsonObject.get(ID).getAsString() : null, 
								(jsonObject.has(SCORE))?	jsonObject.get(SCORE).getAsDouble() : null);
		
		
	}

	@Override
	public String toString() {
		return "MetaSource [index=" + index + ", type=" + type + ", id=" + id + ", score=" + score + "]";
	}

	/*******************************************************************************************/
	
	
	
	

}
