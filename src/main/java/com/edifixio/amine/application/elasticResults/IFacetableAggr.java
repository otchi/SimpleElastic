package com.edifixio.amine.application.elasticResults;

public interface IFacetableAggr<Type> extends Aggr<Type> {
	public  Boolean isTermAggr();

	public  TermAggr getAsTermAggr();

	public  Boolean isRangeAggr();

	public  RangeAggr getAsRangeAggr();


}
