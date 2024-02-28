package eu.ohim.sp.eservices.ui.adapterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.adapter.AttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.EmployeeRepresentativeFactory;
import eu.ohim.sp.common.ui.adapter.FeesFactory;
import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.adapter.LegalEntityFactory;
import eu.ohim.sp.common.ui.adapter.LegalPractitionerFactory;
import eu.ohim.sp.common.ui.adapter.LimitedTrademarkFactory;
import eu.ohim.sp.common.ui.adapter.NaturalPersonFactory;
import eu.ohim.sp.common.ui.adapter.NaturalPersonSpecialFactory;
import eu.ohim.sp.common.ui.adapter.PaymentFactory;
import eu.ohim.sp.common.ui.adapter.ProfessionalPractitionerFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeLegalEntityFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeNaturalPersonFactory;
import eu.ohim.sp.common.ui.adapter.SignatoryFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.adapter.opposition.OppositionGroundFactory;
import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
//import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.MatchedFee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
//import eu.ohim.sp.eservices.ui.service.EServicesUtilitiesProvider;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.service.FormUtil;
import eu.ohim.sp.eservices.ui.service.FormUtil.FormMainType;

public class ESFlowBeanFactoryTest {

	@Mock
	private FeesFactory feesFactory;
	
	@Mock
	private ApplicantFactory applicantFactory;
	
	@Mock
	private PaymentFactory paymentFactory;
	
	@Mock
	private SignatoryFactory signatoryFactory;
	
	@Mock
	private FormUtil formUtil;

	@Mock
	private RepresentativeFactory representativeFactory;
	
	@Mock
	private AssigneeFactory assigneeFactory;
	
	@Mock
	private HolderFactory holderFactory;
	
	@Mock
	private LimitedTrademarkFactory limitedTrademarkFactory;

	@Mock
	private OppositionGroundFactory oppositionGroundFactory;

	@Mock
	private ProfessionalPractitionerFactory professionalPractitionerFactory;

	@Mock
	private EmployeeRepresentativeFactory employeeRepresentativeFactory;

	@Mock
	private LegalPractitionerFactory legalPractitionerFactory;

	@Mock
	private RepresentativeNaturalPersonFactory representativeNaturalPersonFactory;

	@Mock
	private RepresentativeLegalEntityFactory representativeLegalEntityFactory;

	@Mock
	private LegalEntityFactory legalEntityFactory;

	@Mock
	private NaturalPersonFactory naturalPersonFactory;

	@Mock
	private NaturalPersonSpecialFactory naturalPersonSpecialFactory;
	
	@Mock
	private AttachedDocumentFactory attachedDocumentFactory;
	
	@Mock
	private ESDesignFactory designFactory;
	
//	@Mock
//	private EServicesUtilitiesProvider eServicesProvider;
	
