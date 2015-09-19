package com.edifixio.amine.application.elasticResults;

import java.util.Map;

public class RangeAggr extends  FacetableAggr{

	/********************************************************************************/
	public RangeAggr(Map<String,Bucket> buckets) {
		super(buckets);
		
	}
	/*****************************************************************************/
	@Override
	public boolean isTermAggr() {
		return false;
	}
	
	@Override
	public TermAggr getAsTermAggr() {
		return null;
	}

	@Override
	public boolean isRangeAggr() {
		return true;
	}

	@Override
	public RangeAggr getAsRangeAggr() {
		return this;
	}


	@Override
	public FacetableAggr getDataCopy() {
		return new RangeAggr(super.getMapCopy());
	}
	
	
}
