package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;


public class ElasticReturn {
	private static final String HITS="hits";
	private static final String AGGS="aggregations";
	
	private ReturnMetas returnMetas;
	private Hits setSources;
	private Aggregations aggregations;
	/**********************************************************************************************************/
	public ElasticReturn(
			ReturnMetas returnMetas, 
			Hits setSources, 
			 Aggregations aggregations) {
		
		super();
		this.returnMetas = returnMetas;
		this.setSources = setSources;
		this.aggregations = aggregations;
	}

	/**********************************************************************************************************/
	public ReturnMetas getReturnMetas() {
		return returnMetas;
	}

	/**********************************************************************************************************/
	public Hits getSetSources() {
		return setSources;
	}

	/**********************************************************************************************************/
	public Aggregations getAggregation() {
		return aggregations;
	}
	/**********************************************************************************************************/
	public static ElasticReturn getElasticReturn(JsonObject jsonObject){
		Aggregations aggregations=null;
		Hits setSources;
		//System.out.println("getElasticReturn-->"+jsonObject);
		setSources = Hits.getHits(jsonObject.getAsJsonObject(HITS));
		if(jsonObject.has(AGGS)){
			aggregations=Aggregations.getAggregations(jsonObject.getAsJsonObject(AGGS));
		}
		return new ElasticReturn(ReturnMetas.getReturnMetas(jsonObject), setSources, aggregations);
	}
	/**********************************************************************************************************/
	public Boolean hasAggregations(){
		return (this.aggregations!=null)
				? true : false;
	}

	@Override
	public String toString() {
		return "ElasticReturn [returnMetas=" + returnMetas + ", setSources=" + setSources + ", aggregations="
				+ aggregations + "]";
	}

	/**********************************************************************************************************/

	
}
