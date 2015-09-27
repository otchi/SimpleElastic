package com.edifixio.amine.application.elasticResults;

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
		
		return new MetaHits(	jsonObject.has(TOTAL) ? jsonObject.get(TOTAL).getAsInt() : null, 
									jsonObject.has(MAX_SCORE) ? jsonObject.get(MAX_SCORE).getAsDouble() : null);
		
	}

	@Override
	public String toString() {
		return "MetaHits [total=" + total + ", maxScore=" + maxScore + "]";
	}
	
	
	
}
