package com.edifixio.simplElastic.application.elasticResults;

import com.google.gson.JsonElement;
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
		Integer total = null;
		Integer success = null;
		Integer failed = null;
		
		if(jsonObject.has(TOTAL)){
			JsonElement je=jsonObject.get(TOTAL);
			if(je.isJsonPrimitive()){
				if(je.getAsJsonPrimitive().isNumber())
					total=je.getAsInt();
			}	
		}
		
		if(jsonObject.has(SUCCESS)){
			JsonElement je=jsonObject.get(SUCCESS);
			if(je.isJsonPrimitive()){
				if(je.getAsJsonPrimitive().isNumber())
					success=je.getAsInt();
			}	
		}
		
		if(jsonObject.has(FAILED)){
			JsonElement je=jsonObject.get(FAILED);
			if(je.isJsonPrimitive()){
				if(je.getAsJsonPrimitive().isNumber())
					failed=je.getAsInt();
			}	
		}
		
		return new ShardsInfo(	total,success,failed);
		
	}

	@Override
	public String toString() {
		return "ShardsInfo [total=" + total + ", success=" + success + ", failed=" + failed + "]";
	}
	
	
	
}
