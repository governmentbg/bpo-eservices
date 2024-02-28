package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;
import eu.ohim.sp.filing.domain.ds.*;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class LocarnoConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception
    {
        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/ds/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/ds/dozerMapping.xml");

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
    }

    @Test
    public void testConvertToFSP()
    {
        /// Arrange
        ProductIndication core = new ProductIndication();
        core.setDescription("some description");
        core.setVersion("some version");
        core.setLanguageCode("ES");

        core.setClasses(new ArrayList<ProductIndicationClass>());

        ProductIndicationClass c1 = new ProductIndicationClass();
        c1.setDescription("some class description");
        c1.setMainClass("2");
        c1.setSubClass("5");
        c1.setTerms(new ArrayList<ProductIndicationTerm>());

        ProductIndicationTerm t11 = new ProductIndicationTerm();
        t11.setId(123);
        t11.setText("term 1");

        ProductIndicationTerm t12 = new ProductIndicationTerm();
        t12.setId(456);
        t12.setText("second term");

        c1.getTerms().add(t11);
        c1.getTerms().add(t12);

        core.getClasses().add(c1);

        ProductIndicationClass c2 = new ProductIndicationClass();
        c2.setDescription("another class description");
        c2.setMainClass("7");
        c2.setSubClass("14");
        c2.setTerms(new ArrayList<ProductIndicationTerm>());

        ProductIndicationTerm t21 = new ProductIndicationTerm();
        t21.setId(789);
        t21.setText("term 1 of c2");
        c2.getTerms().add(t21);

        core.getClasses().add(c2);


        /// Act
        eu.ohim.sp.filing.domain.ds.IndicationProductType ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.IndicationProductType.class);

        /// Assert
        assertEquals("some description", ext.getIndicationProductDescription().getValue());
        assertEquals("some version", ext.getClassificationVersion());
        assertEquals("some class description",
                     ext.getClassDescriptionDetails().getClassDescription().get(0).getProductDescription().get(0).getValue());
        assertEquals("2.5", ext.getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().getValue().get(0));
        assertEquals("term 1", ext.getClassDescriptionDetails().getClassDescription().get(
                0).getClassificationTermDetails().getClassificationTerm().get(0).getClassificationTermText().getValue());
        assertEquals("123", ext.getClassDescriptionDetails().getClassDescription().get(
                0).getClassificationTermDetails().getClassificationTerm().get(0).getClassificationTermIdentifier());
        assertEquals("second term", ext.getClassDescriptionDetails().getClassDescription().get(
                0).getClassificationTermDetails().getClassificationTerm().get(1).getClassificationTermText().getValue());
        assertEquals("456", ext.getClassDescriptionDetails().getClassDescription().get(
                0).getClassificationTermDetails().getClassificationTerm().get(1).getClassificationTermIdentifier());

        assertEquals("another class description",
                     ext.getClassDescriptionDetails().getClassDescription().get(1).getProductDescription().get(0).getValue());
        assertEquals("7.14", ext.getClassDescriptionDetails().getClassDescription().get(1).getClassNumber().getValue().get(0));
        assertEquals("term 1 of c2", ext.getClassDescriptionDetails().getClassDescription().get(
                1).getClassificationTermDetails().getClassificationTerm().get(0).getClassificationTermText().getValue());
        assertEquals("789", ext.getClassDescriptionDetails().getClassDescription().get(
                1).getClassificationTermDetails().getClassificationTerm().get(0).getClassificationTermIdentifier());
    }

    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.IndicationProductType ext = new eu.ohim.sp.filing.domain.ds.IndicationProductType();
        ext.setIndicationProductDescription(new Text("some description", null));
        ext.setClassificationVersion("some version");
        ext.setClassDescriptionDetails(new ClassDescriptionDetails());
        ext.getClassDescriptionDetails().setClassDescription(new ArrayList<ClassDescription>());

        ClassDescription c1 = new ClassDescription();
        c1.setClassNumber(new ClassNumber(new ArrayList<String>()));
        c1.getClassNumber().getValue().add("6.22");
        c1.setProductDescription(new ArrayList<Text>());
        c1.getProductDescription().add(new Text("class description", null));

        c1.setClassificationTermDetails(new ClassificationTermDetailsType());
        c1.getClassificationTermDetails().setClassificationTerm(new ArrayList<ClassificationTermType>());
        c1.getClassificationTermDetails().getClassificationTerm().add(
                new ClassificationTermType("123", null, null, new Text("term 1 of 2", null)));
        c1.getClassificationTermDetails().getClassificationTerm().add(
                new ClassificationTermType("456", null, null, new Text("term 2 of 2", null)));


        ext.getClassDescriptionDetails().getClassDescription().add(c1);

        /// Act
        ProductIndication core = dozerBeanMapper.map(ext, ProductIndication.class);

        /// Assert
        assertEquals("some description", core.getDescription());
        assertEquals("some version", core.getVersion());
        assertEquals("6", core.getClasses().get(0).getMainClass());
        assertEquals("22", core.getClasses().get(0).getSubClass());
        assertEquals("class description", core.getClasses().get(0).getDescription());
        assertEquals("term 1 of 2", core.getClasses().get(0).getTerms().get(0).getText());
        assertEquals(123, core.getClasses().get(0).getTerms().get(0).getId().intValue());
        assertEquals("term 2 of 2", core.getClasses().get(0).getTerms().get(1).getText());
        assertEquals(456, core.getClasses().get(0).getTerms().get(1).getId().intValue());
    }
}
