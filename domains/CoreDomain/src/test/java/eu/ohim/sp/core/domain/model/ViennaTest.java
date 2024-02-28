package eu.ohim.sp.core.domain.model;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class ViennaTest {

	@Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(Vienna.class);
    }

}
