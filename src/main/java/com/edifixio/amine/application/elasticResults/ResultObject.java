package com.edifixio.amine.application.elasticResults;

import java.util.List;
import java.util.Map;
public class ResultObject {
	private List<Object> resultList;
	private AggrsResultObject aggsresult;
	
	
	public ResultObject() {
		super();

	}
	public ResultObject(List<Object> resultList, Map<String, FacetableAggr> facets) {
		super();
		this.resultList = resultList;
		this.aggsresult=new AggrsResultObject(facets);
	}
	public List<Object> getResultList() {
		return resultList;
	}

	public AggrsResultObject getAggsresult() {
		return aggsresult;
	}

	public void setAggsresult(AggrsResultObject aggsresult) {
		this.aggsresult = aggsresult;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

	@Override
	public String toString() {
		return "ResultObject [resultList=" + resultList + ", \n facets=" + aggsresult + "]";
	}
	
	

}
