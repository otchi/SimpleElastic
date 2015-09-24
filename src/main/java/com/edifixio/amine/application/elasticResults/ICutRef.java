package com.edifixio.amine.application.elasticResults;

public interface ICutRef<Type> {

	public Type getCopy();
	public void update(Type object);
}
