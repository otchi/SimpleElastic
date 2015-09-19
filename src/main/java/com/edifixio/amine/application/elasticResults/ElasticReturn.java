package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;


public class ElasticReturn {
	private static final String HITS="hits";
	private static final String AGGS="aggregations";
	
	private ReturnMetas returnMetas;
	private SetSources setSources;
	private Aggregations aggregations;
	
	public ElasticReturn(
			ReturnMetas returnMetas, 
			SetSources setSources, 
			 Aggregations aggregations) {
		
		super();
		this.returnMetas = returnMetas;
		this.setSources = setSources;
		this.aggregations = aggregations;
	}


	public ReturnMetas getReturnMetas() {
		return returnMetas;
	}


	public SetSources getSetSources() {
		return setSources;
	}


	public Aggregations getAggregation() {
		return aggregations;
	}
	
	public static ElasticReturn getElasticReturn(JsonObject jsonObject){
		Aggregations aggregations=null;
		SetSources setSources;
		//System.out.println("getElasticReturn-->"+jsonObject);
		setSources = SetSources.getSetSources(jsonObject.getAsJsonObject(HITS));
		if(jsonObject.has(AGGS)){
			aggregations=Aggregations.getAggregations(jsonObject.getAsJsonObject(AGGS));
		}
		return new ElasticReturn(null, setSources, aggregations);
	}
	public Boolean hasAggregations(){
		return (this.aggregations!=null)
				? true : false;
	}


	@Override
	public String toString() {
		return "ElasticReturn [returnMetas=" + returnMetas + ", setSources=" + setSources + ", aggregations="
				+ aggregations + "]";
	}


	
}
