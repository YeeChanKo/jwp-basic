package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        
        for(Method m : clazz.getMethods()){
        	if(m.isAnnotationPresent(MyTest.class)){
        		m.invoke(clazz.newInstance());
        	}
        }
    }
}
