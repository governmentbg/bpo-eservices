package eu.ohim.sp.dsefiling.ui.controller.payment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.flow.section.PaymentFlowBean;
import eu.ohim.sp.common.ui.form.payment.PayerKindForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;

public class DSPaymentOptionsControllerTest
{
    @Mock
    private PaymentFlowBean flowBean;

    @InjectMocks
    DSPaymentOptionsController paymentOptionsController = new DSPaymentOptionsController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadPayerTypes_applicant_empty()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.APPLICANT.getCode());
        fbParam.setPaymentForm(mockPayFormParam);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(null, mockPayForm.getPayerType());
    }

    @Test
    public void loadPayerTypes_applicant_legalEntity()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.APPLICANT.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        LegalEntityForm mockAp = new LegalEntityForm();
        mockAp.setName("ap name");
        when(flowBean.getObject(ApplicantForm.class, "payer")).thenReturn(mockAp);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.APPLICANT.getCode(), mockPayForm.getPayerType());
        assertEquals("ap name", flowBean.getPaymentForm().getCompany());
    }

    @Test
    public void loadPayerTypes_applicant_naturalPerson()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.APPLICANT.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        NaturalPersonForm mockAp = new NaturalPersonForm();
        mockAp.setFirstName("ap first name");
        mockAp.setSurname("ap surname");
        when(flowBean.getObject(ApplicantForm.class, "payer")).thenReturn(mockAp);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.APPLICANT.getCode(), mockPayForm.getPayerType());
        assertEquals("ap surname", flowBean.getPaymentForm().getSurname());
        assertEquals("ap first name", flowBean.getPaymentForm().getName());
    }

    @Test
    public void loadPayerTypes_applicant_naturalPersonSpecial()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.APPLICANT.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        NaturalPersonSpecialForm mockAp = new NaturalPersonSpecialForm();
        mockAp.setName("ap name");
        when(flowBean.getObject(ApplicantForm.class, "payer")).thenReturn(mockAp);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.APPLICANT.getCode(), mockPayForm.getPayerType());
        assertEquals("ap name", flowBean.getPaymentForm().getName());
    }

    @Test
    public void loadPayerTypes_representative_empty()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        fbParam.setPaymentForm(mockPayFormParam);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(null, mockPayForm.getPayerType());
    }


    @Test
    public void loadPayerTypes_representative_association()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        RepresentativeAssociationForm mockRep = new RepresentativeAssociationForm();
        mockRep.setAssociationName("rep name");
        when(flowBean.getObject(RepresentativeForm.class, "payer")).thenReturn(mockRep);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.REPRESENTATIVE.getCode(), mockPayForm.getPayerType());
        assertEquals("rep name", flowBean.getPaymentForm().getCompany());
    }

    @Test
    public void loadPayerTypes_representative_employee()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        EmployeeRepresentativeForm mockRep = new EmployeeRepresentativeForm();
        mockRep.setFirstName("rep first name");
        when(flowBean.getObject(RepresentativeForm.class, "payer")).thenReturn(mockRep);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.REPRESENTATIVE.getCode(), mockPayForm.getPayerType());
        assertEquals("rep first name", flowBean.getPaymentForm().getName());
    }

    @Test
    public void loadPayerTypes_representative_legalEntity()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        RepresentativeLegalEntityForm mockRep = new RepresentativeLegalEntityForm();
        mockRep.setNameOfLegalEntity("rep name");
        when(flowBean.getObject(RepresentativeForm.class, "payer")).thenReturn(mockRep);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.REPRESENTATIVE.getCode(), mockPayForm.getPayerType());
        assertEquals("rep name", flowBean.getPaymentForm().getCompany());
    }

    @Test
    public void loadPayerTypes_representative_legalPractitioner()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        LegalPractitionerForm mockRep = new LegalPractitionerForm();
        mockRep.setFirstName("rep first name");
        when(flowBean.getObject(RepresentativeForm.class, "payer")).thenReturn(mockRep);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.REPRESENTATIVE.getCode(), mockPayForm.getPayerType());
        assertEquals("rep first name", flowBean.getPaymentForm().getName());
    }

    @Test
    public void loadPayerTypes_representative_naturalPerson()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        RepresentativeNaturalPersonForm mockRep = new RepresentativeNaturalPersonForm();
        mockRep.setFirstName("rep first name");
        when(flowBean.getObject(RepresentativeForm.class, "payer")).thenReturn(mockRep);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.REPRESENTATIVE.getCode(), mockPayForm.getPayerType());
        assertEquals("rep first name", flowBean.getPaymentForm().getName());
    }

    @Test
    public void loadPayerTypes_representative_professionalPractitioner()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.REPRESENTATIVE.getCode());
        mockPayFormParam.setSelectedPayer("payer");
        fbParam.setPaymentForm(mockPayFormParam);

        ProfessionalPractitionerForm mockRep = new ProfessionalPractitionerForm();
        mockRep.setFirstName("rep first name");
        when(flowBean.getObject(RepresentativeForm.class, "payer")).thenReturn(mockRep);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.REPRESENTATIVE.getCode(), mockPayForm.getPayerType());
        assertEquals("rep first name", flowBean.getPaymentForm().getName());
    }

    @Test
    public void loadPayerTypes_other()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType(PayerKindForm.OTHER.getCode());
        fbParam.setPaymentForm(mockPayFormParam);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals(PayerKindForm.OTHER.getCode(), mockPayForm.getPayerType());
    }

    @Test
    public void loadPayerTypes_else()
    {
        PaymentForm mockPayForm = new PaymentForm();
        when(flowBean.getPaymentForm()).thenReturn(mockPayForm);
        DSFlowBeanImpl fbParam = new DSFlowBeanImpl();

        PaymentForm mockPayFormParam = new PaymentForm();
        mockPayFormParam.setPayerType("something else");
        fbParam.setPaymentForm(mockPayFormParam);

        paymentOptionsController.loadPayerTypes(fbParam, null);

        assertEquals("something else", mockPayForm.getPayerType());
    }
}
