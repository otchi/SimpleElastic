package test.com.edifixio.amine.applicatif;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.application.MappingRequestResolver;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MappingRequestResolverTest {
	private MappingRequestResolver mrp;
	@Before
	public void init(){
		 mrp=new MappingRequestResolver(new JsonObject());
	}
	public void display(int[][] tab){
		if(tab==null) return;
		for(int i=0;i<tab.length;i++)
			System.out.print(tab[i][0]+"-"+tab[i][1]+"\t");
		
		System.out.println("\n______________________");
	}
	@Test
	public void testIndexer(){
		int [][] tab;
		System.out.println("\n"
				+ "**************************** begin indexer test"
				+ "*******************************");	
		tab=mrp.indexer("");
		display(tab);
		Assert.assertNull(tab);
		
		tab=mrp.indexer("$");
		display(tab);
		Assert.assertNull(tab);
		
		tab=mrp.indexer("sq$ss");
		display(tab);
		Assert.assertNull(tab);
		
		tab=mrp.indexer("hh");
		display(tab);
		Assert.assertNull(tab);
		
		tab=mrp.indexer("${bb}");
		display(tab);
		Assert.assertEquals(tab.length, 1);
		
		tab=mrp.indexer("hh${bb}");
		display(tab);
		Assert.assertEquals(tab.length, 1);
		
		tab=mrp.indexer("hh${bb}m${bb}");
		display(tab);
		Assert.assertNull(tab);
		
		
		tab=mrp.indexer("hh${bb}");
		display(tab);
		Assert.assertEquals(tab.length, 1);
		
		
		tab=mrp.indexer("${bb}far");
		display(tab);
		Assert.assertEquals(tab.length, 1);
		
		
		tab=mrp.indexer("${bd}");
		display(tab);
		Assert.assertEquals(tab.length, 1);
		

		tab=mrp.indexer("${bl}dd${bc}dd${km}ff");
		display(tab);
		Assert.assertEquals(tab.length, 3);
		
		tab=mrp.indexer("${bl}dd${bb}dd${km}");
		display(tab);
		Assert.assertEquals(tab.length, 3);
		
		tab=mrp.indexer("${bl}${bb}${km}");
		display(tab);
		Assert.assertEquals(tab.length, 3);
		
		tab=mrp.indexer("${bl}${bb}${km}dsq");
		display(tab);
		Assert.assertEquals(tab.length, 3);
		
		tab=mrp.indexer("sss${bl}${bb}${km}");
		display(tab);
		Assert.assertEquals(tab.length, 3);
		
		System.out.println("\n"
				+ "****************************end indexer test"
				+ "*******************************");	
	}
	
	
	@Test
	public void decoderTest(){
		
		mrp.stringParser("p}${b", "cc::p");
		Assert.assertNull(mrp.getCorresp().get("b"));
		Assert.assertNull(mrp.getCorresp().get("p"));
		mrp.stringParser("fds${p}${b}", "cc::p");
		Assert.assertNotNull(mrp.getCorresp().get("b"));
		Assert.assertNotNull(mrp.getCorresp().get("p"));
		mrp.stringParser("${c}", "cc::p");
		Assert.assertNotNull(mrp.getCorresp().get("c"));
		//Assert.assertEquals("b",mrp.getCorresp().get(0));
	}
	
	@Test
	public void parsingResolverTest(){
		System.out.println("\n"
				+ "**************************** begin parsing resolver"
				+ " *******************************");
		JsonObject jo=	new JsonParser()
				.parse("{pst:\"base\","
						+ "pator:{bsm:[\"bd\",1,\"${kf}l\"],"
						+ "pdf:\"${pkh}\""
						+ "}}").getAsJsonObject();
		mrp=new MappingRequestResolver(jo);
		mrp.parsing();
		
		System.out.println(mrp.getCorresp());
		
		Assert.assertEquals("pator::bsm::2",mrp.getCorresp().get("kf").getKey());
		Assert.assertEquals("pator::pdf",mrp.getCorresp().get("pkh").getKey());
		System.out.println("--------- resolving ----------------");
		mrp.varResolver("kf", "55");
		mrp.varResolver("pkh", "66");
		Assert.assertEquals("55l", 
				JsonHandleUtil.seletor("pator::bsm::2",
						mrp.getJsonObject()).getAsString());
		Assert.assertEquals("66", 
				JsonHandleUtil.seletor("pator::pdf",
						mrp.getJsonObject()).getAsString());
		Assert.assertNull(mrp.getCorresp().get("kf"));
		Assert.assertNull(mrp.getCorresp().get("pkh"));
		System.out.println(jo);
		
		System.out.println("\n"
				+ "**************************** end parsing resolver "
				+ "*******************************");
	}

}
