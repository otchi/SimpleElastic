package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class Bucket {
	private Integer count;
	private Boolean isCheked;
	private Aggregations aggregations;
	
	public Bucket(Integer count, Aggregations aggregations) {
		super();
		this.count = count;
		this.isCheked=true;
		this.aggregations = aggregations;
	}
	
	/*****************************************************************/
	public Aggregations getAggregations() {
		return aggregations;
	}


	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Boolean getIsCheked() {
		return isCheked;
	}

	public void setIsCheked(Boolean isCheked) {
		this.isCheked = isCheked;
	}
	


	public static boolean isBucket(JsonObject jsonObject){
		return jsonObject.has("doc_count");
		
	}
	//***************************************************************************************/
	public static Bucket getBucket(JsonObject jsonObject){
		
		if(!isBucket(jsonObject)){
			System.out.println("Exception Bucket : non object input: "+jsonObject);
			return null;
		}		
		Aggregations subAggr=Aggregations.getAggregations(jsonObject);
		
		return (jsonObject.has("doc_count"))? 
				new Bucket(jsonObject.get("doc_count").getAsInt(),
				(subAggr!=null)?subAggr:new Aggregations())	:	null;
		
	}
	/*********************************************************************************/
	

	
	public Bucket getDataCopy() {
		return new Bucket(this.count.intValue(), aggregations.getDataCopy());
	}

	@Override
	public String toString() {
		return "Bucket [count=" + count + ", isCheked=" + isCheked + ", aggregations=" + aggregations + "]";
	}


}
