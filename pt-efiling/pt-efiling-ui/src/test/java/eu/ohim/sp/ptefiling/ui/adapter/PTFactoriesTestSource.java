package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.EntitlementKind;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.contact.*;
import eu.ohim.sp.core.domain.patent.*;
import eu.ohim.sp.core.domain.person.*;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBeanImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by Raya
 * 13.11.2018
 */
public class PTFactoriesTestSource {

    static Date createTestDate(){
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse("10.10.2018");
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
    }

    static FileWrapper createTestFileWrapper(){
        FileWrapper wrapper = new FileWrapper();
        StoredFile testFile = new StoredFile();
        testFile.setDocumentId("123");

        testFile.setThumbnail(testFile);
        wrapper.setStoredFiles(Arrays.asList(testFile));
        return wrapper;
    }

    static AttachedDocument createTestAttachedDocument(){
        AttachedDocument attDoc = new AttachedDocument();
        attDoc.setDocument(new Document());
        attDoc.getDocument().setDocumentId("123");
        return attDoc;
    }

    static SignatureForm createTestSignatureForm(){
        SignatureForm sig = new SignatureForm();
        sig.setFullName("Signatory full name");
        return sig;
    }

    static Signatory createTestSignatory(){
        Signatory sig = new Signatory();
        sig.setName("Signatory full name");
        return sig;
    }

    static PTFlowBean createTestFlowBeanAndMainForm(){
        PTFlowBean ptFlowBean = new PTFlowBeanImpl();

        ptFlowBean.setIdreserventity("IDRES");
        ptFlowBean.setFirstLang("bg");
        ptFlowBean.setSecLang("en");
        ptFlowBean.setApplicantsImportedFromTemplate(true);


        ptFlowBean.getMainForm().setPostponementOfPublication(true);
        ptFlowBean.getMainForm().setAnticipationOfPublication(true);
        ptFlowBean.getMainForm().setApplicationKind(PatentApplicationKind.EP_VALIDATION_REQUEST);

        ptFlowBean.getMainForm().setLicenceAvailability(true);
        ptFlowBean.getMainForm().setExaminationRequested(true);
        ptFlowBean.getMainForm().setInventorsAreReal(true);
        ptFlowBean.getMainForm().setSmallCompany(true);
        ptFlowBean.getMainForm().setEpoDecisionCopy(true);
        ptFlowBean.getMainForm().setEpoTransferChangeForm(true);

        ptFlowBean.getMainForm().setClassifiedForNationalSecurity(true);
        ptFlowBean.getMainForm().setClassifiedForDefense(true);

        ptFlowBean.getMainForm().setEntitlement(new PTEntitlementForm());

        ptFlowBean.setTrueDocumentsIndicator(true);


        ptFlowBean.setComment("Test comment");

        return ptFlowBean;
    }

    static PatentApplication createTestPatentApplication(){
        PatentApplication ptApp = new PatentApplication();
        ptApp.setApplicationFilingNumber("IDRES");
        ptApp.setPatent(new Patent());
        ptApp.getPatent().setApplicationLanguage("bg");
        ptApp.getPatent().setSecondLanguage("en");

        ptApp.setDefermentOfPublication(true);
        ptApp.setAnticipationOfPublication(true);
        ptApp.setApplicationKind("EP Validation Request");
        ptApp.setApplicantsImportedFromTemplate(true);
        ptApp.setDefermentOfPublicationDocuments(new ArrayList<>());

        ptApp.setInventorsAreReal(true);
        ptApp.setSmallCompany(true);
        ptApp.setLicenceAvailability(true);
        ptApp.setExaminationRequested(true);
        ptApp.setEpoDecisionCopy(true);
        ptApp.setEpoTransferChangeForm(true);

        ptApp.setClassifiedForNationalSecurity(true);
        ptApp.setClassifiedForDefense(true);

        ptApp.setEntitlement(new Entitlement());

        ptApp.getPatent().setContactDetails(new ArrayList<>());

        ptApp.setTrueDocumentsIndicator(true);

        ptApp.setDocuments(new ArrayList<>());
        ptApp.getDocuments().add(new AttachedDocument());

        ptApp.setComment("Test comment");
        return ptApp;
    }


