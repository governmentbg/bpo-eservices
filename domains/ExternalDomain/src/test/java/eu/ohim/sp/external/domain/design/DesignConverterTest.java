package eu.ohim.sp.external.domain.design;

import eu.ohim.sp.external.domain.claim.DesignExhibitionPriority;
import eu.ohim.sp.external.domain.claim.DesignPriority;
import eu.ohim.sp.external.domain.person.Designer;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 27/1/2014
 * Time: 1:35 πμ
 * To change this template use File | Settings | File Templates.
 */
public class DesignConverterTest {

    @Test
    public void testDesignConvert() {
        /// ARRANGE
        ObjectFactory designObjectFactory = new ObjectFactory();
        Design external = designObjectFactory.createDesign();
        external.setApplicationLanguage("en");
        external.setSecondLanguage("fr");
        external.setDesignIdentifier("some id");
        external.setRegistrationNumber("some reg number");
        external.setRegistrationOffice("gb");
        external.setRegistrationDate(new Date(2011, 11, 11));
        external.setExpiryDate(new Date(2011, 12, 12));

        external.setOrnamentationIndicator(true);

        external.setPublicationDefermentIndicator(true);
        external.setPublicationDefermentTillDate(new Date(2012, 12, 12));
        external.setVerbalElements("some verbal");
        external.setDescription("some description");
        external.setDistinctiveFeatures("some distinctive");
        external.setColours("some colours");
        external.setCurrentStatus(DesignStatusCode.FILED);
        external.setCurrentStatusDate(new Date(2012, 11, 11));
        external.setNumberOfRenewals(BigInteger.TEN);
        external.setSpecimenIndicator(true);
        //external.setDivisionalApplication(true);


        // Divisional
        //external.setDivisionalApplicationDetails(new DesignDivisionalApplicationDetails());
        //external.getDivisionalApplicationDetails().setInitialApplicationNumber("some number");


        // Designer
        eu.ohim.sp.external.domain.person.ObjectFactory personObjectFactory = new eu.ohim.sp.external.domain.person.ObjectFactory();
        Designer designer = personObjectFactory.createDesigner();
        designer.setGroupName("groupName");
        //designer.setSequenceNumber(1);

        external.setDesigners(new ArrayList<Designer>());
        external.getDesigners().add(designer);

        // DesignView
        DesignView designView = designObjectFactory.createDesignView();
        designView.setDescription("some view description");

        external.setViews(new ArrayList<DesignView>());
        external.getViews().add(designView);

        // ProductIndication
        ProductIndication prodIndication = designObjectFactory.createProductIndication();
        prodIndication.setDescription("some locarno description");

        external.setProductIndications(new ArrayList<ProductIndication>());
        external.getProductIndications().add(prodIndication);

        // Priority
        eu.ohim.sp.external.domain.claim.ObjectFactory claimObjectFactory = new eu.ohim.sp.external.domain.claim.ObjectFactory();
        DesignPriority prio = claimObjectFactory.createDesignPriority();
        //prio.setSequenceNumber(1);
        prio.setFilingNumber("filing number");
        prio.setFilingOffice("EM");
        prio.setFilingDate(new Date());
        prio.setApplicantName("prio applicant name");
        external.setPriorities(new ArrayList<DesignPriority>());
        external.getPriorities().add(prio);

        // Exh priority
        DesignExhibitionPriority exhPrio = claimObjectFactory.createDesignExhibitionPriority();
        //exhPrio.setSequenceNumber(1);
        //exhPrio.setFirstDisplayDate(new Date(2011, 11, 11));
        external.setExhibitionPriorities(new ArrayList<DesignExhibitionPriority>());
        external.getExhibitionPriorities().add(exhPrio);

        /// ACT
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.design.Design core = dozerBeanMapper.map(external, eu.ohim.sp.core.domain.design.Design.class);

        /// ASSERT
        assertEquals("en", core.getApplicationLanguage());
        assertEquals("fr", core.getSecondLanguage());
        assertEquals("some id", core.getDesignIdentifier());
        assertEquals("some reg number", core.getRegistrationNumber());
        assertEquals("gb", core.getRegistrationOffice());
        assertEquals(new Date(2011, 11, 11), core.getRegistrationDate());
        assertEquals(new Date(2011, 12, 12), core.getExpiryDate());
        assertEquals(true, core.isOrnamentationIndicator());
        assertEquals(true, core.isPublicationDefermentIndicator());
        assertEquals(new Date(2012, 12, 12), core.getPublicationDefermentTillDate());
        assertEquals("some verbal", core.getVerbalElements());
        assertEquals("some description", core.getDescription());
        assertEquals("some distinctive", core.getDistinctiveFeatures());
        assertEquals(eu.ohim.sp.core.domain.design.DesignStatusCode.FILED, core.getCurrentStatus());
        assertEquals(new Date(2012, 11, 11), core.getCurrentStatusDate());
        //assertEquals(true, core.isDivisionalApplicationIndicator());
        assertEquals("prio applicant name", core.getPriorities().get(0).getApplicantName());
        assertEquals("filing number", core.getPriorities().get(0).getFilingNumber());
        assertEquals("EM", core.getPriorities().get(0).getFilingOffice());
        assertEquals("some colours", core.getColours());
        assertEquals(external.getNumberOfRenewals().intValue(), core.getNumberOfRenewals());
        assertEquals(external.getSpecimenIndicator(), core.isSpecimenIndicator());

        // designer
        assertEquals("groupName", core.getDesigners().get(0).getGroupName());

        // design view
        assertEquals("some view description",
                core.getViews().get(0).getDescription());

        // product indication
        assertEquals("some locarno description",
                core.getProductIndications().get(0).getDescription());

    }
}
