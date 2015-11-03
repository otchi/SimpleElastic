package test.com.edifixio.simplElastic.application.elasticResults;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.edifixio.simplElastic.application.elasticResults.Bucket;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BucketTest {
	public static JsonParser JP=new JsonParser();
	private JsonObject jsonObject;
	
	@Before
	public void init(){
		jsonObject=JP.parse("{"
				+ "doc_count: 20"
				+ "}").getAsJsonObject();
		
	}
	
	@Test
	public void test(){
		Bucket b=Bucket.getBucket(jsonObject);
		Assert.assertEquals(b.getCount(),(Integer) 20);
	
	}
	
	
}
