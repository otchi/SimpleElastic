package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.utils.ConfigFactoryUtiles;

@RunWith(Parameterized.class)
public class ConfigFactoryUtilesTest {
	
	private Class<?> superClass;
	private Class<?> subClass;
	private int expectedResult;
	
	public ConfigFactoryUtilesTest( int expectedResult ,Class<?> superClass, Class<?> subClass) {
		super();
		this.superClass = superClass;
		this.subClass = subClass;
		this.expectedResult = expectedResult;
	}
	@Parameterized.Parameters
	public static Collection<?> inputClass(){
	return Arrays.asList(new Object[][]{
		{0,String.class,String.class},
		{1,Object.class,String.class},
		{1,JsonObjectConfig.class,SimpleRootConfig.class}
	});
	}
	@Test
	public void testInherited(){
		//System.out.println(expectedResult);
		Assert.assertEquals(expectedResult, ConfigFactoryUtiles.inherited(superClass, subClass));
		
	
	}

}
