package test.com.edifixio.amine.applicatif;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.edifixio.amine.application.SimpleJsonBooleanConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleResponseConfigUnit;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.TestObject;
import com.edifixio.amine.utils.ElasticClient;
import com.edifixio.amine.utils.JsonPathTree;

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
