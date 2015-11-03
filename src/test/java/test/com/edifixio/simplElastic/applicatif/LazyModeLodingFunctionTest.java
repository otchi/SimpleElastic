package test.com.edifixio.simplElastic.applicatif;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.edifixio.simplElastic.application.SimpleJsonBooleanConfig;
import com.edifixio.simplElastic.application.SimpleJsonStringConfig;
import com.edifixio.simplElastic.application.SimpleResponseConfigUnit;
import com.edifixio.simplElastic.config.JsonElementConfig;
import com.edifixio.simplElastic.object.TestObject;
import com.edifixio.simplElastic.utils.ElasticClient;
import com.edifixio.simplElastic.utils.JsonPathTree;

public class LazyModeLodingFunctionTest {
	
	private SimpleResponseConfigUnit srcu;
	
	@Test
	public void test() throws SecurityException, IllegalArgumentException, IOException, ReflectiveOperationException{
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		mapConfig.put("name", new SimpleJsonStringConfig("field1"));
		mapConfig.put("lazy", new SimpleJsonBooleanConfig(true));
		srcu=new SimpleResponseConfigUnit(mapConfig);
		Field field=this.srcu.getClass().getDeclaredField("jsonPathTree");
		field.setAccessible(true);
		JsonPathTree jpt=new JsonPathTree("name", "kta", true);
		field.set(srcu, jpt);
		TestObject to=new TestObject();
		srcu.lazyModeLoading(to, "2", "bibliothéque", "histoire",ElasticClient.getElasticClient("http://localhost:9200").getClient());
		System.out.println(to);
	}

}
