package com.edifixio.amine.application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.edifixio.amine.application.elasticResults.Aggr;
import com.edifixio.amine.application.elasticResults.Aggregations;
import com.edifixio.amine.application.elasticResults.Bucket;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.application.elasticResults.RangeAggr;
import com.edifixio.amine.application.elasticResults.RangeBucket;
import com.edifixio.amine.application.elasticResults.TermAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.google.gson.JsonObject;


public class SimpleFacetConfigUnit extends JsonObjectConfig {

	private static final String FACET_NAME = "facet_name";
	private static final String SUB_FACETS = "sub_facets";
	private static final String AGGS = "aggs";

	public SimpleFacetConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	public void process(JsonObject aggQuery, Map<String, FacetableAggr> facetsData,
			IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse) {

		/**********************************/
		if (!mapConfig.containsKey(FACET_NAME)) {
			System.out.println("error : ~ 32 SimpleFacetConfigUnit");
			return;
		}
		
		String facetName = ((JsonStringConfig) mapConfig.get(FACET_NAME)).getValue();
		/**********************************/
		if (!facetsData.containsKey(facetName)) {
			System.out.println("Exception SimpleFacetConfigUnit  :there are no facet named  :" + facetName
					+ " in facet stucture input");
			return;
		}
		/*************************************/
		if (!aggQuery.has(facetName)) {
			System.out
					.println("Exception SimpleFacetConfigUnit :there are no facet named  :" + facetName
							+ " in Query ");
			return;
		}
		/**********************************/
		FacetableAggr facetableAggr = facetsData.get(facetName);
		JsonObject jsFacetAgg = aggQuery.getAsJsonObject(facetName);		

		/**********************************/
		if (facetableAggr.isTermAggr()) {
			
			BuildTermFacet(jsFacetAgg, facetName, facetableAggr, buildResponse);
		}
		/**********************************/
		if (facetableAggr.isRangeAggr()) {
			
			BuildRangeFacet(jsFacetAgg, facetName, facetableAggr, buildResponse);
		}
		// buildResponse.putElement(facetUnit.end().end().end().getJsonElement());
		// System.out.println("-+-+-+-->"+buildResponse.end().end().end().getJsonElement());

	}
	
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	
	public final  void BuildTermFacet(JsonObject jsFacetAgg,String facetName,FacetableAggr facetableAggr,
			IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse){
		
		Iterator<Entry<String, Bucket>> bucketsIter = facetableAggr.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		/**********************************/
		if (!jsFacetAgg.has(SimpleFacetsConfig.TERMS)) {
			System.out.println("Exception : incompatile type facet between facet input structure and Query "
					+ ": query agg  haven't term aggregation at " + facetName + " aggr");
			return;
		}
		/**********************************/
		String fieldAgg = jsFacetAgg.getAsJsonObject(SimpleFacetsConfig.TERMS)
									.get(SimpleFacetsConfig.FIELD)
									.getAsString();
		/****************************/
		while (bucketsIter.hasNext()) {

			bucketEntry = bucketsIter.next();
			Bucket bucket = bucketEntry.getValue();
			/**********************************/
			if (!bucket.getIsCheked()) continue;

			String bucketName = bucketEntry.getKey();
			// System.out.println("call SimpleFacetsConfig js :---->// "+jsFacetAgg+"\n"+AGGS);
			// System.out.println("----> call SimpleFacetsConfig facets// :"+bucket.getAggregations().getFacetableAggregations());
			buildResponse.putObject()
						.begin()
							.putObject(SimpleFacetsConfig.BOOL)
							.begin()
								.putArray(SimpleFacetsConfig.MUST)
								.begin()
									.putObject()
									.begin()
										.putObject(SimpleFacetsConfig.TERM)
										.begin()
											.putPreprety(fieldAgg, bucketName)
										.end()
									.end()
									.putElement(((SimpleFacetsConfig) mapConfig.get(SUB_FACETS))
												.process(jsFacetAgg.getAsJsonObject(AGGS), 
														 bucket.getAggregations().getFacetableAggregations()));
		}
	}
	
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	
	
