package com.edifixio.amine.utils;

public class IntegerDuo extends Duo<Integer, Integer>{

	/*******************************************************/
	public IntegerDuo(Integer first, Integer seconde) {
		super(first, seconde);
		// TODO Auto-generated constructor stub
	}
	
	/*********************************************************/
	public void incrementAll(Integer incrementWith){
		this.setFirst(getFirst()+incrementWith);
		this.setSeconde(getSeconde()+incrementWith);
	}

}
