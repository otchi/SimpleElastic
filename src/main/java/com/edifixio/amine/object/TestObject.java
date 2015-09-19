package com.edifixio.amine.object;

public class TestObject {
	private String field1;
	private Boolean field2;
	private Integer field3;
	

	public TestObject() {
		super();
	}

	
	public TestObject(String field1, Boolean field2, Integer field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public Boolean getField2() {
		return field2;
	}
	public void setField2(Boolean field2) {
		this.field2 = field2;
	}
	
	public Integer getField3() {
		return field3;
	}
	public void setField3(Integer field3) {
		this.field3 = field3;
	}


	@Override
	public String toString() {
		return "TestObject [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}

	

	


}
