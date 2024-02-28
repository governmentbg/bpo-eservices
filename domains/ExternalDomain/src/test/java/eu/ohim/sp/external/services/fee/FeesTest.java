package eu.ohim.sp.external.services.fee;

import eu.ohim.sp.external.domain.epayment.Fee;
import eu.ohim.sp.external.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 03/02/14
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class FeesTest {

    @Test
    public void testCalculateApplicationFees() throws IntrospectionException {
        JavaBeanTester.test(CalculateApplicationFees.class);
    }

    @Test
    public void testCalculateApplicationFeesResponse() throws IntrospectionException {
        JavaBeanTester.test(CalculateApplicationFeesResponse.class);
    }

    @Test
    public void testFees() throws IntrospectionException {
        JavaBeanTester.test(Fee.class);
    }

    @Test
    public void testFeeFault() throws IntrospectionException {
        JavaBeanTester.test(FeeFault.class);
    }

}
