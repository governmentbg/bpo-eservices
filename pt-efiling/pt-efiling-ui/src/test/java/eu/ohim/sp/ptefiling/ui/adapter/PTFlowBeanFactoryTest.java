package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.patent.*;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.patent.*;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.service.FormUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PTFlowBeanFactoryTest {

    @Mock
    private FeesFactory feesFactory;

    @Mock
    private PatentFactory patentFactory;

    @Mock
    private PTDivisionalApplicationFactory ptDivisionalApplicationFactory;

    @Mock
    private PTPriorityFactory ptPriorityFactory;

    @Mock
    private PTTransformationFactory ptTransformationFactory;

    @Mock
    private PTParallelApplicationFactory ptParallelApplicationFactory;

    @Mock
    private PCTFactory pctFactory;

    @Mock
    private ExhibitionPriorityFactory exhibitionPriorityFactory;

    @Mock
    private PTEntitlementFactory ptEntitlementFactory;

    @Mock
    private ApplicantFactory applicantFactory;

    @Mock
    private RepresentativeFactory representativeFactory;

    @Mock
    private InventorFactory inventorFactory;

    @Mock
    private ContactDetailsFactory contactDetailsFactory;

    @Mock
    private SignatoryFactory signatoryFactory;

    @Mock
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Mock
    private PaymentFactory paymentFactory;

    @Mock
    private AttachedDocumentFactory attachedDocumentFactory;

    @Mock
    private FormUtil formUtil;

    @InjectMocks
    private PTFlowBeanFactory ptFlowBeanFactory;


    private PTFlowBean form;

    private PatentApplication core;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        form = PTFactoriesTestSource.createTestFlowBeanAndMainForm();
        core = PTFactoriesTestSource.createTestPatentApplication();

        Patent patent = PTFactoriesTestSource.createTestPatent();
        when(patentFactory.convertTo(form.getPatent())).thenReturn(patent);

        PatentForm ptForm = PTFactoriesTestSource.createTestPatentForm();
        when(patentFactory.convertFrom(core.getPatent())).thenReturn(ptForm);
    }


    @Test
    public void test_convertToPatent(){

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);

        assertEquals(PTFactoriesTestSource.createTestPatent().getPatentTitle(), ptApp.getPatent().getPatentTitle());
    }

    @Test
    public void test_convertToFlowBeanMainForm(){
        AttachedDocument att = PTFactoriesTestSource.createTestAttachedDocument();
        List<AttachedDocument> attachments = Arrays.asList(att);

        when(formUtil.getType()).thenReturn("PT_EFILING");
        when(listAttachedDocumentFactory.convertTo(form.getMainForm().getSmallCompanyFiles())).thenReturn(attachments);
        when(listAttachedDocumentFactory.convertTo(form.getMainForm().getInventorsAreRealFiles())).thenReturn(attachments);
        when(listAttachedDocumentFactory.convertTo(form.getMainForm().getPostponementOfPublicationFiles())).thenReturn(attachments);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);

        assertEquals(form.getComment(), ptApp.getComment());
        assertEquals(form.getApplicantsImportedFromTemplate(), ptApp.getApplicantsImportedFromTemplate());
        assertEquals(form.getFirstLang(), ptApp.getPatent().getApplicationLanguage());
        assertEquals(form.getSecLang(), ptApp.getPatent().getSecondLanguage());
        assertEquals(form.getIdreserventity(), ptApp.getApplicationFilingNumber());
        assertEquals("PT_EFILING", ptApp.getApplicationType());

        assertEquals(form.getMainForm().isPostponementOfPublication(), ptApp.isDefermentOfPublication());
        assertEquals(form.getMainForm().isAnticipationOfPublication(), ptApp.isAnticipationOfPublication());
        assertEquals(form.getMainForm().getApplicationKind().getValue(), ptApp.getApplicationKind());

        assertEquals(form.getMainForm().isInventorsAreReal(), ptApp.isInventorsAreReal());
        assertEquals(form.getMainForm().isExaminationRequested(), ptApp.isExaminationRequested());
        assertEquals(form.getMainForm().isSmallCompany(), ptApp.isSmallCompany());
        assertEquals(form.getMainForm().isEpoDecisionCopy(), ptApp.isEpoDecisionCopy());
        assertEquals(form.getMainForm().isEpoTransferChangeForm(), ptApp.isEpoTransferChangeForm());
        assertEquals(form.getMainForm().isLicenceAvailability(), ptApp.isLicenceAvailability());
        assertEquals(form.getMainForm().isClassifiedForDefense(), ptApp.isClassifiedForDefense());
        assertEquals(form.getMainForm().isClassifiedForNationalSecurity(), ptApp.isClassifiedForNationalSecurity());
        assertEquals(att.getDocument().getDocumentId(), ptApp.getSmallCompanyDocuments().get(0).getDocument().getDocumentId());
        assertEquals(att.getDocument().getDocumentId(), ptApp.getInventorsAreRealDocuments().get(0).getDocument().getDocumentId());
        assertEquals(att.getDocument().getDocumentId(), ptApp.getDefermentOfPublicationDocuments().get(0).getDocument().getDocumentId());
    }

    @Test
    public void test_convertToEntitlement(){
        Entitlement entitlement = PTFactoriesTestSource.createTestEntitlementOffic();
        when(ptEntitlementFactory.convertTo(form.getMainForm().getEntitlement())).thenReturn(entitlement);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(entitlement.getKind(), ptApp.getEntitlement().getKind());

    }

    @Test
    public void test_convertToCA(){
        ContactDetails ca = PTFactoriesTestSource.createTestContactDet();
        form.getMainForm().getCorrespondanceAddresses().add(PTFactoriesTestSource.createTestCAForm());
        when(contactDetailsFactory.convertTo(form.getMainForm().getCorrespondanceAddresses().get(0).getCorrespondenceAddressForm())).thenReturn(ca);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(ca.getAddress().get(0).getCity(), ptApp.getPatent().getContactDetails().get(0).getAddress().get(0).getCity());
    }

    @Test
    public void test_convertToApplicant(){
        Applicant app = PTFactoriesTestSource.createTestApplicant();
        form.getApplicants().add(PTFactoriesTestSource.createTestApplicantForm());
        when(applicantFactory.convertTo(form.getApplicants().get(0))).thenReturn(app);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(app.getIdentifiers().get(0).getValue(), ptApp.getPatent().getApplicants().get(0).getIdentifiers().get(0).getValue());
    }

    @Test
    public void test_convertToRepresentative(){
        Representative rep = PTFactoriesTestSource.createTestRepresentative();
        form.getRepresentatives().add(PTFactoriesTestSource.createTestRepresentativeForm());
        when(representativeFactory.convertTo(form.getRepresentatives().get(0))).thenReturn(rep);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(rep.getIdentifiers().get(0).getValue(), ptApp.getPatent().getRepresentatives().get(0).getIdentifiers().get(0).getValue());
    }

    @Test
    public void test_convertToInventor(){
        Inventor inventor = PTFactoriesTestSource.createTestInventor();
        form.getInventors().add(PTFactoriesTestSource.createTestInventorForm());
        when(inventorFactory.convertTo(form.getInventors().get(0))).thenReturn(inventor);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(inventor.getIdentifiers().get(0).getValue(), ptApp.getPatent().getInventors().get(0).getIdentifiers().get(0).getValue());
    }
    @Test
    public void test_convertToPriorities(){
        PatentPriority prio = PTFactoriesTestSource.createTestPTPriority();
        form.getPriorities().add(PTFactoriesTestSource.createTestPTPriorityForm());
        when(ptPriorityFactory.convertTo(form.getPriorities().get(0))).thenReturn(prio);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(prio.getFilingNumber(), ptApp.getPatent().getPriorities().get(0).getFilingNumber());
    }

    @Test
    public void test_convertToTransformations(){
        PatentTransformation trans = PTFactoriesTestSource.createBGTransform();
        form.getTransformations().add(PTFactoriesTestSource.createTestNationalTransform());
        when(ptTransformationFactory.convertTo(form.getTransformations().get(0))).thenReturn(trans);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(trans.getApplicationNumber(), ptApp.getPatent().getTransformationPriorities().get(0).getApplicationNumber());

    }

    @Test
    public void test_convertToExhibitions(){
        ExhibitionPriority exh = PTFactoriesTestSource.createTestExhibition();
        form.getExhibitions().add(PTFactoriesTestSource.createTestExhibitionPrio());
        when(exhibitionPriorityFactory.convertTo(form.getExhibitions().get(0))).thenReturn(exh);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(exh.getExhibition().getName(), ptApp.getPatent().getExhibitions().get(0).getExhibition().getName());
    }

    @Test
    public void test_convertToParallel(){
        ParallelApplication par = PTFactoriesTestSource.createTestBGParallelApp();
        form.getParallelApplications().add(PTFactoriesTestSource.createTestNationalParallelForm());
        when(ptParallelApplicationFactory.convertTo(form.getParallelApplications().get(0))).thenReturn(par);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(par.getApplicationNumber(), ptApp.getPatent().getParallelApplications().get(0).getApplicationNumber());
    }

    @Test
    public void test_convertTpPct(){
        PCT pct = PTFactoriesTestSource.createTestPct();
        form.getPcts().add(PTFactoriesTestSource.createTestPctForm());

        when(pctFactory.convertTo(form.getPcts().get(0))).thenReturn(pct);
        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(pct.getApplicationNumber(), ptApp.getPatent().getPcts().get(0).getApplicationNumber());

    }

    @Test
    public void test_convertToDivisionals(){
        DivisionalApplicationDetails div = PTFactoriesTestSource.createTestPTDivisionalApp();
        form.getDivisionalApplications().add(PTFactoriesTestSource.createTestPTDivisionalAppForm());
        when(ptDivisionalApplicationFactory.convertTo(form.getDivisionalApplications().get(0))).thenReturn(div);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(div.getInitialApplicationNumber(), ptApp.getPatent().getDivisionalApplicationDetails().get(0).getInitialApplicationNumber());
    }


    @Test
    public void test_convertToOtherAttachments(){
        AttachedDocument attachedDocument = PTFactoriesTestSource.createTestAttachedDocument();
        form.setOtherAttachments(PTFactoriesTestSource.createTestFileWrapper());
        when(attachedDocumentFactory.convertTo(form.getOtherAttachments().getStoredFiles().get(0))).thenReturn(attachedDocument);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(attachedDocument.getDocument().getDocumentId(), ptApp.getDocuments().get(0).getDocument().getDocumentId());

        assertEquals(form.getTrueDocumentsIndicator(), ptApp.getTrueDocumentsIndicator());
    }

    @Test
    public void test_convertToSignatories(){
        Signatory sig = PTFactoriesTestSource.createTestSignatory();
        form.getSignatures().add(PTFactoriesTestSource.createTestSignatureForm());
        when(signatoryFactory.convertTo(form.getSignatures().get(0))).thenReturn(sig);

        PatentApplication ptApp = ptFlowBeanFactory.convertTo(form);
        assertEquals(sig.getName(), ptApp.getSignatures().get(0).getName());
    }



    @Test
    public void test_convertFromPatent(){
        PatentForm pat = PTFactoriesTestSource.createTestPatentForm();
        when(patentFactory.convertFrom(core.getPatent())).thenReturn(pat);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);

        assertEquals(pat.getPatentTitle(), ptFlowBean.getPatent().getPatentTitle());
    }

    @Test
    public void test_convertFromPatentApplication(){
        FileWrapper fl = PTFactoriesTestSource.createTestFileWrapper();
        PatentForm ptForm = PTFactoriesTestSource.createTestPatentForm();
        when(patentFactory.convertFrom(core.getPatent())).thenReturn(ptForm);
        core.setSmallCompanyDocuments(new ArrayList<>());
        core.setInventorsAreRealDocuments(new ArrayList<>());
        when(listAttachedDocumentFactory.convertFrom(core.getSmallCompanyDocuments())).thenReturn(fl);
        when(listAttachedDocumentFactory.convertFrom(core.getInventorsAreRealDocuments())).thenReturn(fl);
        when(listAttachedDocumentFactory.convertFrom(core.getDefermentOfPublicationDocuments())).thenReturn(fl);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(core.getApplicationFilingNumber(), ptFlowBean.getIdreserventity());
        assertEquals(core.getApplicantsImportedFromTemplate(), ptFlowBean.getApplicantsImportedFromTemplate());
        assertEquals(core.getComment(), ptFlowBean.getComment());
        assertEquals(core.getPatent().getApplicationLanguage(), ptFlowBean.getFirstLang());
        assertEquals(core.getPatent().getSecondLanguage(), ptFlowBean.getSecLang());
        assertEquals(core.getApplicationKind(), ptFlowBean.getMainForm().getApplicationKind().getValue());

        assertEquals(core.isDefermentOfPublication(), ptFlowBean.getMainForm().isPostponementOfPublication());
        assertEquals(core.isAnticipationOfPublication(), ptFlowBean.getMainForm().isAnticipationOfPublication());

        assertEquals(core.isExaminationRequested(), ptFlowBean.getMainForm().isExaminationRequested());
        assertEquals(core.isSmallCompany(), ptFlowBean.getMainForm().isSmallCompany());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptFlowBean.getMainForm().getSmallCompanyFiles().getStoredFiles().get(0).getDocumentId());
        assertEquals(core.isInventorsAreReal(), ptFlowBean.getMainForm().isInventorsAreReal());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptFlowBean.getMainForm().getInventorsAreRealFiles().getStoredFiles().get(0).getDocumentId());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptFlowBean.getMainForm().getPostponementOfPublicationFiles().getStoredFiles().get(0).getDocumentId());
        assertEquals(core.isLicenceAvailability(), ptFlowBean.getMainForm().isLicenceAvailability());

        assertEquals(core.isEpoDecisionCopy(), ptFlowBean.getMainForm().isEpoDecisionCopy());

        assertEquals(core.isEpoTransferChangeForm(), ptFlowBean.getMainForm().isEpoTransferChangeForm());

        assertEquals(core.isClassifiedForDefense(), ptFlowBean.getMainForm().isClassifiedForDefense());
        assertEquals(core.isClassifiedForNationalSecurity(), ptFlowBean.getMainForm().isClassifiedForNationalSecurity());
    }

    @Test
    public void test_convertFromEntitlement(){
        PTEntitlementForm entitlement = PTFactoriesTestSource.createTestPTEntitlementFormOffic();
        when(ptEntitlementFactory.convertFrom(core.getEntitlement())).thenReturn(entitlement);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(entitlement.isPatentOfficiary(), ptFlowBean.getMainForm().getEntitlement().isPatentOfficiary());

    }

    @Test
    public void test_convertFromCA(){
        ApplicationCAForm ca = PTFactoriesTestSource.createTestCAForm();
       core.getPatent().setContactDetails(Arrays.asList(PTFactoriesTestSource.createTestContactDet()));
        when(contactDetailsFactory.convertFrom(core.getPatent().getContactDetails().get(0))).thenReturn(ca.getCorrespondenceAddressForm());

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(ca.getCorrespondenceAddressForm().getAddressForm().getStreet(), ptFlowBean.getMainForm().getCorrespondanceAddresses().get(0).getCorrespondenceAddressForm().getAddressForm().getStreet());
    }

    @Test
    public void test_convertFromApplicant(){
        ApplicantForm app = PTFactoriesTestSource.createTestApplicantForm();
        core.getPatent().setApplicants(Arrays.asList(PTFactoriesTestSource.createTestApplicant()));

        when(applicantFactory.convertFrom(core.getPatent().getApplicants().get(0))).thenReturn(app);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(app.getId(), ptFlowBean.getApplicants().get(0).getId());
    }

    @Test
    public void test_convertFromRepresentative(){
        RepresentativeForm rep = PTFactoriesTestSource.createTestRepresentativeForm();
        core.getPatent().setRepresentatives(Arrays.asList(PTFactoriesTestSource.createTestRepresentative()));

        when(representativeFactory.convertFrom(core.getPatent().getRepresentatives().get(0))).thenReturn(rep);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(rep.getId(), ptFlowBean.getRepresentatives().get(0).getId());
    }

    @Test
    public void test_convertFromInventor(){
        InventorForm inv = PTFactoriesTestSource.createTestInventorForm();
        core.getPatent().setInventors(Arrays.asList(PTFactoriesTestSource.createTestInventor()));
        when(inventorFactory.convertFrom(core.getPatent().getInventors().get(0))).thenReturn(inv);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(inv.getId(), ptFlowBean.getInventors().get(0).getId());
    }
    @Test
    public void test_convertFromPriority(){
        PTPriorityForm prio = PTFactoriesTestSource.createTestPTPriorityForm();

        core.getPatent().setPriorities(Arrays.asList(PTFactoriesTestSource.createTestPTPriority()));
        when(ptPriorityFactory.convertFrom(core.getPatent().getPriorities().get(0))).thenReturn(prio);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(prio.getNumber(), ptFlowBean.getPriorities().get(0).getNumber());
    }
    @Test
    public void test_convertFromTransformations(){
        PTTransformationForm trans = PTFactoriesTestSource.createTestNationalTransform();
        core.getPatent().setTransformationPriorities(Arrays.asList(PTFactoriesTestSource.createBGTransform()));
        when(ptTransformationFactory.convertFrom(core.getPatent().getTransformationPriorities().get(0))).thenReturn(trans);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(trans.getApplicationNumber(), ptFlowBean.getTransformations().get(0).getApplicationNumber());

    }

    @Test
    public void test_convertFromExhibitions(){
        ExhPriorityForm exh = PTFactoriesTestSource.createTestExhibitionPrio();
        core.getPatent().setExhibitions(Arrays.asList(PTFactoriesTestSource.createTestExhibition()));
        when(exhibitionPriorityFactory.convertFrom(core.getPatent().getExhibitions().get(0))).thenReturn(exh);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(exh.getExhibitionName(), ptFlowBean.getExhibitions().get(0).getExhibitionName());
    }

    @Test
    public void test_convertFromParallel(){
        PTParallelApplicationForm par = PTFactoriesTestSource.createTestNationalParallelForm();
        core.getPatent().setParallelApplications(Arrays.asList(PTFactoriesTestSource.createTestBGParallelApp()));
        when(ptParallelApplicationFactory.convertFrom(core.getPatent().getParallelApplications().get(0))).thenReturn(par);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(par.getApplicationNumber(), ptFlowBean.getParallelApplications().get(0).getApplicationNumber());
    }

    @Test
    public void test_convertFromPct(){
        PCTForm pctForm = PTFactoriesTestSource.createTestPctForm();
        core.getPatent().setPcts(Arrays.asList(PTFactoriesTestSource.createTestPct()));
        when(pctFactory.convertFrom(core.getPatent().getPcts().get(0))).thenReturn(pctForm);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(pctForm.getApplicationNumber(), ptFlowBean.getPcts().get(0).getApplicationNumber());
    }

    @Test
    public void test_convertFromDivisionalApp(){
        PTDivisionalApplicationForm div = PTFactoriesTestSource.createTestPTDivisionalAppForm();

        core.getPatent().setDivisionalApplicationDetails(Arrays.asList(PTFactoriesTestSource.createTestPTDivisionalApp()));
        when(ptDivisionalApplicationFactory.convertFrom(core.getPatent().getDivisionalApplicationDetails().get(0))).thenReturn(div);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(div.getNumberDivisionalApplication(), ptFlowBean.getDivisionalApplications().get(0).getNumberDivisionalApplication());
    }

    @Test
    public void test_convertFromOtherAttachments(){
        FileWrapper fl = PTFactoriesTestSource.createTestFileWrapper();

        core.setDocuments(Arrays.asList(PTFactoriesTestSource.createTestAttachedDocument()));
        when(listAttachedDocumentFactory.convertFrom(core.getDocuments())).thenReturn(fl);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptFlowBean.getOtherAttachments().getStoredFiles().get(0).getDocumentId());
        assertEquals(core.getTrueDocumentsIndicator(), ptFlowBean.getTrueDocumentsIndicator());
    }

    @Test
    public void test_convertFromSignatories(){
        SignatureForm sig = PTFactoriesTestSource.createTestSignatureForm();
        core.setSignatures(Arrays.asList(PTFactoriesTestSource.createTestSignatory()));
        when(signatoryFactory.convertFrom(core.getSignatures().get(0))).thenReturn(sig);

        PTFlowBean ptFlowBean = ptFlowBeanFactory.convertFrom(core);
        assertEquals(sig.getFullName(), ptFlowBean.getSignatures().get(0).getFullName());
    }

}
