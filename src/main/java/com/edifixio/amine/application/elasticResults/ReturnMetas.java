package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class ReturnMetas {
	
	private static final String TOOK="took";
	private static final String TIME_OUT="timed_out";
	private static final String SHARDS="_shards";
	private static final String HITS="hits";
	
	private Integer took;
	private Boolean timeOut;
	private ShardsInfo shardsInfo;
	private MetaHits metaHits;
	
	public ReturnMetas() {
		super();
	}

	
	public ReturnMetas(Integer took, Boolean timeOut, ShardsInfo shardsInfo, MetaHits metaHits) {
		super();
		this.took = took;
		this.timeOut = timeOut;
		this.shardsInfo = shardsInfo;
		this.metaHits = metaHits;
	}


	public Integer getTook() {
		return took;
	}

	public void setTook(Integer took) {
		this.took = took;
	}

	public Boolean getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Boolean timeOut) {
		this.timeOut = timeOut;
	}

	public ShardsInfo getShardsInfo() {
		return shardsInfo;
	}

	public void setShardsInfo(ShardsInfo shardsInfo) {
		this.shardsInfo = shardsInfo;
	}


	public MetaHits getMetaHits() {
		return metaHits;
	}


	public void setMetaHits(MetaHits metaSetSource) {
		this.metaHits = metaSetSource;
	}
	
	
	public static ReturnMetas getReturnMetas(JsonObject jsonObject){
		if(jsonObject==null) return null;
		
		return new ReturnMetas(	jsonObject.has(TOOK) ? jsonObject.get(TOOK).getAsInt() : null,
								jsonObject.has(TIME_OUT) ? jsonObject.get(TIME_OUT).getAsBoolean() : null,
								jsonObject.has(SHARDS) ? ShardsInfo.getShardsInfo(jsonObject.get(SHARDS).getAsJsonObject()): null ,
								jsonObject.has(HITS) ? MetaHits.getMetaHits(jsonObject.get(HITS).getAsJsonObject()) : null);
		
	}


	@Override
	public String toString() {
		return "ReturnMetas [took=" + took + ", timeOut=" + timeOut + ", shardsInfo=" + shardsInfo + ", metaHits="
				+ metaHits + "]";
	}	

}
