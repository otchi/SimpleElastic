package com.edifixio.simplElastic.application.elasticResults;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MetaHits {
	private static final String TOTAL="total";
	private static final String MAX_SCORE="max_score";
	
	private Integer total;
	private Double maxScore;

	public MetaHits() {
		super();
	}

	public MetaHits(Integer total,Double maxScore) {
		super();
		this.total = total;
		this.maxScore = maxScore;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

	public static MetaHits getMetaHits(JsonObject jsonObject){
		if(jsonObject==null) return  null;
		Integer total=null;
		Double maxScore=null;
		
		if(jsonObject.has(TOTAL)){
			JsonElement je=jsonObject.get(TOTAL);
			if(je.isJsonPrimitive()){
				if(je.getAsJsonPrimitive().isNumber())
					total=je.getAsInt();
			}	
		}
		
		if(jsonObject.has(MAX_SCORE)){
			JsonElement je=jsonObject.get(MAX_SCORE);
			if(je.isJsonPrimitive()){
				if(je.getAsJsonPrimitive().isNumber())
					maxScore=je.getAsDouble();
			}	
		}
		
		return new MetaHits(total, maxScore);
		
	}

	@Override
	public String toString() {
		return "MetaHits [total=" + total + ", maxScore=" + maxScore + "]";
	}
	
	
	
}