    static ExhPriorityForm createTestExhibitionPrio(){
        ExhPriorityForm form = new ExhPriorityForm();
        form.setExhibitionName("EXH NAME");
        return form;
    }

    static ExhibitionPriority createTestExhibition(){
        ExhibitionPriority core = new ExhibitionPriority();
        core.setExhibition(new Exhibition());
        core.getExhibition().setName("EXH NAME");
        return core;
    }

    static PatentForm createTestPatentForm(){
        PatentForm ptform = new PatentForm();
        ptform.setPatentTitle("TITLE");
        ptform.setPatentAbstract("Abstract");
        ptform.setPatentTitleSecondLang("TITLE2");
        ptform.setPatentAbstractSecondLang("Abstract2");
        ptform.setClaimsCount("1");
        ptform.setIndependentClaimsCount("1");
        ptform.setPagesCount("2");
        ptform.setDrawingsCount("3");
        ptform.setDrawingNumber("1");

        ptform.setImported(true);
        ptform.setId("1234");
        ptform.setApplicationNumber("1234");
        ptform.setRegistrationNumber("5678");
        ptform.setApplicationDate(createTestDate());
        ptform.setRegistrationDate(createTestDate());
        ptform.setRegistrationPublicationDate(createTestDate());
        ptform.setPatentValidated(true);
        ptform.setExternalReferenceNumber("abcd");
        ptform.setPatentPublicationsInfo("abcdef");
        return ptform;
    }

    static Patent createTestPatent(){
        Patent patent = new Patent();
        patent.setPatentTitle("TITLE");
        patent.setPatentAbstract("Abstract");
        patent.setPatentTitleSecondLang("TITLE2");
        patent.setPatentAbstractSecondLang("Abstract2");
        patent.setClaimsCount("1");
        patent.setIndependentClaimsCount("1");
        patent.setPagesCount("2");
        patent.setDrawingsCount("3");
        patent.setDrawingNumber("1");

        patent.setApplicationNumber("1234");
        patent.setRegistrationNumber("5678");
        patent.setApplicationDate(createTestDate());
        patent.setRegistrationDate(createTestDate());
        patent.setRegistrationPublicationDate(createTestDate());
        patent.setPatentValidated(true);
        patent.setExternalReferenceNumber("abcd");
        patent.setPatentPublicationsInfo("abcdef");
        return patent;
    }

    static PatentViewForm createTestPatentViewForm(){
        PatentViewForm ptView = new PatentViewForm();
        ptView.setPublicationSize("2");
        ptView.setPublishInColour(true);
        ptView.setType("Type");
        ptView.setPublishInBlackWhite(true);
        ptView.setSequence(1);
        ptView.setDescription("DESC");
        return ptView;
    }

    static PatentView createTestPatentView(){
        PatentView view = new PatentView();
        view.setPublicationSize("2");
        view.setPublishInColour(true);
        view.setType("Type");
        view.setPublishInBlackWhite(true);
        view.setSequence(1);
        view.setDescription("DEC");

        return view;
    }
    static PTPriorityForm createTestPTPriorityForm(){
        PTPriorityForm ptPrio = new PTPriorityForm();
        ptPrio.setNumber("123");
        ptPrio.setCountry("BG");

        ptPrio.setDate(createTestDate());
        return ptPrio;

    }

    static PatentPriority createTestPTPriority(){
        PatentPriority ptPrio = new PatentPriority();
        ptPrio.setFilingNumber("123");
        ptPrio.setFilingOffice("BG");
        ptPrio.setFilingDate(createTestDate());
        return ptPrio;

    }

    static PTEntitlementForm createTestPTEntitlementFormTransfer(){
        PTEntitlementForm form = new PTEntitlementForm();
        form.setTransferContract(true);
        return form;
    }

    static Entitlement createTestEntitlementTransfer(){
        Entitlement core = new Entitlement();
        core.setKind(EntitlementKind.TRANSFER_OF_RIGHTS);
        return core;
    }

