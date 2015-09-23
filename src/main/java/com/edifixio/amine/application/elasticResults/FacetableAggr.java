package com.edifixio.amine.application.elasticResults;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public  class FacetableAggr implements IFacetableAggr{
	private Map<String, Bucket> buckets;


	/*****************************************************************/
	public FacetableAggr(Map<String, Bucket> buckets) {
		super();
		this.buckets = buckets;

	}
	
	public FacetableAggr() {
		super();
		buckets=new HashMap<String, Bucket>();
	}
	/*********************************************************************/
	public Map<String, Bucket> getBuckets() {
		return buckets;
	}
	

	public void setBuckets(Map<String, Bucket> buckets) {
		this.buckets = buckets;
	}

	public boolean isFacetableAggr() {
		// TODO Auto-generated method stub
		return true;
	}

	public FacetableAggr getAsFacetableAggr() {
		// TODO Auto-generated method stub
		return this;
	}
	/***********************************************************************/
	public static FacetableAggr getFacetableAggr(JsonArray jsonArray) {
		//System.out.println(jsonArray);

		if (jsonArray.size() == 0)
			return new TermAggr(new HashMap<String, Bucket>());

		JsonElement testElement = jsonArray.get(0);

		if (!testElement.isJsonObject()) {
			System.out.println("error");
			return null;
		}
		/*************************************************/
		JsonObject testObject = testElement.getAsJsonObject();
		Iterator<JsonElement> jeIter = jsonArray.iterator();
		Map<String, Bucket> buckets = new HashMap<String, Bucket>();
		FacetableAggrType ft = null;

		/*
		 * System.out.println("-->range" +" bucket ????--->"
		 * +RangeBucket.isRangeBucket(testObject)); System.out.println("-->term"
		 * +" bucket ????--->"+RangeBucket.isBucket(testObject));
		 */
		if (RangeBucket.isRangeBucket(testObject))
			ft = FacetableAggrType.RANGE;
		else if (Bucket.isBucket(testObject))
			ft = FacetableAggrType.TERMS;
		
		/***********************************************/
		while (jeIter.hasNext()) {

			JsonElement je = jeIter.next();

			if (!je.isJsonObject()) {
				System.out.println("erreur");
				return null;
			}

			JsonObject jo = je.getAsJsonObject();
			String key = jo.get("key").getAsString();

			if (ft.equals(FacetableAggrType.RANGE)) {
				buckets.put(key, RangeBucket.getRangeBucket(jo));
				continue;
			}

			if (ft.equals(FacetableAggrType.TERMS)) {
				buckets.put(key, Bucket.getBucket(jo));
				continue;
			}

		}
		/***************************************/
		if (ft.equals(FacetableAggrType.RANGE))
			return new RangeAggr(buckets);
		if (ft.equals(FacetableAggrType.TERMS))
			return new TermAggr(buckets);

		return null;
	}
	/*******************************************************************************************/
	public void update(FacetableAggr newFacetAggr) {
		Iterator<Entry<String, Bucket>> newFacetAggrIter = newFacetAggr.getBuckets().entrySet().iterator();
		Entry<String, Bucket> entry;

		while (newFacetAggrIter.hasNext()) {
			entry = newFacetAggrIter.next();
			if (this.buckets.containsKey(entry.getKey())) {
				this.buckets.get(entry.getKey()).setCount(entry.getValue().getCount());
			}
		}
	}
	/*****************************************************************************************/
	public void intitialFacet() {
		Iterator<Bucket> bucketsIter = this.buckets.values().iterator();
		while (bucketsIter.hasNext()) {
			bucketsIter.next().setCount(0);
		}
	}

	/*************************************************************************************/


	public final Map<String, Bucket> getMapCopy() {
		Map<String, Bucket> copy = new HashMap<String, Bucket>();
		Iterator<Entry<String, Bucket>> originIter = this.getBuckets().entrySet().iterator();
		Entry<String, Bucket> entry;
		while (originIter.hasNext()) {
			entry = originIter.next();
			copy.put(entry.getKey().toString(), entry.getValue().getDataCopy());
		}
		return copy;
	}

	@Override
	public String toString() {
		return "FacetableAggr [buckets=" + buckets + "]";
	}

	public Boolean isTermAggr() {
		// TODO Auto-generated method stub
		return null;
	}

	public TermAggr getAsTermAggr() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isRangeAggr() {
		// TODO Auto-generated method stub
		return null;
	}

	public RangeAggr getAsRangeAggr() {
		// TODO Auto-generated method stub
		return null;
	}

	public FacetableAggr getDataCopy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
