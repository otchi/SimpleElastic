package test.com.edifixio.amine.AOPandCGlib;

public class OriginalObject {
	private String messageTest;

	public String getMessageTest() {
		System.out.println("getMessageTest");
		return messageTest;
	}

	public void setMessageTest(String messageTest) {
		System.out.println("setMessageTest");
		this.messageTest = messageTest;
	}
	
	

}
