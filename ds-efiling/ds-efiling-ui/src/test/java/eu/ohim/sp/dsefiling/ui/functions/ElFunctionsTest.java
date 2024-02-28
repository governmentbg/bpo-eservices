package eu.ohim.sp.dsefiling.ui.functions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;

public class ElFunctionsTest
{
    @Test
    public void getLinkedDesignNumbersTest1()
    {
        DesignerForm designer = new DesignerForm();
        designer.setDesignsLinked(new ArrayList<DesignForm>());

        String result = ElFunctions.getLinkedDesignsNumbers(designer);

        assertEquals("", result);
    }

    @Test
    public void getLinkedDesignNumbersTest2()
    {
        DesignerForm designer = new DesignerForm();
        designer.setDesignsLinked(new ArrayList<DesignForm>());

        DesignForm d1 = new DesignForm();
        d1.setNumber(12);

        DesignForm d2 = new DesignForm();
        d2.setNumber(33);

        designer.getDesignsLinked().add(d1);
        designer.getDesignsLinked().add(d2);


        String result = ElFunctions.getLinkedDesignsNumbers(designer);

        assertEquals("12, 33", result);
    }

    @Test
    public void getLabelDesignViewTypeTest1()
    {
        DesignViewForm form = new DesignViewForm();

        List<DesignViewTypes.DesignViewType> types = new ArrayList<DesignViewTypes.DesignViewType>();

        String result = ElFunctions.getLabelDesignViewType(form, types);

        assertEquals("", result);
    }

    @Test
    public void getLabelDesignViewTypeTest2()
    {
        DesignViewForm form = new DesignViewForm();
        form.setType("code1");

        List<DesignViewTypes.DesignViewType> types = new ArrayList<DesignViewTypes.DesignViewType>();
        types.add(new DesignViewTypes.DesignViewType("code1", "value1", 1));
        types.add(new DesignViewTypes.DesignViewType("code2", "value2", 1));

        String result = ElFunctions.getLabelDesignViewType(form, types);

        assertEquals("value1", result);
    }

    @Test
    public void getDifferentLocarnoClassificationsTest1()
    {
        DesignForm form = new DesignForm();
        form.setLocarno(new ArrayList<LocarnoAbstractForm>());
        LocarnoForm l1 = new LocarnoForm();
        l1.setLocarnoClassification(new LocarnoClassification("id", "2", "3", "indication"));
        form.getLocarno().add(l1);

        Map<String, String> result = ElFunctions.getDifferentLocarnoClassifications(form);

        assertEquals("indication", result.get("2.3"));
    }

    @Test
    public void getDifferentLocarnoClassificationsTest2()
    {
        DesignForm form = new DesignForm();
        form.setLocarno(new ArrayList<LocarnoAbstractForm>());
        LocarnoForm l1 = new LocarnoForm();
        l1.setLocarnoClassification(new LocarnoClassification("id", "2", "3", "indication 1"));
        LocarnoForm l2 = new LocarnoForm();
        l2.setLocarnoClassification(new LocarnoClassification("id", "2", "3", "indication 2"));
        form.getLocarno().add(l1);
        form.getLocarno().add(l2);

        Map<String, String> result = ElFunctions.getDifferentLocarnoClassifications(form);

        assertEquals("indication 1, indication 2", result.get("2.3"));
    }

    @Test
    public void isMultipleDesignApplicationTest1()
    {
        DSFlowBeanImpl flowbean = new DSFlowBeanImpl();
        flowbean.setDesigns(new ArrayList<DesignForm>());
        flowbean.getDesigns().add(new DesignForm());

        Boolean result = ElFunctions.isMultipleDesignApplication(flowbean);

        assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void isMultipleDesignApplicationTest2()
    {
        DSFlowBeanImpl flowbean = new DSFlowBeanImpl();
        flowbean.setDesigns(new ArrayList<DesignForm>());
        flowbean.getDesigns().add(new DesignForm());
        flowbean.getDesigns().add(new DesignForm());

        Boolean result = ElFunctions.isMultipleDesignApplication(flowbean);

        assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void isOneDesignApplicationTest1()
    {
        DSFlowBeanImpl flowbean = new DSFlowBeanImpl();
        flowbean.setDesigns(new ArrayList<DesignForm>());
        flowbean.getDesigns().add(new DesignForm());
        flowbean.getDesigns().add(new DesignForm());

        Boolean result = ElFunctions.isOneDesignApplication(flowbean);

        assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void isOneDesignApplicationTest2()
    {
        DSFlowBeanImpl flowbean = new DSFlowBeanImpl();
        flowbean.setDesigns(new ArrayList<DesignForm>());
        flowbean.getDesigns().add(new DesignForm());

        Boolean result = ElFunctions.isOneDesignApplication(flowbean);

        assertEquals(Boolean.TRUE, result);
    }
}
