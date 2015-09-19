package com.edifixio.amine.application.elasticResults;

public enum FacetableAggrType {
	RANGE("range",0),TERMS("terms",1);

	private int index;
	private String name;
	
	
	private FacetableAggrType(String name,int index) {
		this.name = name;
		this.index=index;
	}

	public String getName() {
		return name;
	}
	public static FacetableAggrType getByName(String name){
		FacetableAggrType[] values=FacetableAggrType.values();
		for(FacetableAggrType val:values){
			if(val.name.equals(name)) return val;
		}
		System.out.println("FacetableAggrTypeException ~");
		return null;
	}
	
	public int getPosition(){
		if(FacetableAggrType.RANGE.getPosition()==0);
		return 0;
	}

	public int getIndex() {
		return index;
	}	
	
	
}
