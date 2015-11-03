package com.edifixio.simplElastic.utils;

public class JsonPathTree extends AbstractSimpleTreeNode<String> {
	
	private boolean isLazy;
	
	public JsonPathTree(String name, String parentPath,boolean isLazy) {
		super(name, parentPath);
		this.isLazy=isLazy;
	}
	
	public boolean isLazy() {
		return isLazy;
	}
	public void setLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}

	@Override
	public String toString() {
		return "JsonPathTree [isLazy=" + isLazy + ", name=" + name + ", element=" + element + ", childs=" + childs
				+ "]";
	}	
	
	
	
	
	

	
	

}
