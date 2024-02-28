package eu.ohim.sp.external.domain.person;

import eu.ohim.sp.external.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 03/02/14
 * Time: 21:02
 * To change this template use File | Settings | File Templates.
 */
public class PersonTest {

    @Test
    public void testPersonCriteria() throws IntrospectionException {
        JavaBeanTester.test(PersonCriteria.class);
    }

    @Test
    public void testTransliteratedPersonName() throws IntrospectionException {
        JavaBeanTester.test(TransliteratedPersonName.class);
    }

    @Test
    public void testPersonRoleRelationship() throws IntrospectionException {
        JavaBeanTester.test(PersonRoleRelationship.class);
    }

    @Test
    public void testPersonIdentifier() throws IntrospectionException {
        JavaBeanTester.test(PersonIdentifier.class);
    }

    @Test
    public void testPersonRole() throws IntrospectionException {
        JavaBeanTester.test(PersonRole.class);
    }


}