    static PTEntitlementForm createTestPTEntitlementFormOffic(){
        PTEntitlementForm form = new PTEntitlementForm();
        form.setPatentOfficiary(true);
        return form;
    }

    static Entitlement createTestEntitlementOffic(){
        Entitlement core = new Entitlement();
        core.setKind(EntitlementKind.PATENT_IS_OFFICIARY);
        return core;
    }

    static PTEntitlementForm createTestPTEntitlementFormOther(){
        PTEntitlementForm form = new PTEntitlementForm();
        form.setOtherGrounds(true);
        form.setOtherGroundsDescription("DESCR");
        return form;
    }

    static Entitlement createTestEntitlementOther(){
        Entitlement core = new Entitlement();
        core.setKind(EntitlementKind.OTHER_GROUNDS);
        core.setDescription("DESCR");
        return core;
    }


    static PTDivisionalApplicationForm createTestPTDivisionalAppForm(){
        PTDivisionalApplicationForm form = new PTDivisionalApplicationForm();
        form.setNumberDivisionalApplication("123");
        form.setDateDivisionalApplication(createTestDate());
        return form;
    }

    static DivisionalApplicationDetails createTestPTDivisionalApp(){
        DivisionalApplicationDetails div = new DivisionalApplicationDetails();
        div.setInitialApplicationNumber("123");
        div.setInitialApplicationDate(createTestDate());
        return div;
    }

    static ApplicantForm createTestApplicantForm(){
        NaturalPersonForm form = new NaturalPersonForm();
        form.setId("123");

        return form;
    }

    static Applicant createTestApplicant(){
        Applicant app = new Applicant();
        List<PersonIdentifier> ids = new ArrayList<>();
        ids.add(new PersonIdentifier());
        ids.get(0).setValue("12");

        app.setIdentifiers(ids);
        return app;
    }

    static RepresentativeForm createTestRepresentativeForm(){
        EmployeeRepresentativeForm form = new EmployeeRepresentativeForm();
        form.setId("123");
        return form;
    }

    static Representative createTestRepresentative(){
        Representative rep = new Representative();
        List<PersonIdentifier> ids = new ArrayList<>();
        ids.add(new PersonIdentifier());
        ids.get(0).setValue("12");

        rep.setIdentifiers(ids);
        return rep;
    }

    static PTNationalTransformationForm createTestNationalTransform(){
        PTNationalTransformationForm form = new PTNationalTransformationForm();
        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");
        form.setCountryCode("BG");
        form.setPayedFees(true);
        return form;
    }

    static PTInternationalTransformationForm createTestInternatTransform(){
        PTInternationalTransformationForm form = new PTInternationalTransformationForm();
        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");

        form.setPublicationDate(createTestDate());
        form.setPublicationNumber("456");

        form.setCountryCode("WO");
        form.setPayedFees(true);
        return form;
    }

    static PTConversionForm createTestConversion(){
        PTConversionForm form = new PTConversionForm();
        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");

        form.setCountryCode("EM");
        form.setPayedFees(true);
        return form;
    }

    static PatentTransformation createBGTransform(){
        PatentTransformation core = new PatentTransformation();
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");
        core.setCountryCode("BG");
        core.setPayedFees(true);
        return core;
    }

    static PatentTransformation createWOTransform(){
        PatentTransformation core = new PatentTransformation();
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");

        core.setPublicationDate(createTestDate());
        core.setPublicationNumber("456");
        core.setCountryCode("WO");
        core.setPayedFees(true);
        return core;
    }

    static PatentTransformation createEMTransform(){
        PatentTransformation core = new PatentTransformation();
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");

        core.setCountryCode("EM");
        core.setPayedFees(true);
        return core;
    }



    static PTParallelApplicationForm createTestNationalParallelForm(){
        PTParallelApplicationForm form = new PTNationalParallelApplicationForm();
        form.setCountryCode("BG");
        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");

        form.setPublicationDate(createTestDate());
        form.setPublicationNumber("456");

        return form;
    }

    static PTParallelApplicationForm createTestInternationalParallelForm(){
        PTParallelApplicationForm form = new PTNationalParallelApplicationForm();
        form.setCountryCode("WO");
        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");

        form.setPublicationDate(createTestDate());
        form.setPublicationNumber("456");

        return form;
    }

