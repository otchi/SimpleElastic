package com.edifixio.amine.application.elasticResults;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Aggregations {
	private final  static String BUCKETS="buckets";
	private Map<String,Aggr> aggregations;
	
	
	public Aggregations(){
		this.aggregations=new HashMap<String, Aggr>();
	}
	public Aggregations(Map<String,Aggr> aggregations){
		this.aggregations=aggregations;
	}
	public  Map<String,Aggr> getAggregations(){
		return this.aggregations;
	}
	
	/**********************************************************************************************/
	public static Aggregations getAggregations(JsonObject jsonObject){
		
		
		//if(jsonObject.entrySet().size()>0)System.out.println("--->"+jsonObject+"-----");
		if(!jsonObject.isJsonObject()){
			return null;
		}
		Map<String,Aggr> aggregations=new HashMap<String, Aggr>();
		
		Iterator<Entry<String, JsonElement>> jsonObjectIter=jsonObject.entrySet().iterator();
		Entry<String, JsonElement> entry;
		
		while (jsonObjectIter.hasNext()){
			entry=jsonObjectIter.next();
			String key=entry.getKey();
			
			if(!entry.getValue().isJsonObject())continue;
			
			JsonObject value=entry.getValue().getAsJsonObject();
			
			if(value.has(BUCKETS)){
				//System.out.println(FacetableAggr.getFacetableAggr(value.getAsJsonArray(BUCKETS)));
				aggregations.put(key, FacetableAggr.getFacetableAggr(value.getAsJsonArray(BUCKETS)));
				continue;
			}
				
		}
		return new Aggregations(aggregations);
		
	}
	/************************************************************************************/
	public Map<String,FacetableAggr> getFacetableAggregations(){
		
		Map<String,FacetableAggr> facetableAggr=new HashMap<String, FacetableAggr>();
		Iterator<Entry<String, Aggr>> aggrs=aggregations.entrySet().iterator();
		Entry<String, Aggr> entry;
		while(aggrs.hasNext()){
			entry=aggrs.next();
			if(entry.getValue().isFacetableAggr()) {
				facetableAggr.put(entry.getKey(), entry.getValue().getAsFacetableAggr());
			}
		}	
		
		return facetableAggr;
	}

	/************************************************************************************/

	
	public Aggregations getDataCopy(){
		Map<String,Aggr> copy=new HashMap<String, Aggr>();
		Iterator<Entry<String, Aggr>> aggregationIter=
				this.aggregations.entrySet().iterator();
		Entry<String, Aggr> entry;
		
		while(aggregationIter.hasNext()){
			entry=aggregationIter.next();
			copy.put(entry.getKey().toString(), (Aggr) entry.getValue().getDataCopy());
		}
		return new Aggregations(copy);
		
	}
	@Override
	public String toString() {
		return "Aggregations [aggregations=" + aggregations + "]";
	}
	
	
	
	

	
	
}
