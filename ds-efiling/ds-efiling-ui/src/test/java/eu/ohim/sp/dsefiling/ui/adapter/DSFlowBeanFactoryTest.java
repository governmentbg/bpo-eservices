package eu.ohim.sp.dsefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.adapter.design.*;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.design.*;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.domain.DSMainForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author ionitdi
 */
public class DSFlowBeanFactoryTest
{
    
    DSFlowBeanImpl flowBean;

    DesignApplication coreApplication;
    
    @Mock
    FeesFactory feesFactory;

    @Mock
    DesignFactory designFactory;

    @Mock
    DSDivisionalApplicationFactory divisionalApplicationFactory;

    @Mock
    DSPriorityFactory priorityFactory;

    @Mock
    DSExhPriorityFactory exhibitionPriorityFactory;

    @Mock
    ApplicantFactory applicantFactory;

    @Mock
    RepresentativeFactory representativeFactory;

    @Mock
    DesignerFactory designerFactory;

    @Mock
    SignatoryFactory signatoryFactory;

    @Mock
    ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Mock
    PaymentFactory paymentFactory;

    @Mock
    EntitlementFactory entitlementFactory;

    @Mock
    ContactDetailsFactory contactDetailsFactory;

    @Mock
    AttachmentsFactory attachmentsFactory;
    
    @InjectMocks
    DSFlowBeanFactory flowBeanFactory = new DSFlowBeanFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        flowBean = new DSFlowBeanImpl();
        coreApplication = new DesignApplication();

        PaymentFee fee = new PaymentFee();

