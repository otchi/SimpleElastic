package com.edifixio.amine.application.elasticResults;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
@SuppressWarnings("rawtypes")
public class Aggregations implements ICutRef<Aggregations> {
	private final  static String BUCKETS="buckets";
	private Map<String,Aggr> aggregations;
	private Map<String,FacetableAggr> facetableAggrs;
	
	/****************************************************************************/
	public Aggregations(){
		this.aggregations=new HashMap<String, Aggr>();
		this.facetableAggrs=new HashMap<String, FacetableAggr>();
	}
	/***************************************************************************/
	public Aggregations(Map<String,Aggr> aggregations){
		this.aggregations=aggregations;
		this.putFacetableAggr(aggregations,true);
		

	}
	/***************************************************************************/
	public  Map<String,Aggr> getAggregations(){
		return this.aggregations;
	}
	/***************************************************************************/
	public void setAggregations(Map<String, Aggr> aggregations) {
		this.aggregations = aggregations;
		this.putFacetableAggr(aggregations,true);
	}
	/***************************************************************************/
	public Map<String, FacetableAggr> getFacetableAggrs() {
		return facetableAggrs;
	}
	/***************************************************************************/
	public void addAggs(String key,Aggr value) {
		aggregations.put(key, value);
		if(value.isFacetableAggr()) 
			facetableAggrs.put(key, value.getAsFacetableAggr());
	}
	/***************************************************************************/
	public void setFacetableAggrs(Map<String, FacetableAggr> facetableAggrs) {
		if(facetableAggrs==null) return;
		
		boolean isFacetableNull	=	(this.facetableAggrs==null)?true : false;
		boolean isAggrNull	=	(this.facetableAggrs==null)?true : false;
		if(isFacetableNull)	this.facetableAggrs=facetableAggrs;
		if(isAggrNull) this.aggregations=new HashMap<String, Aggr>();
		
		Iterator<Entry<String, FacetableAggr>>  facetableAggrsIter=facetableAggrs.entrySet().iterator();
		Entry<String, FacetableAggr> facetableAggrsEnter;
		while(facetableAggrsIter.hasNext()){
			facetableAggrsEnter=facetableAggrsIter.next();
			String key=facetableAggrsEnter.getKey();
			FacetableAggr value=facetableAggrsEnter.getValue();
			
			if(!isFacetableNull) facetableAggrs.put(key, value);
			aggregations.put(key, value);
			
		}
		
	}
	/***************************************************************************/
	private  void  putFacetableAggr(Map<String, Aggr> aggregations,boolean isNew){
		if(facetableAggrs==null || isNew)
			this.facetableAggrs=new HashMap<String, FacetableAggr>();
		
		if(aggregations==null) return;
		
		Iterator<Entry<String, Aggr>> aggrsIter=aggregations.entrySet().iterator();
		Entry<String, Aggr> entryAggr;
		while(aggrsIter.hasNext()){
			entryAggr=aggrsIter.next();
			if(entryAggr.getValue().isFacetableAggr()){
				this.facetableAggrs.put(entryAggr.getKey(), entryAggr.getValue().getAsFacetableAggr());
			}
		}
		
	}
	/*****************************************************************************************************/
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
	/****************************************************************************************************/
	public Aggregations getCopy() {
		Map<String,Aggr> copy=new HashMap<String, Aggr>();
		Iterator<Entry<String, Aggr>> aggregationIter=
				this.aggregations.entrySet().iterator();
		Entry<String, Aggr> entry;
		
		while(aggregationIter.hasNext()){
			entry=aggregationIter.next();
			copy.put(entry.getKey(), (Aggr) entry.getValue().getCopy());
		}
		return new Aggregations(copy);
	}
	/****************************************************************************************************/
	@Override
	public String toString() {
		return "Aggregations [aggregations=" + aggregations + ", facetableAggrs=" + facetableAggrs + "]";
	}
	/******************************************************************************************************/
	@SuppressWarnings("unchecked")
	public void update(Aggregations object) {
		
		Iterator<Entry<String, FacetableAggr>> facetAggsIter=object.getFacetableAggrs().entrySet().iterator();
		Entry<String, FacetableAggr> facetAggsEntry;
		while(facetAggsIter.hasNext()){
			facetAggsEntry=facetAggsIter.next();
			String key=facetAggsEntry.getKey();
			
			if(!this.facetableAggrs.containsKey(key)){
				System.out.println("154 ~ exception Aggrregation");
				return;
			}
			this.facetableAggrs.get(key).update(facetAggsEntry.getValue());
		}
		
		/**************************************************************************/
		Iterator<Entry<String, Aggr>> aggsIter=object.getAggregations().entrySet().iterator();
		Entry<String, Aggr> aggsEntry;
		while(aggsIter.hasNext()){
			aggsEntry=aggsIter.next();
			String key=aggsEntry.getKey();
			if(!this.aggregations.containsKey(key)){
				System.out.println("154 ~ exception Aggrregation");
				return;
			}
			this.aggregations.get(key).update(aggsEntry.getValue());
			
		}	
	}
	


	
	
	
	

	
	
}
