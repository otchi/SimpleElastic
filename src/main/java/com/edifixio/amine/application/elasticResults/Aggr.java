package com.edifixio.amine.application.elasticResults;

public interface Aggr<Type> extends ICutRef<Type>{
	public boolean isFacetableAggr();
	public FacetableAggr getAsFacetableAggr(); 


}
