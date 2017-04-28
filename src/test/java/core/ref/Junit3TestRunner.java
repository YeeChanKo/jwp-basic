package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        
        for(Method m : clazz.getMethods()){
        	if(m.getName().startsWith("test")){
        		m.invoke(clazz.newInstance());
        	}
        }
    }
}