        when(paymentFactory.convertTo(Mockito.any())).thenReturn(fee);
    }

    @Test
    public void convertTo_nullFlowBean()
    {
        flowBeanFactory.convertTo(null);
    }

    @Test
    public void convertTo_validFlowBean()
    {
        flowBean.setFirstLang("some first lang");

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals("some first lang", core.getApplicationLanguage());
    }

    @Test
    public void convertTo_designFormsToCore()
    {
        DesignForm form = new DesignForm();
        form.setId("12");
        flowBean.addObject(form);
        flowBean.getMainForm().setRequestDeferredPublication(true);

        Design coreDesign = new Design();
        coreDesign.setId(12);

        when(designFactory.convertTo(form)).thenReturn(coreDesign);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals(12, core.getDesignDetails().get(0).getId().intValue());
    }

    @Test
    public void convertTo_SupplementaryInfo()
    {
        FileWrapper fileWrapper = new FileWrapper();
        fileWrapper.setStoredFiles(new ArrayList<StoredFile>());
        fileWrapper.getStoredFiles().add(new StoredFile());
        fileWrapper.getStoredFiles().get(0).setDocumentId("123");

        ArrayList<AttachedDocument> coreDocs = new ArrayList<AttachedDocument>();
        coreDocs.add(new AttachedDocument());
        coreDocs.get(0).setDocument(new Document());
        coreDocs.get(0).getDocument().setDocumentId("123");

        when(listAttachedDocumentFactory.convertTo(fileWrapper)).thenReturn(coreDocs);

        flowBean.setOtherAttachments(fileWrapper);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals("123", core.getDocuments().get(0).getDocument().getDocumentId());
    }

    @Test
    public void convertTo_divisionalApp()
    {
        DSDivisionalApplicationForm divApp = new DSDivisionalApplicationForm();
        divApp.setNumberDivisionalApplication("number");

        DesignDivisionalApplicationDetails divCore = new DesignDivisionalApplicationDetails();
        divCore.setInitialApplicationNumber("number");

        when(divisionalApplicationFactory.convertTo(divApp)).thenReturn(divCore);

        flowBean.addObject(divApp);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals("number", core.getDivisionalApplicationDetails().getInitialApplicationNumber());
    }

    @Test
    public void convertTo_priority()
    {
        DSPriorityForm form = new DSPriorityForm();
        form.setId("55");

        Priority coreObj = new Priority();
        coreObj.setId(55);

        when(priorityFactory.convertTo(form)).thenReturn(coreObj);

        flowBean.addObject(form);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals(55, core.getPriorities().get(0).getId().intValue());
    }

    @Test
    public void convertTo_exhibitionPriority()
    {
        DSExhPriorityForm form = new DSExhPriorityForm();
        form.setId("55");

        ExhibitionPriority coreObj = new ExhibitionPriority();
        coreObj.setId(55);

        when(exhibitionPriorityFactory.convertTo(form)).thenReturn(coreObj);

        flowBean.addObject(form);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals(55, core.getExhibitionPriorities().get(0).getId().intValue());
    }

    @Test
    public void convertTo_applicant()
    {
        ApplicantForm form = new ApplicantForm();
        form.setId("88");

        Applicant coreObj = new Applicant();
        coreObj.setId(88);

        when(applicantFactory.convertTo(form)).thenReturn(coreObj);

        flowBean.addObject(form);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals(88, core.getApplicants().get(0).getId().intValue());
    }

    @Test
    public void convertTo_representative()
    {
        RepresentativeForm form = new RepresentativeForm();
        form.setId("88");

        Representative coreObj = new Representative();
        coreObj.setId(88);

        when(representativeFactory.convertTo(form)).thenReturn(coreObj);

        flowBean.addObject(form);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals(88, core.getRepresentatives().get(0).getId().intValue());
    }

    @Test
    public void convertTo_designer()
    {
        DesignerForm form = new DesignerForm();
        form.setId("88");

        Designer coreObj = new Designer();
        coreObj.setId(88);

        when(designerFactory.convertTo(form)).thenReturn(coreObj);

        flowBean.addObject(form);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals(88, core.getDesigners().get(0).getId().intValue());
    }

    @Test
    public void convertTo_signature()
    {
        DSMainForm formObj = new DSMainForm();
        formObj.clearInformation();
        formObj.setSignatoryForm(new SignatureForm());
        formObj.setSecondSignatoryForm(new SignatureForm());

        formObj.getSignatoryForm().setFullName("some name");
        formObj.getSecondSignatoryForm().setFullName("second name");
        formObj.setAddSecondSign(true);


        Signatory coreObj = new Signatory();
        coreObj.setName("whatever name");

        Signatory coreObj2 = new Signatory();
        coreObj2.setName("second name");

        when(signatoryFactory.convertTo(any(SignatureForm.class))).thenReturn(coreObj).thenReturn(coreObj2);

        flowBean.setMainForm(formObj);

        DesignApplication core = flowBeanFactory.convertTo(flowBean);

        assertEquals("whatever name", core.getSignatures().get(0).getName());
        assertEquals("second name", core.getSignatures().get(1).getName());
    }


    @Test
    public void convertFrom_nullFlowBean()
    {
        flowBeanFactory.convertFrom(null);
    }


    @Test
    public void convertFrom_validFlowBean()
    {
        coreApplication.setApplicationLanguage("some first lang");

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("some first lang", form.getFirstLang());
    }

    @Test
    public void convertFrom_designs()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("12");

        Design coreDesign = new Design();
        coreDesign.setId(12);
        coreApplication.setRequestDeferredPublication(true);
        coreApplication.setDesignDetails(new ArrayList<Design>());
        coreApplication.getDesignDetails().add(coreDesign);

        when(designFactory.convertFrom(coreDesign)).thenReturn(designForm);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("12", form.getDesigns().get(0).getId());
    }

    @Test
    public void convertFrom_supplementaryInfo()
    {

        FileWrapper fileWrapper = new FileWrapper();
        fileWrapper.setStoredFiles(new ArrayList<StoredFile>());
        fileWrapper.getStoredFiles().add(new StoredFile());
        fileWrapper.getStoredFiles().get(0).setDocumentId("123");

        ArrayList<AttachedDocument> coreDocs = new ArrayList<AttachedDocument>();
        coreDocs.add(new AttachedDocument());
        coreDocs.get(0).setDocument(new Document());
        coreDocs.get(0).getDocument().setDocumentId("123");

        when(listAttachedDocumentFactory.convertFrom(coreDocs)).thenReturn(fileWrapper);

        coreApplication.setDocuments(coreDocs);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("123", form.getOtherAttachments().getStoredFiles().get(0).getDocumentId());
    }


    @Test
    public void convertFrom_divisionalApp()
    {
        DSDivisionalApplicationForm divApp = new DSDivisionalApplicationForm();
        divApp.setNumberDivisionalApplication("number");

        DesignDivisionalApplicationDetails divCore = new DesignDivisionalApplicationDetails();
        divCore.setInitialApplicationNumber("number");

        when(divisionalApplicationFactory.convertFrom(divCore)).thenReturn(divApp);

        coreApplication.setDivisionalApplicationDetails(divCore);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("number", form.getDivisionalApplication().getNumberDivisionalApplication());
    }


    @Test
    public void convertFrom_priority()
    {
        DSPriorityForm formObj = new DSPriorityForm();
        formObj.setId("55");

        Priority coreObj = new Priority();
        coreObj.setId(55);

        when(priorityFactory.convertFrom(coreObj)).thenReturn(formObj);

        coreApplication.setPriorities(new ArrayList<Priority>());
        coreApplication.getPriorities().add(coreObj);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getPriorities().get(0).getId());
    }

    @Test
    public void convertFrom_exhibitionPriority()
    {
        DSExhPriorityForm formObj = new DSExhPriorityForm();
        formObj.setId("55");

        ExhibitionPriority coreObj = new ExhibitionPriority();
        coreObj.setId(55);

        when(exhibitionPriorityFactory.convertFrom(coreObj)).thenReturn(formObj);

        coreApplication.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        coreApplication.getExhibitionPriorities().add(coreObj);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getExhpriorities().get(0).getId());
    }


    @Test
    public void convertFrom_applicant()
    {
        ApplicantForm formObj = new ApplicantForm();
        formObj.setId("55");

        Applicant coreObj = new Applicant();
        coreObj.setId(55);

        when(applicantFactory.convertFrom(coreObj)).thenReturn(formObj);

        coreApplication.setApplicants(new ArrayList<Applicant>());
        coreApplication.getApplicants().add(coreObj);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getApplicants().get(0).getId());
    }

    @Test
    public void convertFrom_representative()
    {
        RepresentativeForm formObj = new RepresentativeForm();
        formObj.setId("55");

        Representative coreObj = new Representative();
        coreObj.setId(55);

        when(representativeFactory.convertFrom(coreObj)).thenReturn(formObj);

        coreApplication.setRepresentatives(new ArrayList<Representative>());
        coreApplication.getRepresentatives().add(coreObj);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getRepresentatives().get(0).getId());
    }

    @Test
    public void convertFrom_designer()
    {
        DesignerForm formObj = new DesignerForm();
        formObj.setId("55");

        Designer coreObj = new Designer();
        coreObj.setId(55);

        when(designerFactory.convertFrom(coreObj)).thenReturn(formObj);

        coreApplication.setDesigners(new ArrayList<Designer>());
        coreApplication.getDesigners().add(coreObj);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getDesigners().get(0).getId());
    }

    @Test
    public void convertFrom_signature()
    {
        DSMainForm formObj = new DSMainForm();
        formObj.setSignatoryForm(new SignatureForm());
        formObj.setSecondSignatoryForm(new SignatureForm());

        formObj.getSignatoryForm().setFullName("some name");
        formObj.getSecondSignatoryForm().setFullName("second name");


        Signatory coreObj = new Signatory();
        coreObj.setName("some name");

        Signatory coreObj2 = new Signatory();
        coreObj2.setName("second name");

        when(signatoryFactory.convertFrom(coreObj)).thenReturn(formObj.getSignatoryForm());
        when(signatoryFactory.convertFrom(coreObj2)).thenReturn(formObj.getSecondSignatoryForm());

        coreApplication.setSignatures(new ArrayList<Signatory>());
        coreApplication.getSignatures().add(coreObj);
        coreApplication.getSignatures().add(coreObj2);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("some name", form.getMainForm().getSignatoryForm().getFullName());
        assertEquals("second name", form.getMainForm().getSecondSignatoryForm().getFullName());
    }

    @Test
    public void convertFrom_designerWithDesign()
    {
        DesignerForm formObj = new DesignerForm();
        formObj.setId("55");
        formObj.setDesignsLinked(new ArrayList<DesignForm>());
        formObj.setDesignSequenceNumber(1);
        formObj.setWaiver(true);
        DesignForm designForm = new DesignForm();
        designForm.setId("11");
        designForm.setDesignerWaiverIndicator(true);
        DesignForm designForm2 = new DesignForm();
        designForm.setId("12");


        formObj.getDesignsLinked().add(designForm);

        Designer coreObj = new Designer();
        coreObj.setId(55);
        coreObj.setSequenceNumber(1);
        Design coreDesign = new Design();
        coreDesign.setId(11);

        ExhibitionPriority exhPrio = new ExhibitionPriority();
        exhPrio.setSequenceNumber(1);


        Design coreDesign2 = new Design();
        coreDesign.setId(12);


        coreDesign.setDesigners(new ArrayList<Designer>());
        coreDesign.getDesigners().add(coreObj);

        coreDesign2.setDesigners(new ArrayList<Designer>());




        when(designerFactory.convertFrom(coreObj)).thenReturn(formObj);
        when(designFactory.convertFrom(coreDesign)).thenReturn(designForm);
        when(designFactory.convertFrom(coreDesign2)).thenReturn(designForm2);

        coreApplication.setDesigners(new ArrayList<Designer>());
        coreApplication.getDesigners().add(coreObj);
        coreApplication.setDesignDetails(new ArrayList<Design>());
        coreApplication.getDesignDetails().add(coreDesign);
        coreApplication.getDesignDetails().add(coreDesign2);
        coreApplication.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        coreApplication.getExhibitionPriorities().add(exhPrio);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getDesigners().get(0).getId());
    }

    @Test
    public void convertFrom_designerWithDesign_nowaiver()
    {
        DesignerForm formObj = new DesignerForm();
        formObj.setId("55");
        formObj.setDesignsLinked(new ArrayList<DesignForm>());
        formObj.setDesignSequenceNumber(1);
        formObj.setWaiver(false);
        DesignForm designForm = new DesignForm();
        designForm.setId("11");
        designForm.setDesignerWaiverIndicator(true);
        DesignForm designForm2 = new DesignForm();
        designForm.setId("12");


        formObj.getDesignsLinked().add(designForm);

        Designer coreObj = new Designer();
        coreObj.setId(55);
        coreObj.setSequenceNumber(1);
        Design coreDesign = new Design();
        coreDesign.setId(11);


        Design coreDesign2 = new Design();
        coreDesign.setId(12);


        coreDesign.setDesigners(new ArrayList<Designer>());
        coreDesign.getDesigners().add(coreObj);

        coreDesign2.setDesigners(new ArrayList<Designer>());




        when(designerFactory.convertFrom(coreObj)).thenReturn(formObj);
        when(designFactory.convertFrom(coreDesign)).thenReturn(designForm);
        when(designFactory.convertFrom(coreDesign2)).thenReturn(designForm2);

        coreApplication.setDesigners(new ArrayList<Designer>());
        coreApplication.getDesigners().add(coreObj);
        coreApplication.setDesignDetails(new ArrayList<Design>());
        coreApplication.getDesignDetails().add(coreDesign);
        coreApplication.getDesignDetails().add(coreDesign2);

        DSFlowBean form = flowBeanFactory.convertFrom(coreApplication);

        assertEquals("55", form.getDesigners().get(0).getId());
    }
}
