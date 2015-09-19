package test.com.edifixio.amine.applicatif;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import test.com.edifixio.amine.AOPandCGlib.TestRessourcesLoader;


public class SpringInjectionTest {
	
	private ApplicationContext context;
	
	/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext(
					TestRessourcesLoader
						.getPathRessource(
								this.getClass(),"BeansApplication.xml"));
	
	}
	
	/******************************************************************************************/
	@Test
	public void Test() {
		//try {
			
			//DeclaredJsonObjectConfigFactory djocf =
				//	(DeclaredJsonObjectConfigFactory)
							context.getBean("main_config");
			/*JsonObject jo = JsonHandleUtil.jsonFile(
					Ressources.JSON_QUERIES+"config_voiture.json").getAsJsonObject();
			SimpleRootConfig src = (SimpleRootConfig) djocf.getJsonElementConfig(jo);
			JsonObject joq = JsonHandleUtil.jsonString("{query:{match_all:{}}}").getAsJsonObject();
			TestObject to = new TestObject();
			
			
			System.out.println(joq);
			src.process(joq,null);
			joq = jo.get("_query").getAsJsonObject();
			System.out.println(joq);
			src.process(joq,null);
			to.setField1("audi");
			src.process(joq, to);
		
			Assert.assertTrue(true);
			
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), false);
		}*/
	}

}
