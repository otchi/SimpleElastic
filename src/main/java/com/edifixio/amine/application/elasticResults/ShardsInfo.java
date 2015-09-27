package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class ShardsInfo {
	private static final String TOTAL="total";
	private static final String SUCCESS= "successful";
	private static final String FAILED= "failed";
	
	private Integer total;
	private Integer success;
	private Integer failed;
	
	public ShardsInfo() {
		super();
	}
	
	public ShardsInfo(Integer total, Integer success, Integer failed) {
		super();
		this.total = total;
		this.success = success;
		this.failed = failed;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getSuccess() {
		return success;
	}
	
	public void setSuccess(Integer success) {
		this.success = success;
	}
	
	public Integer getFailed() {
		return failed;
	}
	
	public void setFailed(Integer failed) {
		this.failed = failed;
	}
	
	public static ShardsInfo getShardsInfo(JsonObject jsonObject){
		if(jsonObject==null) return  null;
		
		return new ShardsInfo(	jsonObject.has(TOTAL) ? jsonObject.get(TOTAL).getAsInt() : null,
								jsonObject.has(SUCCESS) ? jsonObject.get(SUCCESS).getAsInt() : null,
								jsonObject.has(FAILED) ? jsonObject.get(FAILED).getAsInt() : null);
		
	}

	@Override
	public String toString() {
		return "ShardsInfo [total=" + total + ", success=" + success + ", failed=" + failed + "]";
	}
	
	
	
}
