package test.com.edifixio.simplElastic.AOPandCGlib;

import java.io.File;

public class TestRessourcesLoader {

	public static String getPathRessource(Class<?> relativeClass, String resource) {
		return relativeClass.getClassLoader().getResource(resource).toString();
	}

	public static File loadRessource(Class<?> relativeClass, String resource) {

			return new File(relativeClass.getClassLoader().getResource(resource).getFile());
	
	
	}

}
