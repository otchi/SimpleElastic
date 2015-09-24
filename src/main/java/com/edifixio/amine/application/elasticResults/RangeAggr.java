package com.edifixio.amine.application.elasticResults;

import java.util.Map;

public class RangeAggr extends FacetableAggr{

	/********************************************************************************/
	public RangeAggr() {
		super();
		
	}
	public RangeAggr(Map<String,Bucket> buckets) {
		super(buckets);
		
	}
	/*****************************************************************************/
	@Override
	public Boolean isTermAggr() {
		return false;
	}
	
	@Override
	public TermAggr getAsTermAggr() {
		return null;
	}

	@Override
	public Boolean isRangeAggr() {
		return true;
	}

	@Override
	public RangeAggr getAsRangeAggr() {
		return this;
	}


	
	
	
}
