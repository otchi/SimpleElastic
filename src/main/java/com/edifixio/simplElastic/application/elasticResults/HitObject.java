package com.edifixio.simplElastic.application.elasticResults;

public class HitObject {
	
	private MetaSource metasSources;
	private Object sourceObject;
	
	public HitObject() {
		super();
	}

	public HitObject(MetaSource metasSources, Object sourceObject) {
		super();
		this.metasSources = metasSources;
		this.sourceObject = sourceObject;
	}

	public MetaSource getMetasSources() {
		return metasSources;
	}

	public void setMetasSources(MetaSource metasSources) {
		this.metasSources = metasSources;
	}

	public Object getSourceObject() {
		return sourceObject;
	}

	public void setSourceObject(Object sourceObject) {
		this.sourceObject = sourceObject;
	}

	@Override
	public String toString() {
		return "HitObject [metasSources=" + metasSources + ", sourceObject=" + sourceObject + "]";
	}
	
	
}
