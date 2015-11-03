package com.edifixio.simplElastic.application.elasticResults;

public interface ICutRef<Type> {

	public Type getCopy();
	public void update(Type object);
}
