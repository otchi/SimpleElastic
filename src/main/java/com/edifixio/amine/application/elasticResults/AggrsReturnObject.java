package com.edifixio.amine.application.elasticResults;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AggrsReturnObject implements ICutRef<AggrsReturnObject> {
	private Map<String, FacetableAggr> facets;
	
	public AggrsReturnObject(){
		
		facets=new HashMap<String, FacetableAggr>();
	}
	
	public AggrsReturnObject( Map<String, FacetableAggr> facets){
		this.facets=facets;
	}

	public Map<String, FacetableAggr> getFacets() {
		return facets;
	}
	

	public void setFacets(Map<String, FacetableAggr> facets) {
		this.facets = facets;
	}

	@Override
	public String toString() {
		return "AggrsResultObject [facets=" + facets + "]";
	}

	public AggrsReturnObject getCopy() {
		Iterator<Entry<String, FacetableAggr>> facetsIter=facets.entrySet().iterator();
		Entry<String, FacetableAggr> facetEntry;		
		Map<String, FacetableAggr> result=new HashMap<String, FacetableAggr>();
		
		while(facetsIter.hasNext()){
			facetEntry = facetsIter.next();
			result.put(facetEntry.getKey(), facetEntry.getValue().getCopy());
		}
		
		
		return new AggrsReturnObject(result);
	}

	public void update(AggrsReturnObject object) {
		Iterator<Entry<String, FacetableAggr>> facetsIter=object.getFacets().entrySet().iterator();
		Entry<String, FacetableAggr> facetEntry;
		
		while(facetsIter.hasNext()){
			facetEntry = facetsIter.next();
			String key = facetEntry.getKey();
			if(!this.facets.containsKey(key)){
				System.out.println("54 ~ exception AggrsResult");
				return;
			}
			this.facets.get(key).update(facetEntry.getValue());
		}
	}


	
	

}
