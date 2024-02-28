package eu.ohim.sp.ui.tmefiling.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

@RunWith(MockitoJUnitRunner.class)
public class FeeServiceTest {
    @InjectMocks
    private FeeService feeService;

    @Mock
    private FlowBeanFactory flowBeanFactory;

    @Test
    public void testGetModule() {
        assertThat(feeService.getModule()).isEqualTo("tmefiling");
    }

    @Test
    public void testGetFeesInputData() {
        // given
        TradeMark tradeMark = mock(TradeMark.class);
        FlowBeanImpl flowBean = mock(FlowBeanImpl.class);

        when(flowBeanFactory.convertTo(eq(flowBean))).thenReturn(tradeMark);

        // when
        List<Object> result = feeService.getFeesInputData(flowBean);

        // then
        assertThat(result).hasSize(1).contains(tradeMark);
    }
}