	@InjectMocks
	ESFlowBeanFactory eSFlowBeanFactory=new ESFlowBeanFactory();
	
	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		TMeServiceApplication eServiceApplicationtm = Mockito.mock(TMeServiceApplication.class);
//		DSeServiceApplication eServiceApplicationds = Mockito.mock(DSeServiceApplication.class);
//		Mockito.when(eServicesProvider.getServiceApplication(FormMainType.TM)).thenReturn(eServiceApplicationtm);
//		Mockito.when(eServicesProvider.getServiceApplication(FormMainType.DS)).thenReturn(eServiceApplicationds);
//	}	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
//	@Test //this should not throw a nullpointer
//	public void convertTo() {
//		
//		Mockito.when(paymentFactory.convertTo(Mockito.any(PaymentForm.class))).thenReturn(new PaymentFee());
//		
//		
//		ESFlowBean form = new ESFlowBeanImpl();
//		form.setReference("hey");
//		
//		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.TM);
//		EServiceApplication tmEservice = eSFlowBeanFactory.convertTo(form);
//		assertNotNull(tmEservice);
//		
//		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.DS);
//		EServiceApplication dsEservice = eSFlowBeanFactory.convertTo(form);
//		assertNotNull(dsEservice);
//	}
	
	
	@Test //this should not throw a nullpointer
	public void convertTo() {
		
		Mockito.when(paymentFactory.convertTo(Mockito.any(PaymentForm.class))).thenReturn(new PaymentFee());
		
		
		ESFlowBean form = new ESFlowBeanImpl();
		form.setReference("hey");
		
		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.TM);
		EServiceApplication tmEservice = eSFlowBeanFactory.convertTo(form);
		assertEquals("hey", tmEservice.getApplicationReference());
		
		
		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.DS);
		EServiceApplication dsEservice = eSFlowBeanFactory.convertTo(form);
		assertEquals("hey", dsEservice.getApplicationReference());
	}
	
	@Test
	public void init(){
		eSFlowBeanFactory.init();
		
		assertNotNull(eSFlowBeanFactory.getFactory(LegalEntityForm.class));
	}

	@Test
	public void convertFrom() {
		
		TMeServiceApplication tmApplication = new TMeServiceApplication();
		tmApplication.setApplicants(new ArrayList<Applicant>());
		tmApplication.setRepresentatives(new ArrayList<Representative>());
		tmApplication.setHolders(new ArrayList<Holder>());
		tmApplication.setAssignees(new ArrayList<Assignee>());
		tmApplication.setPayments(new ArrayList<PaymentFee>());
		tmApplication.setDocuments(new ArrayList<AttachedDocument>());
		tmApplication.setTradeMarks(new ArrayList<LimitedTradeMark>());
		
		ESFlowBean tmFlowBean = eSFlowBeanFactory.convertFrom(tmApplication);
		assertNotNull(tmFlowBean);

	}
	
	@Test
	public void convertTo2() {
				
		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.DS);
		assertNotNull(eSFlowBeanFactory.convertTo(null));
		
		ESFlowBean form = Mockito.mock(ESFlowBean.class);
		Mockito.when(form.getReference()).thenReturn("hey");
		List<ApplicantForm> listafs=new ArrayList<ApplicantForm>();
		listafs.add(Mockito.mock(ApplicantForm.class));
		Mockito.when(form.getApplicants()).thenReturn(listafs);
		List<RepresentativeForm> listrfs=new ArrayList<RepresentativeForm>();
		listrfs.add(Mockito.mock(RepresentativeForm.class));
		Mockito.when(form.getRepresentatives()).thenReturn(listrfs);
		List<AssigneeForm> listsfs=new ArrayList<AssigneeForm>();
		listsfs.add(Mockito.mock(AssigneeForm.class));
		Mockito.when(form.getAssignees()).thenReturn(listsfs);
		List<HolderForm> listhfs=new ArrayList<HolderForm>();
		listhfs.add(Mockito.mock(HolderForm.class));
		Mockito.when(form.getHolders()).thenReturn(listhfs);
		List<SignatureForm> listgfs=new ArrayList<SignatureForm>();
		listgfs.add(Mockito.mock(SignatureForm.class));
		Mockito.when(form.getSignatures()).thenReturn(listgfs);
		MainForm mainForm=Mockito.mock(MainForm.class);
		Mockito.when(mainForm.getSignatoryForm()).thenReturn(Mockito.mock(SignatureForm.class));
		Mockito.when(form.getMainForm()).thenReturn(mainForm);
		List<StoredFile> lstfiles=new ArrayList<StoredFile>();
		lstfiles.add(Mockito.mock(StoredFile.class));
		FileWrapper fileWrapper=Mockito.mock( FileWrapper.class);
		Mockito.when(fileWrapper.getStoredFiles()).thenReturn(lstfiles);
		Mockito.when(form.getOtherAttachments()).thenReturn(fileWrapper);
		List<OppositionBasisForm> listofs=new ArrayList<OppositionBasisForm>();
		listofs.add(Mockito.mock(OppositionBasisForm.class));
		Mockito.when(form.getObsList()).thenReturn(listofs);
		List<ESDesignDetailsForm> listefs=new ArrayList<ESDesignDetailsForm>();
		listefs.add(Mockito.mock(ESDesignDetailsForm.class));
		Mockito.when(form.getDssList()).thenReturn(listefs);
		Mockito.when(paymentFactory.convertTo(form.getPaymentForm())).thenReturn(Mockito.mock(PaymentFee.class));
		assertNotNull(eSFlowBeanFactory.convertTo(form));
		
		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.TM);
		List<TMDetailsForm> listtfs=new ArrayList<TMDetailsForm>();
		listtfs.add(Mockito.mock(TMDetailsForm.class));
		Mockito.when(form.getTmsList()).thenReturn(listtfs);
		assertNotNull(eSFlowBeanFactory.convertTo(form));
	
	}
	
