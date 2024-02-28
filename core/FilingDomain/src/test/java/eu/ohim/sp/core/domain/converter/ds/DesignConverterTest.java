package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignStatusCode;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.filing.domain.ds.DesignCurrentStatusCodeType;
import eu.ohim.sp.filing.domain.ds.DesignRepresentationSheetDetailsType;
import eu.ohim.sp.filing.domain.ds.DesignRepresentationSheetType;
import eu.ohim.sp.filing.domain.ds.DesignerDetails;
import eu.ohim.sp.filing.domain.ds.ExhibitionPriorityDetails;
import eu.ohim.sp.filing.domain.ds.ISOLanguageCode;
import eu.ohim.sp.filing.domain.ds.IndicationProductDetailsType;
import eu.ohim.sp.filing.domain.ds.IndicationProductType;
import eu.ohim.sp.filing.domain.ds.PriorityDetails;
import eu.ohim.sp.filing.domain.ds.Text;
import eu.ohim.sp.filing.domain.ds.ViewDetailsType;
import eu.ohim.sp.filing.domain.ds.ViewType;

/**
 * @author ionitdi
 */
public class DesignConverterTest {
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP() {
        // / ARRANGE
        Design core = new Design();
        core.setApplicationLanguage("en");
        core.setSecondLanguage("fr");
        core.setDesignIdentifier("some id");
        core.setRegistrationNumber("some reg number");
        core.setRegistrationOffice("gb");
        core.setRegistrationDate(new Date(2011, 11, 11));
        core.setExpiryDate(new Date(2011, 12, 12));

        core.setOrnamentationIndicator(true);

        core.setPublicationDefermentIndicator(true);
        core.setPublicationDefermentTillDate(new Date(2012, 12, 12));
        core.setVerbalElements("some verbal");
        core.setDescription("some description");
        core.setDistinctiveFeatures("some distinctive");
        core.setColours("some colours");
        core.setCurrentStatus(DesignStatusCode.FILED);
        core.setCurrentStatusDate(new Date(2012, 11, 11));
        core.setDivisionalApplication(true);

        // Divisional
        core.setDivisionalApplicationDetails(new DesignDivisionalApplicationDetails());
        core.getDivisionalApplicationDetails().setInitialApplicationNumber("some number");

        // Designer
        Designer designer = new Designer();
        designer.setSequenceNumber(1);

        core.setDesigners(new ArrayList<Designer>());
        core.getDesigners().add(designer);

        // DesignView
        DesignView designView = new DesignView();
        designView.setDescription("some view description");

        core.setViews(new ArrayList<DesignView>());
        core.getViews().add(designView);

        // ProductIndication
        ProductIndication prodIndication = new ProductIndication();
        prodIndication.setDescription("some locarno description");

        core.setProductIndications(new ArrayList<ProductIndication>());
        core.getProductIndications().add(prodIndication);

        // Priority
        Priority prio = new Priority();
        prio.setSequenceNumber(1);
        prio.setApplicantName("prio applicant name");
        core.setPriorities(new ArrayList<Priority>());
        core.getPriorities().add(prio);

        // Exh priority
        ExhibitionPriority exhPrio = new ExhibitionPriority();
        exhPrio.setSequenceNumber(1);
        exhPrio.setFirstDisplayDate(new Date(2011, 11, 11));
        core.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        core.getExhibitionPriorities().add(exhPrio);

        // Documents
        AttachedDocument doc = new AttachedDocument();
        doc.setId(12);

        // / ACT
        eu.ohim.sp.filing.domain.ds.Design ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Design.class);

        // / ASSERT
        assertEquals("en", ext.getApplicationLanguage().value());
        assertEquals("fr", ext.getSecondLanguage().value());
        assertEquals("some id", ext.getDesignIdentifier());
        assertEquals("some reg number", ext.getRegistrationNumber());
        assertEquals("gb", ext.getRegistrationOffice());
        assertEquals(new Date(2011, 11, 11), ext.getRegistrationDate());
        assertEquals(new Date(2011, 12, 12), ext.getExpiryDate());
        assertEquals(true, ext.isOrnamentationIndicator());
        assertEquals(true, ext.isPublicationDefermentIndicator());
        assertEquals(new Date(2012, 12, 12), ext.getPublicationDefermentUntilDate());
        assertEquals("some verbal", ext.getVerbalElementText().getValue());
        assertEquals("some description", ext.getDesignDescription().getValue());
        assertEquals("some distinctive", ext.getDistinctiveFeatures().getValue());
        assertEquals(DesignCurrentStatusCodeType.FILED, ext.getDesignCurrentStatusCode());
        assertEquals(new Date(2012, 11, 11), ext.getDesignCurrentStatusDate());
        assertEquals(true, ext.isDivisionalApplicationIndicator());
        assertEquals(1, ext.getPriorities().getPriority().get(0).getPrioritySequenceNumber().intValue());
        assertEquals(1, ext.getExhibitionPriorities().getExhibitionPriority().get(0)
                .getExhibitionPrioritySequenceNumber().intValue());
        assertEquals("some colours", ext.getDesignColourClaimedText().get(0).getValue());

        // designer
        assertEquals(1, ext.getDesigners().getDesigner().get(0).getDesignerSequenceNumber().intValue());

        // design view
        assertEquals("some view description", ext.getDesignRepresentationSheetDetails().getDesignRepresentationSheet()
                .get(0).getViewDetails().getView().get(0).getViewTitle().getValue());

        // product indication
        assertEquals("some locarno description", ext.getIndicationProductDetails().getIndicationProduct().get(0)
                .getIndicationProductDescription().getValue());

