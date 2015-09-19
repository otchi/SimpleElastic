package test.com.edifixio.amine.AOPandCGlib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MyInterceptor implements MethodInterceptor {

	private Object originalObject;

	public MyInterceptor(Object originalObject) {
		super();
		this.originalObject = originalObject;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy arg3) throws Throwable {
		if(method.getName().equals("getMessageTest")){
			if(method.invoke(originalObject, args)==null){
				originalObject.getClass().getMethod("setMessageTest", String.class).invoke(originalObject, "cc");
			}
		}
		
		return method.invoke(originalObject, args);
	}

	public static <T> T createProxy(T obj) {

		// this is the main cglib api entry-point

		// this object will 'enhance' (in terms of CGLIB) with new capabilities

		// one can treat this class as a 'Builder' for the dynamic proxy

		Enhancer e = new Enhancer();

		// the class will extend from the real class

		e.setSuperclass(obj.getClass());

		// we have to declare the interceptor - the class whose 'intercept'

		// will be called when any method of the proxified object is called.

		e.setCallback(new MyInterceptor(obj));

		// now the enhancer is configured and we'll create the proxified object

		@SuppressWarnings("unchecked")
		T proxifiedObj = (T) e.create();

		// the object is ready to be used - return it

		return proxifiedObj;
	}
	
	
	public static void main(String args[]){
		OriginalObject originalObject=new OriginalObject();
		OriginalObject proxyObject=createProxy(originalObject);
		System.out.println(proxyObject.getMessageTest());
		proxyObject.setMessageTest("tt");
		System.out.println(proxyObject.getMessageTest());
	}
}
