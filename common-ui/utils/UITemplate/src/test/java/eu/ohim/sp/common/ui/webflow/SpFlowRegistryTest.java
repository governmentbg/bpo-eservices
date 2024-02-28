package eu.ohim.sp.common.ui.webflow;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

import java.util.ArrayList;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpFlowRegistryTest {
    private static final String ANY_MODULE = "module";

    @Mock
    private FlowBuilderServices flowBuilderServices;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private SectionViewConfiguration sectionViewConfiguration;

    @Test(expected = IllegalStateException.class)
    public void should_throw_exceptions_when_flow_definitions_is_null() {
        // given
        when(configurationService.getValueList(eq(SpFlowRegistry.WEBFLOW_DEFINITION_FILENAMES), eq(ANY_MODULE))).thenReturn(null);

        // when
        new SpFlowRegistry(flowBuilderServices, configurationService, sectionViewConfiguration, ANY_MODULE);

        // then
        // IllegalStateException is thrown
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_exceptions_when_flow_definitions_is_empty() {
        // given
        when(configurationService.getValueList(eq(SpFlowRegistry.WEBFLOW_DEFINITION_FILENAMES), eq(ANY_MODULE))).thenReturn(new ArrayList<String>());

        // when
        new SpFlowRegistry(flowBuilderServices, configurationService, sectionViewConfiguration, ANY_MODULE);

        // then
        // IllegalStateException is thrown
    }
}