	public final  void BuildRangeFacet(JsonObject jsFacetAgg,String facetName,FacetableAggr facetableAggr,
			IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse){
		/**********************************/
		if (!jsFacetAgg.has(SimpleFacetsConfig.RANGE)) {
			System.out.println("Exception : incompatile type facet between facet input structure and Query "
					+ ": query agg  haven't range aggregation at " + facetName + " aggr");
			return;
		}
		/**********************************/
		Iterator<Entry<String, Bucket>> bucketsIter = facetableAggr.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		/**********************************/
		String fieldAgg = jsFacetAgg.getAsJsonObject(SimpleFacetsConfig.RANGE).get(SimpleFacetsConfig.FIELD)
				.getAsString();

		// System.out.println("|||||------>"+facetableAggr.getBuckets());
		/**********************************/
		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			RangeBucket bucket = (RangeBucket) bucketEntry.getValue();
			/**********************************/
			if (!bucket.getIsCheked())
				continue;

			buildResponse.putObject()
						.begin()
							.putObject(SimpleFacetsConfig.BOOL)
							.begin()
								.putArray(SimpleFacetsConfig.MUST)
								.begin()
									.putObject()
									.begin()
										.putObject(SimpleFacetsConfig.RANGE)
										.begin()
											.putObject(fieldAgg)
											.begin()
												.putPreprety("gte", bucket.getFrom())
												.putPreprety("lt", bucket.getTo())
											.end()
										.end()
									.end()
									.putElement(((SimpleFacetsConfig) mapConfig.get(SUB_FACETS))
												.process(jsFacetAgg.getAsJsonObject(AGGS),
															bucket.getAggregations()
																.getFacetableAggregations()));
		}
	}

	
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	/*******************************************************************************************************************/
	public void getFacet(Map<String, FacetableAggr> facetableAggs,Map<String, FacetableAggr> facetResult){

	
		
		String facetName=((SimpleJsonStringConfig)this.mapConfig.get(FACET_NAME)).getValue();
		SimpleFacetsConfig simpleFacetsConfig=((SimpleFacetsConfig) mapConfig.get(SUB_FACETS));
		
			
		if(!facetableAggs.containsKey(facetName)){
			System.out.println("exception  SimpleFacetsConfig ~ no facet named : "+facetName+" in this floor");
			return ;
		}
		
		FacetableAggr facet=facetableAggs.get(facetName);
		
		
		
		Iterator<Entry<String, Bucket>> bucketsIter=facet.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		Map<String, Bucket> resultMap=new HashMap<String, Bucket>();
			
	
		while(bucketsIter.hasNext()){
			
			bucketEntry=bucketsIter.next();
			Bucket bucket=bucketEntry.getValue();
			
			Map<String, FacetableAggr> subFacets=simpleFacetsConfig.getFacets(bucket.getAggregations().getFacetableAggregations());
			Iterator<Entry<String, FacetableAggr>> subFacetsIter=subFacets.entrySet().iterator();
			
			Map<String,Aggr> castedFacets=new HashMap<String, Aggr>();
			
			//System.out.println(subFacets);
			
			while(subFacetsIter.hasNext()){
				Entry<String, FacetableAggr> entry=subFacetsIter.next();
				castedFacets.put(entry.getKey(), entry.getValue());
			}
			
			resultMap.put(bucketEntry.getKey(), new Bucket(bucket.getCount(), new Aggregations(castedFacets)));
			
		}
		
		
		if(facet.isRangeAggr()){
			facetResult.put(facetName,(FacetableAggr) new RangeAggr(resultMap));
			return;
		}
		if(facet.isTermAggr()){
			facetResult.put(facetName,(FacetableAggr) new TermAggr(resultMap));
			return;
		}
		
	} 
	/****************************************************************************************************/

	@Override
	public String toString() {
		return "SimpleFacetConfigUnit [mapConfig=" + mapConfig + "]";
	}
	
}