        // TODO: document included
    }

    @Test
    public void testConvertToCore() {
        eu.ohim.sp.filing.domain.ds.Design ext = new eu.ohim.sp.filing.domain.ds.Design();
        ext.setApplicationLanguage(ISOLanguageCode.IS);
        ext.setSecondLanguage(ISOLanguageCode.CO);
        ext.setDesignIdentifier("some id");
        ext.setRegistrationNumber("some reg number");
        ext.setRegistrationOffice("reg office");
        ext.setRegistrationDate(new Date(2011, 11, 11));
        ext.setExpiryDate(new Date(2012, 11, 11));
        ext.setOrnamentationIndicator(Boolean.TRUE);
        ext.setPublicationDefermentIndicator(Boolean.TRUE);
        ext.setPublicationDefermentUntilDate(new Date(2011, 12, 12));
        ext.setVerbalElementText(new Text("some verbal", null));
        ext.setDesignDescription(new Text("some description", null));
        ext.setDistinctiveFeatures(new Text("some distinctive", null));
        ext.setDesignCurrentStatusCode(DesignCurrentStatusCodeType.EXPIRED);
        ext.setDesignCurrentStatusDate(new Date(2011, 10, 10));
        ext.setDivisionalApplicationIndicator(Boolean.TRUE);

        // priorities
        ext.setPriorities(new PriorityDetails());
        ext.getPriorities().setPriority(new ArrayList<eu.ohim.sp.filing.domain.ds.Priority>());

        eu.ohim.sp.filing.domain.ds.Priority prio = new eu.ohim.sp.filing.domain.ds.Priority();
        prio.setPrioritySequenceNumber(new BigInteger("1"));
        ext.getPriorities().getPriority().add(prio);

        // exh priorities
        ext.setExhibitionPriorities(new ExhibitionPriorityDetails());
        ext.getExhibitionPriorities().setExhibitionPriority(
                new ArrayList<eu.ohim.sp.filing.domain.ds.ExhibitionPriority>());

        eu.ohim.sp.filing.domain.ds.ExhibitionPriority exhPrio = new eu.ohim.sp.filing.domain.ds.ExhibitionPriority();
        exhPrio.setExhibitionPrioritySequenceNumber(new BigInteger("1"));
        ext.getExhibitionPriorities().getExhibitionPriority().add(exhPrio);

        // views
        eu.ohim.sp.filing.domain.ds.ViewType view = new eu.ohim.sp.filing.domain.ds.ViewType();
        view.setViewTitle(new Text("view description", null));
        ext.setDesignRepresentationSheetDetails(new DesignRepresentationSheetDetailsType(
                new ArrayList<DesignRepresentationSheetType>()));
        ext.getDesignRepresentationSheetDetails().getDesignRepresentationSheet()
                .add(new DesignRepresentationSheetType());
        ext.getDesignRepresentationSheetDetails().getDesignRepresentationSheet().get(0)
                .setViewDetails(new ViewDetailsType(new ArrayList<ViewType>()));
        ext.getDesignRepresentationSheetDetails().getDesignRepresentationSheet().get(0).getViewDetails().getView()
                .add(view);

        // designer
        eu.ohim.sp.filing.domain.ds.Designer designer = new eu.ohim.sp.filing.domain.ds.Designer();
        designer.setDesignerSequenceNumber(new BigInteger("1"));
        ext.setDesigners(new DesignerDetails(null, new ArrayList<eu.ohim.sp.filing.domain.ds.Designer>()));
        ext.getDesigners().getDesigner().add(designer);

        // product indication
        IndicationProductType ip = new IndicationProductType();
        ip.setIndicationProductDescription(new Text("some locarno description", null));
        ext.setIndicationProductDetails(new IndicationProductDetailsType(new ArrayList<IndicationProductType>()));
        ext.getIndicationProductDetails().getIndicationProduct().add(ip);

        // TODO: document included

        // / ACT
        Design core = dozerBeanMapper.map(ext, Design.class);

        // / ASSERT
        assertEquals("is", core.getApplicationLanguage());
        assertEquals("co", core.getSecondLanguage());
        assertEquals("some id", core.getDesignIdentifier());
        assertEquals("some reg number", core.getRegistrationNumber());
        assertEquals("reg office", core.getRegistrationOffice());
        assertEquals(new Date(2011, 11, 11), core.getRegistrationDate());
        assertEquals(new Date(2012, 11, 11), core.getExpiryDate());
        assertEquals(true, core.isOrnamentationIndicator());
        assertEquals(true, core.isPublicationDefermentIndicator());
        assertEquals(new Date(2011, 12, 12), core.getPublicationDefermentTillDate());
        assertEquals("some verbal", core.getVerbalElements());
        assertEquals("some description", core.getDescription());
        assertEquals("some distinctive", core.getDistinctiveFeatures());
        assertEquals(DesignStatusCode.EXPIRED, core.getCurrentStatus());
        assertEquals(new Date(2011, 10, 10), core.getCurrentStatusDate());
        assertEquals(true, core.isDivisionalApplication());

        // seq number items
        assertEquals(1, core.getPriorities().get(0).getSequenceNumber());
        assertEquals(1, core.getExhibitionPriorities().get(0).getSequenceNumber());

        // view
        assertEquals("view description", core.getViews().get(0).getDescription());

        // designer
        assertEquals(1, core.getDesigners().get(0).getSequenceNumber());

        // locarno
        assertEquals("some locarno description", core.getProductIndications().get(0).getDescription());
    }
}
