package com.edifixio.amine.application.elasticResults;

import java.util.Map;

import com.google.gson.JsonObject;

public class Bucket implements ICutRef<Bucket>{
	private Integer count;
	private Boolean isChecked;
	private Aggregations aggregations;

	/******************************************************************/
	public Bucket() {
		super();
		aggregations=new Aggregations();
		
	}
	/****************************************************************/
	public Bucket(Integer count, Aggregations aggregations) {
		super();
		this.count = count;
		this.isChecked=true;
		this.aggregations = aggregations;
	}
	
	/*****************************************************************/
	public Aggregations getAggregations() {
		return aggregations;
	}
	/**********************************************************************/
	public Map<String,FacetableAggr> getFacetableAggrs() {
		return aggregations.getFacetableAggrs();
	}
	/********************************************************************/
	public  void setFacetableAggrs(Map<String,FacetableAggr> FacetableAggrs) {
		this.aggregations.setFacetableAggrs(FacetableAggrs);
	}

	/**********************************************************************/
	public void setAggregations(Aggregations aggregations) {
		this.aggregations = aggregations;
	}
	/******************************************************************/
	public Integer getCount() {
		return count;
	}
	/********************************************************************/
	public void setCount(Integer count) {
		this.count = count;
	}
	/************************************************************************/
	public Boolean getIsChecked() {
		return isChecked;
	}
	/********************************************************************/
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	/***********************************************************************/
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
	
	@Override
	public String toString() {
		return "Bucket [count=" + count + ", isCheked=" + isChecked + ", aggregations=" + aggregations + "]";
	}

	public Bucket getCopy() {
		Bucket bucket=new Bucket(this.count.intValue(), aggregations.getCopy());
		bucket.setIsChecked(this.isChecked.booleanValue());
		return bucket;
	}

	public void update(Bucket object) {
		this.isChecked=object.isChecked;
		this.aggregations.update(object.getAggregations());
		
	}


}
