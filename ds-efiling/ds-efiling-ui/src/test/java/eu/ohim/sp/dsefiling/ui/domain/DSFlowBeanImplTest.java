package eu.ohim.sp.dsefiling.ui.domain;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DSFlowBeanImplTest
{
    DSFlowBeanImpl flowBean;

    @Before
    public void setUp()
    {
        flowBean = new DSFlowBeanImpl();
    }

    @Test
    public void getUserDataDesignersTest()
    {
        DesignerForm d1 = new DesignerForm();
        d1.setCurrentUserIndicator(true);
        d1.setFirstName("first");

        DesignerForm d2 = new DesignerForm();
        d2.setCurrentUserIndicator(false);
        d2.setFirstName("second");

        DesignerForm d3 = new DesignerForm();
        d3.setCurrentUserIndicator(true);
        d3.setFirstName("third");

        flowBean.getDesigners().add(d1);
        flowBean.getDesigners().add(d2);
        flowBean.getDesigners().add(d3);

        List<DesignerForm> result = flowBean.getUserDataDesigners();

        assertEquals(2, result.size());
        assertEquals("first", result.get(0).getFirstName());
        assertEquals("third", result.get(1).getFirstName());
    }


    @Test
    public void getAddedDesignersTest()
    {
        DesignerForm d1 = new DesignerForm();
        d1.setCurrentUserIndicator(false);
        d1.setFirstName("first");

        DesignerForm d2 = new DesignerForm();
        d2.setCurrentUserIndicator(false);
        d2.setFirstName("second");

        DesignerForm d3 = new DesignerForm();
        d3.setCurrentUserIndicator(true);
        d3.setFirstName("third");

        flowBean.getDesigners().add(d1);
        flowBean.getDesigners().add(d2);
        flowBean.getDesigners().add(d3);

        List<DesignerForm> result = flowBean.getAddedDesigners();

        assertEquals(2, result.size());
        assertEquals("first", result.get(0).getFirstName());
        assertEquals("second", result.get(1).getFirstName());
    }

    @Test
    public void getPersonsTest()
    {
        ApplicantForm a1 = new ApplicantForm();
        a1.setName("app name");

        RepresentativeForm r1 = new RepresentativeForm();
        r1.setName("rep name");

        flowBean.addObject(a1);
        flowBean.addObject(r1);

        List<PersonForm> result = flowBean.getPersons();

        assertEquals(2, result.size());
        assertEquals("app name", result.get(0).getName());
        assertEquals("rep name", result.get(1).getName());
    }

}
