package com.edifixio.amine.application.elasticResults;

import java.util.List;
import java.util.Map;
public class ApplicationReturn {
	private ReturnMetas returnMetas;
	private List<HitObject> HitObjectList;
	private AggrsReturnObject aggrs;
	
	
	public ApplicationReturn() {
		super();

	}
	
	
	public ApplicationReturn(ReturnMetas returnMetas, List<HitObject> hitObjectList, Map<String, FacetableAggr> factes) {
		super();
		this.returnMetas = returnMetas;
		HitObjectList = hitObjectList;
		this.aggrs = new AggrsReturnObject(factes);
	}


	public List<HitObject> getHitObjectList() {
		return HitObjectList;
	}

	public AggrsReturnObject getAggrs() {
		return aggrs;
	}

	public void setAggrs(AggrsReturnObject aggrs) {
		this.aggrs = aggrs;
	}

	public void setHitObjectList(List<HitObject> hitObjectList) {
		this.HitObjectList = hitObjectList;
	}


	public ReturnMetas getReturnMetas() {
		return returnMetas;
	}


	@Override
	public String toString() {
		return "ApplicationReturn [returnMetas=" + returnMetas + ", HitObjectList=" + HitObjectList + ", aggrs=" + aggrs
				+ "]";
	}
	

	


	
	

}
