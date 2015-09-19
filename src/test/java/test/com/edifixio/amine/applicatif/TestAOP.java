package test.com.edifixio.amine.applicatif;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class TestAOP {
	
	
	public static Object redirected() {
		Foo foo = new TestAOP().new Foo();
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(foo);

		pf.addAdvice(new MethodInterceptor() {
			public Object invoke(MethodInvocation mi) throws Throwable {
				if (mi.getMethod().getName().startsWith("get")) {
					Method redirect = mi.getThis().getClass().getMethod("setBar", String.class);
					if(mi.getMethod().invoke(mi.getThis())==null)
						redirect.invoke(mi.getThis(), "btte");
				}else{
					mi.getMethod().invoke(mi.getThis(), mi.getArguments()[0]);
				}
				return mi.getMethod().invoke(mi.getThis());
			}
			
			
		});
		return   pf.getProxy();
	}
	
	public class Foo {
		
		private String bar;

		public void setBar(String bar) {
			System.out.println("i m in the setter");
			// throw new UnsupportedOperationException("should not go here");
			this.bar = bar;
		}

		public String getBar() {
			System.out.println("i m in the getter");
			// throw new UnsupportedOperationException("should not go here");
			return bar;
		}


	}

//	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//	
//
//		Foo foo=(Foo)TestAOP.redirected();
//		Class<?> cl=foo.getClass();
//		
//		System.out.println(cl.getMethod("getBar").invoke(foo));
//		System.out.println(cl.getMethod("getBar").invoke(foo));
//		foo.setBar("dd");
//		//System.out.println(cl.getMethod("setBar",String.class).invoke(foo,"batati"));
//		
//		System.out.println(".cc.ff.ds".replaceAll("\\.", "::"));
//		System.out.println("cdaf".substring(3, 4));
//		String s="ddDccd";
//		System.out.println(s.toLowerCase());
//		System.out.println(s);
//		
//
//	}

}
