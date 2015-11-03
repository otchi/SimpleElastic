package com.edifixio.simplElastic.utils;

public class Duo<First, Seconde> {
	
	private First first;
	private Seconde seconde;
	/**********************************************************************/
	public Duo() {
		super();
	}
	public Duo(First first, Seconde seconde) {
		super();
		this.first = first;
		this.seconde = seconde;
	}
	
	/**********************************************************************/
	public First getFirst() {
		return first;
	}
	public void setFirst(First first) {
		this.first = first;
	}
	public Seconde getSeconde() {
		return seconde;
	}
	public void setSeconde(Seconde seconde) {
		this.seconde = seconde;
	}

	/************************************************************************/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+first.toString()+","+seconde.toString()+"]";
	}


}
