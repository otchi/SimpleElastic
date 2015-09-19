package com.edifixio.amine.object;

public class ComplexTestResponseObject {
	
	private TestObject to;
	private String str;
	private int nbr;
	public TestObject getTo() {
		return to;
	}
	public void setTo(TestObject to) {
		this.to = to;
	}

	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getNbr() {
		return nbr;
	}
	public void setNbr(int nbr) {
		this.nbr = nbr;
	}
	
	
	@Override
	public String toString() {
		return "ComplexTestResponseObject [to=" + to + ", str=" + str + ", nbr=" + nbr + "]";
	}
	
	
	
	
	
	
	

}