    static PTParallelApplicationForm createTestEUParallelForm(){
        PTParallelApplicationForm form = new PTNationalParallelApplicationForm();
        form.setCountryCode("EM");
        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");

        form.setPublicationDate(createTestDate());
        form.setPublicationNumber("456");

        return form;
    }

    static ParallelApplication createTestBGParallelApp(){
        ParallelApplication core = new ParallelApplication();
        core.setCountryCode("BG");
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");

        core.setPublicationDate(createTestDate());
        core.setPublicationNumber("456");

        return core;
    }

    static ParallelApplication createTestWOParallelApp(){
        ParallelApplication core = new ParallelApplication();
        core.setCountryCode("WO");
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");

        core.setPublicationDate(createTestDate());
        core.setPublicationNumber("456");

        return core;
    }

    static ParallelApplication createTestEMParallelApp(){
        ParallelApplication core = new ParallelApplication();
        core.setCountryCode("EM");
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");

        core.setPublicationDate(createTestDate());
        core.setPublicationNumber("456");

        return core;
    }

    static PCTForm createTestPctForm(){
        PCTForm form = new PCTForm();

        form.setApplicationDate(createTestDate());
        form.setApplicationNumber("123");
        form.setHolderName("HOLER");

        form.setPublicationDate(createTestDate());
        form.setPublicationNumber("456");
        form.setPayedFees(true);
        return form;
    }

    static PCT createTestPct(){
        PCT core = new PCT();
        core.setApplicationDate(createTestDate());
        core.setApplicationNumber("123");
        core.setHolderName("HOLER");

        core.setPublicationDate(createTestDate());
        core.setPublicationNumber("456");
        core.setPayedFees(true);

        return core;
    }

    static InventorForm createTestInventorForm(){
        InventorForm form = new InventorForm();
        form.setImported(true);
        form.setId("12");

        form.setFirstName("FNAME");
        form.setMiddleName("MNAME");
        form.setSurname("SNAME");

        form.setAddress(createTestAddressForm());

        form.setImportedFromApplicant(true);

        form.setEmail("mail@test.mail");
        form.setPhone("01102221");

        return form;
    }

    static Inventor createTestInventor(){
        Inventor core = new Inventor();
        List<PersonIdentifier> ids = new ArrayList<>();
        ids.add(new PersonIdentifier());
        ids.get(0).setValue("12");

        core.setIdentifiers(ids);

        core.setName(new PersonName());
        core.getName().setFirstName("FNAME");
        core.getName().setMiddleName("MNAME");
        core.getName().setLastName("SNAME");

        core.setAddresses(new ArrayList<>());
        core.getAddresses().add(createTestAddress());


        core.setImportedFromApplicant(true);
        core.setEmails(new ArrayList<>());
        core.getEmails().add(new Email());
        core.getEmails().get(0).setEmailAddress("mail@test.mail");

        core.setPhones(new ArrayList<>());
        core.getPhones().add(new Phone());
        core.getPhones().get(0).setNumber("1234455");
        core.getPhones().get(0).setPhoneKind(PhoneKind.MOBILE_PHONE);

        return core;
    }

    static AddressForm createTestAddressForm(){
        AddressForm form = new AddressForm();
        form.setCity("Sofia");
        form.setStreet("Botev");
        form.setPostalCode("1000");
        form.setCountry("BG");
        return form;
    }

    static Address createTestAddress(){
        Address core = new Address();
        core.setStreet("Botev");
        core.setCountry("BG");
        core.setPostCode("1000");
        core.setCity("Sofia");
        return core;
    }

    static ApplicationCAForm createTestCAForm(){
        ApplicationCAForm form = new ApplicationCAForm();
        form.setCorrespondenceAddressForm(new CorrespondenceAddressForm());
        form.getCorrespondenceAddressForm().setAddressForm(createTestAddressForm());
        return form;
    }

    static ContactDetails createTestContactDet(){
        ContactDetails core = new ContactDetails();
        core.setAddress(new ArrayList<>());
        core.getAddress().add(createTestAddress());
        return core;
    }


}

