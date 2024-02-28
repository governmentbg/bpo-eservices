package eu.ohim.sp.ui.tmefiling.adapter;

import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.*;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.ConversionPriority;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FlowBeanFactoryTest {

    @Mock
    private SeniorityFactory seniorityFactory;

    @Mock
    private PriorityFactory priorityFactory;

    @Mock
    private TransformationFactory transformationFactory;

    @Mock
    private ConversionFactory conversionFactory;

    @Mock
    private ExhibitionPriorityFactory exhibitionPriorityFactory;

    @Mock
    private SignatoryFactory signatoryFactory;

    @Mock
    private GoodsServicesFactory goodsServicesFactory;

    @Mock
    private ApplicantFactory applicantFactory;

    @Mock
    private RepresentativeFactory representativeFactory;

    @Mock
    private MainFormFactory mainFormFactory;

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
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Mock
    private PaymentFactory paymentFactory;

    @Mock
    private FeesFactory feesFactory;

	@InjectMocks
	private FlowBeanFactory flowBeanFactory;

    @Test
    public void initTest() {
        flowBeanFactory.init();

        UIFactory factory = flowBeanFactory.getFactory(FlowBeanImpl.class);

        assertEquals(factory, flowBeanFactory);
    }

    @Test
    public void convertToTestWhenNull() {

        TradeMark trademark = flowBeanFactory.convertTo(null);

        assertNotNull(trademark);
        assertTrue(trademark.getMarkKind()==null);

    }

    @Test
    public void convertToTest1() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MainForm mainForm = Mockito.mock(MainForm.class);

        Mockito.when(mainFormFactory.convertTo(flowBean)).thenReturn(tradeMark);
        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(mainForm.getClaimPriority()).thenReturn(true);

        TradeMark trademark = flowBeanFactory.convertTo(flowBean);

        assertNotNull(trademark);
        assertTrue(trademark.getMarkKind()==null);

    }

    @Test
    public void convertToTest2() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MainForm mainForm = Mockito.mock(MainForm.class);
        PriorityForm priorityForm = Mockito.mock(PriorityForm.class);
        SeniorityForm seniorityForm = Mockito.mock(SeniorityForm.class);
        ExhPriorityForm exhPriorityForm = Mockito.mock(ExhPriorityForm.class);
        TransformationForm transformationForm = Mockito.mock(TransformationForm.class);
        ConversionForm conversionForm = Mockito.mock(ConversionForm.class);
        GoodAndServiceForm goodAndServiceForm = Mockito.mock(GoodAndServiceForm.class);
        ClassDescription classDescription = Mockito.mock(ClassDescription.class);
        DivisionalApplicationDetails divisionalApplicationDetails = Mockito.mock(DivisionalApplicationDetails.class);
        ApplicantForm applicantForm = Mockito.mock(ApplicantForm.class);
        RepresentativeForm representativeForm = Mockito.mock(RepresentativeForm.class);
        SignatureForm signatureForm = Mockito.mock(SignatureForm.class);

        Mockito.when(mainFormFactory.convertTo(flowBean)).thenReturn(tradeMark);
        Mockito.when(goodsServicesFactory.convertTo(goodAndServiceForm)).thenReturn(classDescription);

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(flowBean.getPriorities()).thenReturn(Arrays.asList(priorityForm));
        Mockito.when(flowBean.getSeniorities()).thenReturn(Arrays.asList(seniorityForm));
        Mockito.when(flowBean.getExhpriorities()).thenReturn(Arrays.asList(exhPriorityForm));
        Mockito.when(flowBean.getTransformations()).thenReturn(Arrays.asList(transformationForm));
        Mockito.when(flowBean.getConversions()).thenReturn(Arrays.asList(conversionForm));
        Mockito.when(flowBean.getGoodsAndServices()).thenReturn(new HashSet<>(Arrays.asList(goodAndServiceForm)));
        Mockito.when(flowBean.getApplicants()).thenReturn(Arrays.asList(applicantForm));
        Mockito.when(flowBean.getRepresentatives()).thenReturn(Arrays.asList(representativeForm));

        Mockito.when(mainForm.getCorrespondenceLanguageCheckBox()).thenReturn(true);
        Mockito.when(mainForm.getClaimDivisionalApplication()).thenReturn(true);
        Mockito.when(mainForm.getSignatoryForm()).thenReturn(signatureForm);
        Mockito.when(mainForm.getAddSecondSign()).thenReturn(true);
        Mockito.when(mainForm.getSecondSignatoryForm()).thenReturn(signatureForm);

        Mockito.when(tradeMark.getDivisionalApplicationDetails()).thenReturn(divisionalApplicationDetails);

        TradeMark trademark = flowBeanFactory.convertTo(flowBean);

        assertNotNull(trademark);
        assertTrue(trademark.getMarkKind()==null);

    }

    @Test
    public void convertFromTestWhenNull() {

        FlowBeanImpl flowBean = flowBeanFactory.convertFrom(null);

        assertNotNull(flowBean);
        assertEquals(flowBean.getMainForm().getMarkType(), "0");

    }

    @Test
    public void convertFromTest1() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MainForm mainForm = Mockito.mock(MainForm.class);

        Mockito.when(mainFormFactory.convertFrom(tradeMark)).thenReturn(flowBean);

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(tradeMark.getExhibitionPriorityClaimLaterIndicator()).thenReturn(true);

        FlowBeanImpl flowBeanResult = flowBeanFactory.convertFrom(tradeMark);

        assertNotNull(flowBeanResult);
        assertEquals(flowBeanResult.getMainForm(), mainForm);

    }

    @Test
    public void convertFromTest2() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MainForm mainForm = Mockito.mock(MainForm.class);
        Applicant applicant = Mockito.mock(Applicant.class);
        ApplicantForm applicantForm = Mockito.mock(ApplicantForm.class);
        Representative representative = Mockito.mock(Representative.class);
        RepresentativeForm representativeForm = Mockito.mock(RepresentativeForm.class);
        Seniority seniority = Mockito.mock(Seniority.class);
        SeniorityForm seniorityForm = Mockito.mock(SeniorityForm.class);
        Priority priority = Mockito.mock(Priority.class);
        PriorityForm priorityForm = Mockito.mock(PriorityForm.class);
        ExhibitionPriority exhibitionPriority = Mockito.mock(ExhibitionPriority.class);
        ExhPriorityForm exhPriorityForm = Mockito.mock(ExhPriorityForm.class);
        TransformationPriority transformationPriority = Mockito.mock(TransformationPriority.class);
        TransformationForm transformationForm = Mockito.mock(TransformationForm.class);
        ConversionPriority conversionPriority = Mockito.mock(ConversionPriority.class);
        ConversionForm conversionForm = Mockito.mock(ConversionForm.class);
        ClassDescription classDescription = Mockito.mock(ClassDescription.class);
        GoodAndServiceForm goodAndServiceForm = Mockito.mock(GoodAndServiceForm.class);
        DivisionalApplicationDetails divisionalApplicationDetails = Mockito.mock(DivisionalApplicationDetails.class);
        Signatory signatory = Mockito.mock(Signatory.class);
        SignatureForm signatureForm = Mockito.mock(SignatureForm.class);
        String lang = "en";

        Mockito.when(mainFormFactory.convertFrom(tradeMark)).thenReturn(flowBean);
        Mockito.when(applicantFactory.convertFrom(applicant)).thenReturn(applicantForm);
        Mockito.when(representativeFactory.convertFrom(representative)).thenReturn(representativeForm);
        Mockito.when(seniorityFactory.convertFrom(seniority)).thenReturn(seniorityForm);
        Mockito.when(priorityFactory.convertFrom(priority)).thenReturn(priorityForm);
        Mockito.when(exhibitionPriorityFactory.convertFrom(exhibitionPriority)).thenReturn(exhPriorityForm);
        Mockito.when(transformationFactory.convertFrom(transformationPriority)).thenReturn(transformationForm);
        Mockito.when(conversionFactory.convertFrom(conversionPriority)).thenReturn(conversionForm);
        Mockito.when(goodsServicesFactory.convertFrom(classDescription)).thenReturn(goodAndServiceForm);
        Mockito.when(signatoryFactory.convertFrom(signatory)).thenReturn(signatureForm);

        Mockito.when(tradeMark.getApplicants()).thenReturn(Arrays.asList(applicant));
        Mockito.when(tradeMark.getRepresentatives()).thenReturn(Arrays.asList(representative));
        Mockito.when(tradeMark.getSeniorities()).thenReturn(Arrays.asList(seniority));
        Mockito.when(tradeMark.getPriorities()).thenReturn(Arrays.asList(priority));
        Mockito.when(tradeMark.getExhibitionPriorities()).thenReturn(Arrays.asList(exhibitionPriority));
        Mockito.when(tradeMark.getTransformationPriorities()).thenReturn(Arrays.asList(transformationPriority));
        Mockito.when(tradeMark.getConversionPriorities()).thenReturn(Arrays.asList(conversionPriority));
        Mockito.when(tradeMark.getClassDescriptions()).thenReturn(Arrays.asList(classDescription));
        Mockito.when(tradeMark.getDivisionalApplicationDetails()).thenReturn(divisionalApplicationDetails);
        Mockito.when(tradeMark.getSignatures()).thenReturn(Arrays.asList(signatory, signatory));
        Mockito.when(tradeMark.getCorrespondenceLanguage()).thenReturn(lang);
        Mockito.when(tradeMark.getSecondLanguage()).thenReturn(lang);

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);

        FlowBeanImpl flowBeanResult = flowBeanFactory.convertFrom(tradeMark);

        assertNotNull(flowBeanResult);
        assertEquals(flowBeanResult.getMainForm(), mainForm);

    }

    @Test
    public void convertFromTradeMarkApplicationTestWhenNull() {

        FlowBeanImpl flowBean = flowBeanFactory.convertFromTradeMarkApplication(null);

        assertNotNull(flowBean);
        assertEquals(flowBean.getMainForm().getMarkType(), "0");

    }

    @Test
    public void convertFromTradeMarkApplicationTest1() {

        TradeMarkApplication tradeMarkApplication = Mockito.mock(TradeMarkApplication.class);

        FlowBeanImpl flowBean = flowBeanFactory.convertFromTradeMarkApplication(tradeMarkApplication);

        assertNotNull(flowBean);
        assertEquals(flowBean.getMainForm().getMarkType(), "0");

    }

    @Test
    public void convertFromTradeMarkApplicationTest2() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        MainForm mainForm = Mockito.mock(MainForm.class);
        TradeMarkApplication tradeMarkApplication = Mockito.mock(TradeMarkApplication.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        PaymentFee paymentFee = Mockito.mock(PaymentFee.class);
        Fee fee = Mockito.mock(Fee.class);

        Mockito.when(mainFormFactory.convertFrom(tradeMark)).thenReturn(flowBean);

        Mockito.when(tradeMarkApplication.getTradeMark()).thenReturn(tradeMark);
        Mockito.when(tradeMarkApplication.getPayments()).thenReturn(Arrays.asList(paymentFee));
        Mockito.when(tradeMarkApplication.getFees()).thenReturn(Arrays.asList(fee));

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);

        FlowBeanImpl flowBeanResult = flowBeanFactory.convertFromTradeMarkApplication(tradeMarkApplication);

        assertNotNull(flowBeanResult);
        assertEquals(flowBeanResult.getMainForm(), mainForm);

    }

    @Test
    public void convertToTradeMarkApplicationTestWhenNull() {

        TradeMarkApplication tradeMarkApplication = flowBeanFactory.convertToTradeMarkApplication(null);

        assertNotNull(tradeMarkApplication);
        assertTrue(tradeMarkApplication.getTradeMark()==null);

    }

    @Test
    public void convertToTradeMarkApplicationTest1() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MainForm mainForm = Mockito.mock(MainForm.class);

        Mockito.when(mainFormFactory.convertTo(flowBean)).thenReturn(tradeMark);

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);

        TradeMarkApplication tradeMarkApplication = flowBeanFactory.convertToTradeMarkApplication(flowBean);

        assertNotNull(tradeMarkApplication);
        assertEquals(tradeMarkApplication.getTradeMark(), tradeMark);

    }

    @Test
    public void convertToTradeMarkApplicationTest2() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MainForm mainForm = Mockito.mock(MainForm.class);
        FileWrapper fileWrapper = Mockito.mock(FileWrapper.class);
        PaymentForm paymentForm = Mockito.mock(PaymentForm.class);
        PaymentFee paymentFee = Mockito.mock(PaymentFee.class);
        FeesForm feesForm = Mockito.mock(FeesForm.class);
        Fee fee = Mockito.mock(Fee.class);

        Mockito.when(mainFormFactory.convertTo(flowBean)).thenReturn(tradeMark);
        Mockito.when(paymentFactory.convertTo(paymentForm)).thenReturn(paymentFee);
        Mockito.when(feesFactory.convertFrom(feesForm)).thenReturn(Arrays.asList(fee));

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(flowBean.getOtherAttachments()).thenReturn(fileWrapper);
        Mockito.when(flowBean.getPaymentForm()).thenReturn(paymentForm);
        Mockito.when(flowBean.getFeesForm()).thenReturn(feesForm);

        TradeMarkApplication tradeMarkApplication = flowBeanFactory.convertToTradeMarkApplication(flowBean);

        assertNotNull(tradeMarkApplication);
        assertEquals(tradeMarkApplication.getTradeMark(), tradeMark);

    }
}
