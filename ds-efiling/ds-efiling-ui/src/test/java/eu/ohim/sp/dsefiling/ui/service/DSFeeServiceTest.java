package eu.ohim.sp.dsefiling.ui.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.FeesFactory;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.core.fee.FeeCalculationService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;


public class DSFeeServiceTest
{
    @Mock
    private FlowScopeDetails flowScopeDetails;

    @Mock
    private DSFlowBeanFactory dsFlowBeanFactory;

    @Mock
    private FeeCalculationService feeManagementService;

    @Mock
    private FeesFactory feesFactory;

    @InjectMocks
    DSFeeService feeService = new DSFeeService();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateFeesInformationTest1()
    {
        DSFlowBeanImpl commonFlowBean = mock(DSFlowBeanImpl.class);
        when(commonFlowBean.getFeesForm()).thenReturn(new FeesForm());
        feeService.updateFeesInformation(commonFlowBean);
    }

    @Test
    public void updateFeesInformationTest2()
    {
        DSFlowBeanImpl commonFlowBean = mock(DSFlowBeanImpl.class);
        when(flowScopeDetails.getFlowModeId()).thenReturn("wizard");
        when(feeManagementService.calculateFees(any(String.class), any(List.class))).thenReturn(null);

        feeService.updateFeesInformation(commonFlowBean);
    }
}
