package com.edifixio.simplElastic.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConfigFactoryUtiles {

	public static int inherited(Class<?> superClass, Class<?> subClass) {

		if (subClass.equals(superClass))
			return 0;

		int i = 1;

		while (!subClass.equals(Object.class)) {
			if (subClass.getSuperclass().equals(superClass)) {
				return i;
			}
			i++;
			subClass = subClass.getSuperclass();
		}
		return -1;
	}
	
	public static boolean isOfType(Class<?> yourClass,Class<?> ... candidateClass){

		for(int i=0;i<candidateClass.length;i++){
			if(candidateClass[i].equals(yourClass)) return true;
		}
		return false;	
	}
	
	public static void main(String args[]){
		System.out.println(isOfType(String.class, String.class,JsonObject.class,JsonElement.class));
	}
}
