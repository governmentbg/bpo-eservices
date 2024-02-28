package eu.ohim.sp.dsefiling.ui.controller;


import eu.ohim.sp.common.ui.controller.LoadApplicationCAController;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LoadApplicationCAControllerTest
{
    @Mock
    private FlowBean flowBean;

    @Mock
    private ConfigurationServiceDelegatorInterface configurationService;

    @Mock
    FlowScopeDetails flowScopeDetails;

    @InjectMocks
    LoadApplicationCAController controller = new LoadApplicationCAController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        when(flowScopeDetails.getFlowModeId()).thenReturn("tm-change");
        when(flowBean.getObject(Mockito.any(), Mockito.any())).thenReturn(new RepresentativeForm());
    }

    @Test
    public void handleLoadCAExistTest1()
    {
        ApplicantForm form = new ApplicantForm();
        form.setCorrespondenceAddresses(new ArrayList<CorrespondenceAddressForm>());
        form.getCorrespondenceAddresses().add(new CorrespondenceAddressForm());
        form.getCorrespondenceAddresses().get(0).setAddressForm(new AddressForm());

        when(configurationService.getValue(ConfigurationServiceDelegatorInterface.CORRESPONDENCE_ADDRESS_ADD_MAXNUMBER,
                                           ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("10");
        when(flowBean.getObject(ApplicantForm.class, "id")).thenReturn(form);
        ApplicationCAForm caForm = controller.handleLoadCAExist("id");

        assertEquals(caForm.getCorrespondenceAddressForm().getAddressForm(), form.getAddress());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondenceEmail(), form.getEmail());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondenceFax(), form.getFax());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondenceName(), form.getName());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondencePhone(), form.getPhone());

    }

    @Test
    public void handleLoadCAExistTest2()
    {
        RepresentativeForm form = new RepresentativeForm();

        when(configurationService.getValue(ConfigurationServiceDelegatorInterface.CORRESPONDENCE_ADDRESS_ADD_MAXNUMBER,
                                           ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("10");
        when(flowBean.getObject(RepresentativeForm.class, "id")).thenReturn(form);
        ApplicationCAForm caForm = controller.handleLoadCAExist("id");

        assertEquals(caForm.getCorrespondenceAddressForm().getAddressForm(), form.getAddress());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondenceEmail(), form.getEmail());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondenceFax(), form.getFax());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondenceName(), form.getName());
        assertEquals(caForm.getCorrespondenceAddressForm().getCorrespondencePhone(), form.getPhone());

    }

}
