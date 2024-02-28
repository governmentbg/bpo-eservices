package eu.ohim.sp.dsefiling.ui.form.validator;

import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DSExhPriorityFormValidatorTest
{
    @Mock
    private SectionViewConfiguration sectionViewConfiguration;

    @Mock
    private FlowScopeDetails flowScopeDetails;

    @InjectMocks
    DSExhPriorityFormValidator validator = new DSExhPriorityFormValidator();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void supportsTest1()
    {
        boolean result = validator.supports(DSExhPriorityForm.class);

        assertEquals(true, result);
    }

    @Test
    public void supportsTest2()
    {
        boolean result = validator.supports(DSFlowBean.class);

        assertEquals(false, result);
    }

    @Test
    public void validateTest1()
    {
        when(sectionViewConfiguration.getRender(any(AvailableSection.class), any(String.class), any(String.class))).thenReturn(true);
        when(flowScopeDetails.getFlowModeId()).thenReturn("test");
        Errors errors = mock(Errors.class);

        validator.validate(new DSExhPriorityForm(), errors);
    }

    @Test
    public void validateTest2()
    {
        when(sectionViewConfiguration.getRender(any(AvailableSection.class), any(String.class), any(String.class))).thenReturn(true);
        when(flowScopeDetails.getFlowModeId()).thenReturn("test");
        Errors errors = mock(Errors.class);

        DSExhPriorityForm exh = new DSExhPriorityForm();
        exh.setFileWrapper(new FileWrapper());
        exh.getFileWrapper().setAttachment(true);
        exh.getFileWrapper().setStoredFiles(new ArrayList<StoredFile>());
        exh.getFileWrapper().getStoredFiles().add(new StoredFile());

        validator.validate(new DSExhPriorityForm(), errors);
    }
}
