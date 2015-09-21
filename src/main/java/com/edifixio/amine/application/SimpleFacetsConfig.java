package com.edifixio.amine.application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.SimpleFacetConfigUnit;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.elasticResults.Bucket;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.application.elasticResults.RangeAggr;
import com.edifixio.amine.application.elasticResults.RangeBucket;
import com.edifixio.amine.application.elasticResults.TermAggr;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.utils.ConfigFactoryUtiles;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonObject;

public class SimpleFacetsConfig extends JsonArrayConfig {

	public static final String SHOULD = "should";
	public static final String MUST = "must";
	public static final String BOOL = "bool";
	public static final String TERMS = "terms";
	public static final String TERM = "term";
	public static final String RANGE = "range";
	public static final String FIELD = "field";

	/****************************************************************************************************************/
	/****************************************************************************************************************/
	/***************************************************************************************************/
	public JsonObject process(JsonObject aggQuery, Map<String, FacetableAggr> facetsData) {

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse = JsonObjectBuilder.init().begin()
				.putObject(BOOL).begin().putArray(SHOULD).begin();

		Iterator<JsonElementConfig> facetConfigIter = jsonElementConfigs.iterator();
		JsonElementConfig facetConfigEntry;
		
		/**************************************/
		while (facetConfigIter.hasNext()) {
			
			facetConfigEntry = facetConfigIter.next();
			
			/***************************************/
			if (ConfigFactoryUtiles.inherited(JsonStringConfig.class, facetConfigEntry.getClass()) < 0) {

				if (!facetConfigEntry.getClass().equals(SimpleFacetConfigUnit.class)) {
					System.out.println("Exception SimpleFacetsConfig ~ 46 : this Config class not supported");
					return null;
				}
				System.out.println("------- here ------------------");
				((SimpleFacetConfigUnit) facetConfigEntry).process(aggQuery, facetsData,buildResponse);
				continue;
			}

			String facetName = ((SimpleJsonStringConfig) facetConfigEntry).getValue();
			
			/**************************************/
			//System.out.println(" \n \n facate data :"+facetsData+"\n\n");
			if (!facetsData.containsKey(facetName)) {
				System.out.println("Exception SimpleFacetsConfig :there are no facet named  :" + facetName
									+ " in facet stucture input");
				return null;
			}
			
			/*************************************/
			if (!aggQuery.has(facetName)) {
				System.out.println("Exception SimpleFacetsConfig :there are no facet named  :" + facetName 
									+ " in Query ");
			}
			
			/*************************************/
			FacetableAggr facetableAggr = facetsData.get(facetName);
			JsonObject jsFacetAgg = aggQuery.getAsJsonObject(facetName);

			
			/************************************/
			if (facetableAggr.isTermAggr()) {
				BuildTermFacet(facetableAggr.getAsTermAggr(), jsFacetAgg, facetName,buildResponse);
				continue;
			}
			
			/******************************/
			if (facetableAggr.isRangeAggr()) {
				BuildRangeFacet(facetableAggr.getAsRangeAggr(), jsFacetAgg, facetName,buildResponse);
				continue;
			}
			System.out.println("Exception SimpleFacetConfig ~75 : this kind of facet is not supported ");
		}

		return buildResponse.end().end().end().getJsonElement().getAsJsonObject();
	} 
	
	/****************************************************************************************************************/
	/****************************************************************************************************************/
	/****************************************************************************************************************/
	public Map<String, FacetableAggr> getFacets(Map<String, FacetableAggr> facetablesAggs){
		
		Map<String, FacetableAggr> result=new HashMap<String, FacetableAggr>();
		Iterator<JsonElementConfig> configIter=this.jsonElementConfigs.iterator();
		JsonElementConfig facet;
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println(facetablesAggs);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		
		while(configIter.hasNext()){
			facet =configIter.next();
//			System.out.println("????????????je suis ici");
			
			/***************************************/
			if (ConfigFactoryUtiles.inherited(JsonStringConfig.class, facet.getClass()) < 0) {
//				System.out.println("!!!!!! I m comp");
				if (!facet.getClass().equals(SimpleFacetConfigUnit.class)) {
					System.out.println("Exception SimpleFacetsConfig ~ 46 : this Config class not supported");
					return null;
				}
				System.out.println("------- here ------------------");
				((SimpleFacetConfigUnit) facet).getFacet(facetablesAggs, result);
				continue;
			}
			
			String facetName = ((SimpleJsonStringConfig) facet).getValue();
			
			if(!facetablesAggs.containsKey(facetName)){
				System.out.println("exception  SimpleFacetsConfig ~ no facet named : "+facetName+" in this floor");
				return null;
			}
//			System.out.println("!!!!!! I m here simp");
			result.put(facetName,facetablesAggs.get(facetName));
		}
		
		
		return result;
	}
	
	/****************************************************************************************************************/
	/****************************************************************************************************************/
	/****************************************************************************************************************/
	
	public final static void BuildTermFacet(TermAggr termAggr, JsonObject jsFacetAgg, String facetName ,
			IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse) {

		if (!jsFacetAgg.has(TERMS)) {
			System.out.println("Exception : incompatile type facet between facet input structure and Query "
					+ ": query agg  haven't term aggregation at " + facetName + " aggr");
			return ;
		}
		
		/***************************************************************************/
		String fieldAgg = jsFacetAgg.getAsJsonObject(TERMS).get(FIELD).getAsString();
		Iterator<Entry<String, Bucket>> bucketsIter = termAggr.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		
		/*****************************/
		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			Bucket bucket = bucketEntry.getValue();

			if (!bucket.getIsChecked())
				continue;
			/****************************************/
			String bucketName = bucketEntry.getKey();
			buildResponse.putObject().begin().putObject(TERM).begin().putPreprety(fieldAgg, bucketName).end().end();

		}
	}
	
	/****************************************************************************************************************/
	/****************************************************************************************************************/
	/*******************************************************************************************************/
	
	public final static void BuildRangeFacet(RangeAggr rangeAgg, JsonObject jsFacetAgg, String facetName ,
			IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse) {

		if (!jsFacetAgg.has(RANGE)) {
			System.out.println("Exception : incompatile type facet between facet input structure and Query "
					+ ": query agg  haven't range aggregation at " + facetName + " aggr");
			return ;
		}

		String fieldAgg = jsFacetAgg.getAsJsonObject(RANGE).get(FIELD).getAsString();

		Iterator<Entry<String, Bucket>> bucketsIter = rangeAgg.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		/*******************************/
		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			RangeBucket rangeBucket = (RangeBucket) bucketEntry.getValue();

			if (!rangeBucket.getIsChecked())
				continue;

			buildResponse.putObject().begin().putObject(RANGE).begin().putObject(fieldAgg).begin()
					.putPreprety("gte", rangeBucket.getFrom()).putPreprety("lt", rangeBucket.getTo()).end().end().end();

		}
		
	}

	@Override
	public String toString() {
		return "SimpleFacetsConfig [jsonElementConfigs=" + jsonElementConfigs + "]";
	}
	
	
}
