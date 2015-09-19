package com.edifixio.amine.application.elasticResults;

public interface Aggr {
	public boolean isFacetableAggr();
	public FacetableAggr getAsFacetableAggr();
	public FacetableAggr  getDataCopy(); 


}
