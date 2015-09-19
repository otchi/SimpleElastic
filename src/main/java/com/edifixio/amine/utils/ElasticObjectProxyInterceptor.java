package com.edifixio.amine.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.edifixio.amine.application.SimpleResponseConfigUnit;
import com.edifixio.amine.application.SimpleRootConfig;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ElasticObjectProxyInterceptor implements MethodInterceptor {

	private Object originalElasticObject;
	private Map<String, SimpleResponseConfigUnit> lazyModeUnit=new HashMap<String, SimpleResponseConfigUnit>();
	private String sourceId;
	private String index;
	private String type;
	
	public ElasticObjectProxyInterceptor(Object originalElasticObject,Map<String, SimpleResponseConfigUnit> lazyModeUnit,
			String sourceId,String index, String type) {
		super();
		this.originalElasticObject = originalElasticObject;
		this.lazyModeUnit=lazyModeUnit;
		this.sourceId=sourceId;
		this.index=index;
		this.type=type;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy arg3) 
			throws Throwable {
		
		String methodName=method.getName();
		//System.out.println("++++++"+methodName);
		if (methodName.startsWith("get")) {
			String methodeFieldName=methodName.substring(3);
			String fieldName=methodeFieldName.toLowerCase();
			//System.out.println("++++++"+methodeFieldName);
			//System.out.println("----->>"+lazyModeUnit);
			if(lazyModeUnit.containsKey(fieldName)){
				//System.out.println(fieldName+"//"+originalElasticObject+"//"+sourceId+"//"+ index+"//"+ type);
				//System.out.println(lazyModeUnit.get(fieldName));
				lazyModeUnit.get(fieldName).lazyModeLoading(originalElasticObject, sourceId, index, type,SimpleRootConfig.CLIENT);
			}
		}
		
		return method.invoke(originalElasticObject, args);
	}
	
	public static <T> T createProxy(T obj,Map<String, SimpleResponseConfigUnit> lazyModeUnit,
			String sourceId,String index, String type) {

		// this is the main cglib api entry-point
		// this object will 'enhance' (in terms of CGLIB) with new capabilities
		// one can treat this class as a 'Builder' for the dynamic proxy
		
		Enhancer e = new Enhancer();
		// the class will extend from the real class
		
		e.setSuperclass(obj.getClass());
		// we have to declare the interceptor - the class whose 'intercept'
		// will be called when any method of the proxified object is called.

		e.setCallback(new ElasticObjectProxyInterceptor(obj, lazyModeUnit, sourceId, index, type));
		// now the enhancer is configured and we'll create the proxified objects

		@SuppressWarnings("unchecked")
		T proxifiedObj = (T) e.create();
		// the object is ready to be used - return it

		return proxifiedObj;
	}
	

}
