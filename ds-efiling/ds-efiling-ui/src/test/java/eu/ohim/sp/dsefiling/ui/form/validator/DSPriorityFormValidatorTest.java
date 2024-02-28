package eu.ohim.sp.dsefiling.ui.form.validator;

import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
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

/**
 * @author ionitdi
 */
public class DSPriorityFormValidatorTest
{
    @Mock
    private SectionViewConfiguration sectionViewConfiguration;

    @Mock
    private FlowScopeDetails flowScopeDetails;

    @InjectMocks
    DSPriorityFormValidator validator = new DSPriorityFormValidator();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void supportsTest1()
    {
        boolean result = validator.supports(DSPriorityForm.class);

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

        validator.validate(new DSPriorityForm(), errors);
    }

    @Test
    public void validateTest2()
    {
        when(sectionViewConfiguration.getRender(any(AvailableSection.class), any(String.class), any(String.class))).thenReturn(true);
        when(flowScopeDetails.getFlowModeId()).thenReturn("test");
        Errors errors = mock(Errors.class);

        DSPriorityForm prio = new DSPriorityForm();
        prio.setFileWrapperCopy(new FileWrapper());
        prio.getFileWrapperCopy().setAttachment(true);
        prio.getFileWrapperCopy().setStoredFiles(new ArrayList<StoredFile>());
        prio.getFileWrapperCopy().getStoredFiles().add(new StoredFile());

        prio.setFileWrapperTranslation(new FileWrapper());
        prio.getFileWrapperTranslation().setAttachment(true);
        prio.getFileWrapperTranslation().setStoredFiles(new ArrayList<StoredFile>());
        prio.getFileWrapperTranslation().getStoredFiles().add(new StoredFile());

        validator.validate(new DSPriorityForm(), errors);
    }
}