//	@Test
//	public void convertTo2() {
//				
//		List<Fee> fees=new ArrayList<Fee>();
//		fees.add(Mockito.mock(Fee.class));
//		TMeServiceApplication eServiceApplicationtm = Mockito.mock(TMeServiceApplication.class);
//		DSeServiceApplication eServiceApplicationds = Mockito.mock(DSeServiceApplication.class);
//		Mockito.when(eServiceApplicationtm.getFees()).thenReturn(fees);
//		Mockito.when(eServiceApplicationds.getFees()).thenReturn(fees);
//		
//		Mockito.when(eServicesProvider.getServiceApplication(FormMainType.TM)).thenReturn(eServiceApplicationtm);
//		Mockito.when(eServicesProvider.getServiceApplication(FormMainType.DS)).thenReturn(eServiceApplicationds);
//		
//		
//		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.DS);
//		assertNotNull(eSFlowBeanFactory.convertTo(null));
//		
//		ESFlowBean form = Mockito.mock(ESFlowBean.class);
//		Mockito.when(form.getReference()).thenReturn("hey");
//		List<ApplicantForm> listafs=new ArrayList<ApplicantForm>();
//		listafs.add(Mockito.mock(ApplicantForm.class));
//		Mockito.when(form.getApplicants()).thenReturn(listafs);
//		List<RepresentativeForm> listrfs=new ArrayList<RepresentativeForm>();
//		listrfs.add(Mockito.mock(RepresentativeForm.class));
//		Mockito.when(form.getRepresentatives()).thenReturn(listrfs);
//		List<AssigneeForm> listsfs=new ArrayList<AssigneeForm>();
//		listsfs.add(Mockito.mock(AssigneeForm.class));
//		Mockito.when(form.getAssignees()).thenReturn(listsfs);
//		List<HolderForm> listhfs=new ArrayList<HolderForm>();
//		listhfs.add(Mockito.mock(HolderForm.class));
//		Mockito.when(form.getHolders()).thenReturn(listhfs);
//		List<SignatureForm> listgfs=new ArrayList<SignatureForm>();
//		listgfs.add(Mockito.mock(SignatureForm.class));
//		Mockito.when(form.getSignatures()).thenReturn(listgfs);
//		MainForm mainForm=Mockito.mock(MainForm.class);
//		Mockito.when(mainForm.getSignatoryForm()).thenReturn(Mockito.mock(SignatureForm.class));
//		Mockito.when(form.getMainForm()).thenReturn(mainForm);
//		List<StoredFile> lstfiles=new ArrayList<StoredFile>();
//		lstfiles.add(Mockito.mock(StoredFile.class));
//		FileWrapper fileWrapper=Mockito.mock( FileWrapper.class);
//		Mockito.when(fileWrapper.getStoredFiles()).thenReturn(lstfiles);
//		Mockito.when(form.getOtherAttachments()).thenReturn(fileWrapper);
//		List<OppositionBasisForm> listofs=new ArrayList<OppositionBasisForm>();
//		listofs.add(Mockito.mock(OppositionBasisForm.class));
//		Mockito.when(form.getObsList()).thenReturn(listofs);
//		List<ESDesignDetailsForm> listefs=new ArrayList<ESDesignDetailsForm>();
//		listefs.add(Mockito.mock(ESDesignDetailsForm.class));
//		Mockito.when(form.getDssList()).thenReturn(listefs);
//		Mockito.when(paymentFactory.convertTo(form.getPaymentForm())).thenReturn(Mockito.mock(PaymentFee.class));
//		assertNotNull(eSFlowBeanFactory.convertTo(form));
//		
//		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.TM);
//		List<TMDetailsForm> listtfs=new ArrayList<TMDetailsForm>();
//		listtfs.add(Mockito.mock(TMDetailsForm.class));
//		Mockito.when(form.getTmsList()).thenReturn(listtfs);
//		
//		assertNotNull(eSFlowBeanFactory.convertTo(form));
//	
//	}
	
	@Test
	public void convertFrom2() {
		assertNotNull(eSFlowBeanFactory.convertFrom(null));
		TMeServiceApplication form = Mockito.mock(TMeServiceApplication.class);
		
		List<Applicant> listafs=new ArrayList<Applicant>();
		listafs.add(Mockito.mock(Applicant.class));
		Mockito.when(form.getApplicants()).thenReturn(listafs);
		List<Representative> listrfs=new ArrayList<Representative>();
		listrfs.add(Mockito.mock(Representative.class));
		Mockito.when(form.getRepresentatives()).thenReturn(listrfs);
		List<Assignee> listsfs=new ArrayList<Assignee>();
		listsfs.add(Mockito.mock(Assignee.class));
		Mockito.when(form.getAssignees()).thenReturn(listsfs);
		List<Holder> listhfs=new ArrayList<Holder>();
		listhfs.add(Mockito.mock(Holder.class));
		Mockito.when(form.getHolders()).thenReturn(listhfs);
		List<Signatory> listgfs=new ArrayList<Signatory>();
		listgfs.add(Mockito.mock(Signatory.class));
		Mockito.when(form.getSignatures()).thenReturn(listgfs);
		List<AttachedDocument> lstfiles=new ArrayList<AttachedDocument>();
		lstfiles.add(Mockito.mock(AttachedDocument.class));
		Mockito.when(form.getDocuments()).thenReturn(lstfiles);
		List<OppositionGround> lstogs=new ArrayList<OppositionGround>();
		lstogs.add(Mockito.mock(OppositionGround.class));
		Mockito.when(form.getOppositionGrounds()).thenReturn(lstogs);
		List<MatchedFee> listmcs= new ArrayList<MatchedFee>();
		listmcs.add(Mockito.mock(MatchedFee.class));
		PaymentFee paymentFee=Mockito.mock(PaymentFee.class);
		Mockito.when(paymentFee.getFees()).thenReturn(listmcs);
		List<PaymentFee> lstpfs=new ArrayList<PaymentFee>();
		lstpfs.add(paymentFee);
		Mockito.when(form.getPayments()).thenReturn(lstpfs);
		
		List<LimitedTradeMark> lsttms=new ArrayList<LimitedTradeMark>();
		lsttms.add(Mockito.mock(LimitedTradeMark.class));
		Mockito.when(form.getTradeMarks()).thenReturn(lsttms);
		assertNotNull(eSFlowBeanFactory.convertFrom(form));
		
		DSeServiceApplication form1 = Mockito.mock(DSeServiceApplication.class);
		List<DesignApplication> lstdas=new ArrayList<DesignApplication>();
		lstdas.add(Mockito.mock(DesignApplication.class));
		Mockito.when(form1.getDesignDetails()).thenReturn(lstdas);
		assertNotNull(eSFlowBeanFactory.convertFrom(form1));
	
	}
	
	
	@Test
	public void convertFrom3() {
		assertNotNull(eSFlowBeanFactory.convertFrom(null));
		TMeServiceApplication form = Mockito.mock(TMeServiceApplication.class);
		
		Mockito.when(applicantFactory.convertFrom(Mockito.any(Applicant.class))).thenReturn(new ApplicantForm());
		Mockito.when(representativeFactory.convertFrom(Mockito.any(Representative.class))).thenReturn(new RepresentativeForm());
		Mockito.when(holderFactory.convertFrom(Mockito.any(Holder.class))).thenReturn(new HolderForm());
		Mockito.when(oppositionGroundFactory.convertFrom(Mockito.any(OppositionGround.class))).thenReturn(new OppositionBasisForm());
		Mockito.when(assigneeFactory.convertFrom(Mockito.any(Assignee.class))).thenReturn(new AssigneeForm());
		Mockito.when(paymentFactory.convertFrom(Mockito.any(PaymentFee.class))).thenReturn(new PaymentForm());
		Mockito.when(signatoryFactory.convertFrom(Mockito.any(Signatory.class))).thenReturn(new SignatureForm());
		Mockito.when(attachedDocumentFactory.convertFrom(Mockito.any(AttachedDocument.class))).thenReturn(new StoredFile());
		Mockito.when(limitedTrademarkFactory.convertFrom(Mockito.any(LimitedTradeMark.class))).thenReturn(new TMDetailsForm());
		Mockito.when(designFactory.convertFrom(Mockito.any(DesignApplication.class))).thenReturn(new ESDesignDetailsForm());
		
		List<Applicant> listafs=new ArrayList<Applicant>();
		listafs.add(Mockito.mock(Applicant.class));
		Mockito.when(form.getApplicants()).thenReturn(listafs);
		List<Representative> listrfs=new ArrayList<Representative>();
		listrfs.add(Mockito.mock(Representative.class));
		Mockito.when(form.getRepresentatives()).thenReturn(listrfs);
		List<Assignee> listsfs=new ArrayList<Assignee>();
		listsfs.add(Mockito.mock(Assignee.class));
		Mockito.when(form.getAssignees()).thenReturn(listsfs);
		List<Holder> listhfs=new ArrayList<Holder>();
		listhfs.add(Mockito.mock(Holder.class));
		Mockito.when(form.getHolders()).thenReturn(listhfs);
		List<Signatory> listgfs=new ArrayList<Signatory>();
		listgfs.add(Mockito.mock(Signatory.class));
		Mockito.when(form.getSignatures()).thenReturn(listgfs);
		List<AttachedDocument> lstfiles=new ArrayList<AttachedDocument>();
		lstfiles.add(Mockito.mock(AttachedDocument.class));
		Mockito.when(form.getDocuments()).thenReturn(lstfiles);
		List<OppositionGround> lstogs=new ArrayList<OppositionGround>();
		lstogs.add(Mockito.mock(OppositionGround.class));
		Mockito.when(form.getOppositionGrounds()).thenReturn(lstogs);
		List<MatchedFee> listmcs= new ArrayList<MatchedFee>();
		listmcs.add(null);
		listmcs.add(Mockito.mock(MatchedFee.class));
		PaymentFee paymentFee=Mockito.mock(PaymentFee.class);
		Mockito.when(paymentFee.getFees()).thenReturn(listmcs);
		List<PaymentFee> lstpfs=new ArrayList<PaymentFee>();
		lstpfs.add(paymentFee);
		Mockito.when(form.getPayments()).thenReturn(lstpfs);
		
		List<LimitedTradeMark> lsttms=new ArrayList<LimitedTradeMark>();
		lsttms.add(Mockito.mock(LimitedTradeMark.class));
		Mockito.when(form.getTradeMarks()).thenReturn(lsttms);
		assertNotNull(eSFlowBeanFactory.convertFrom(form));
		
		DSeServiceApplication form1 = Mockito.mock(DSeServiceApplication.class);
		List<DesignApplication> lstdas=new ArrayList<DesignApplication>();
		lstdas.add(Mockito.mock(DesignApplication.class));
		Mockito.when(form1.getDesignDetails()).thenReturn(lstdas);
		assertNotNull(eSFlowBeanFactory.convertFrom(form1));
	
	}
	
	@Test
	public void convertFrom4() {
		assertNotNull(eSFlowBeanFactory.convertFrom(null));
		TMeServiceApplication form = Mockito.mock(TMeServiceApplication.class);
		
		
		List<Applicant> listafs=new ArrayList<Applicant>();
		listafs.add(Mockito.mock(Applicant.class));
		Mockito.when(form.getApplicants()).thenReturn(listafs);
		List<Representative> listrfs=new ArrayList<Representative>();
		listrfs.add(Mockito.mock(Representative.class));
		Mockito.when(form.getRepresentatives()).thenReturn(listrfs);
		List<Assignee> listsfs=new ArrayList<Assignee>();
		listsfs.add(Mockito.mock(Assignee.class));
		Mockito.when(form.getAssignees()).thenReturn(null);
		List<Holder> listhfs=new ArrayList<Holder>();
		listhfs.add(Mockito.mock(Holder.class));
		Mockito.when(form.getHolders()).thenReturn(null);
		List<Signatory> listgfs=new ArrayList<Signatory>();
		listgfs.add(Mockito.mock(Signatory.class));
		Mockito.when(form.getSignatures()).thenReturn(listgfs);
		List<AttachedDocument> lstfiles=new ArrayList<AttachedDocument>();
		lstfiles.add(Mockito.mock(AttachedDocument.class));
		Mockito.when(form.getDocuments()).thenReturn(null);
		List<OppositionGround> lstogs=new ArrayList<OppositionGround>();
		lstogs.add(Mockito.mock(OppositionGround.class));
		Mockito.when(form.getOppositionGrounds()).thenReturn(lstogs);
		List<MatchedFee> listmcs= new ArrayList<MatchedFee>();
		listmcs.add(Mockito.mock(MatchedFee.class));
		PaymentFee paymentFee=Mockito.mock(PaymentFee.class);
		Mockito.when(paymentFee.getFees()).thenReturn(null);
		List<PaymentFee> lstpfs=new ArrayList<PaymentFee>();
		lstpfs.add(paymentFee);
		Mockito.when(form.getPayments()).thenReturn(lstpfs);
		
		List<LimitedTradeMark> lsttms=new ArrayList<LimitedTradeMark>();
		lsttms.add(Mockito.mock(LimitedTradeMark.class));
		Mockito.when(form.getTradeMarks()).thenReturn(lsttms);
		assertNotNull(eSFlowBeanFactory.convertFrom(form));
		
		DSeServiceApplication form1 = Mockito.mock(DSeServiceApplication.class);
		List<DesignApplication> lstdas=new ArrayList<DesignApplication>();
		lstdas.add(Mockito.mock(DesignApplication.class));
		Mockito.when(form1.getDesignDetails()).thenReturn(lstdas);
		assertNotNull(eSFlowBeanFactory.convertFrom(form1));
	
	}

}
