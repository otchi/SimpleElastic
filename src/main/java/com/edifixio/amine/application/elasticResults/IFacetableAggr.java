package com.edifixio.amine.application.elasticResults;

public interface IFacetableAggr extends Aggr {
	public  Boolean isTermAggr();

	public  TermAggr getAsTermAggr();

	public  Boolean isRangeAggr();

	public  RangeAggr getAsRangeAggr();

	public  FacetableAggr getDataCopy();

}
