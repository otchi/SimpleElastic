package com.edifixio.amine.application.elasticResults;

import java.util.HashMap;
import java.util.Map;

public class AggrsResultObject {
	private Map<String, FacetableAggr> facets;
	
	public AggrsResultObject(){
		
		facets=new HashMap<String, FacetableAggr>();
	}
	
	public AggrsResultObject( Map<String, FacetableAggr> facets){
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
	
	

}
