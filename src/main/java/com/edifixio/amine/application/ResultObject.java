package com.edifixio.amine.application;

import java.util.List;
import java.util.Map;

import com.edifixio.amine.application.elasticResults.FacetableAggr;

public class ResultObject {
	private List<Object> resultList; 
	private Map<String, FacetableAggr> facets;
	
	public ResultObject() {
		super();
	}
	
	public ResultObject(List<Object> resultList, Map<String, FacetableAggr> facets) {
		super();
		this.resultList = resultList;
		this.facets = facets;
	}
	public List<Object> getResultList() {
		return resultList;
	}

	public Map<String, FacetableAggr> getFacets() {
		return facets;
	}

	@Override
	public String toString() {
		return "ResultObject [resultList=" + resultList + ", \n facets=" + facets + "]";
	}
	
	

}
