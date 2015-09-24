package com.edifixio.amine.application.elasticResults;

public enum FacetableAggrType {
	RANGE("range"),TERMS("terms");

	private int index;
	private String name;
	
	
	private FacetableAggrType(String name) {
		this.name = name;
		if(name.equals("range")) index=0;
		if(name.equals("terms")) index=1;
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
