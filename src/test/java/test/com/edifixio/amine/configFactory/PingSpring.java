package test.com.edifixio.amine.configFactory;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.object.TestObject;
import com.edifixio.amine.object.TestSpring;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;


public class PingSpring{
	
	private ApplicationContext context ;
	
	
	@Before
	public void init(){
		context= new FileSystemXmlApplicationContext(
				TestRessourcesLoader.getPathRessource(this.getClass(),"BeansPing.xml"));
	}
	
	@Test
	public void ping(){
		TestSpring testSpring=((TestSpring)context.getBean("ping"));
		Assert.assertEquals("amine",testSpring.getMessage());
		//System.out.println( testSpring.getC());
		@SuppressWarnings("unchecked")
		List<TestObject> testSpring1=((List<TestObject>)context.getBean("myList"));
		System.out.println(testSpring1);
	}

}
