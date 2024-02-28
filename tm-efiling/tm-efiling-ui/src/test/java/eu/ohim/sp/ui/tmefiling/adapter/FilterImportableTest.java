package eu.ohim.sp.ui.tmefiling.adapter;

import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FilterImportableTest {

    @Mock
    private SectionViewConfiguration viewConfiguration;

	@InjectMocks
	private FilterImportable filterImportable;

    @Test
    public void filterFlowBeanTestWhenNull() {

        FlowBeanImpl originalFlowBean = null;
        FlowBeanImpl newFlowBean = Mockito.mock(FlowBeanImpl.class);
        String flowModeId = "tm-test";
        SectionViewConfiguration.ImportType importType = SectionViewConfiguration.ImportType.PREVIOUS_CTM;

        MainForm mainForm = Mockito.mock(MainForm.class);

        Mockito.when(viewConfiguration.getImportableSection(Mockito.any(AvailableSection.class),
                Mockito.anyString(), Mockito.any(SectionViewConfiguration.ImportType.class)))
                .thenReturn(true);

        Mockito.when(newFlowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(mainForm.getMarkType()).thenReturn("wordmark");

        filterImportable.filterFlowBean(originalFlowBean, newFlowBean, flowModeId, importType);

        verify(newFlowBean, atLeastOnce()).getMainForm();

    }

    @Test
    public void filterFlowBeanTestWhenFigurative() {

        FlowBeanImpl originalFlowBean = null;
        FlowBeanImpl newFlowBean = Mockito.mock(FlowBeanImpl.class);
        String flowModeId = "tm-test";
        SectionViewConfiguration.ImportType importType = SectionViewConfiguration.ImportType.PREVIOUS_CTM;

        MainForm mainForm = Mockito.mock(MainForm.class);

        Mockito.when(viewConfiguration.getImportableSection(Mockito.any(AvailableSection.class),
                Mockito.anyString(), Mockito.any(SectionViewConfiguration.ImportType.class)))
                .thenReturn(true);

        Mockito.when(newFlowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(mainForm.getMarkType()).thenReturn("figurative");

        filterImportable.filterFlowBean(originalFlowBean, newFlowBean, flowModeId, importType);

        verify(newFlowBean, atLeastOnce()).getMainForm();

    }

	@Test
	public void filterFlowBeanTestWhenWordMark() {

        FlowBeanImpl originalFlowBean = Mockito.mock(FlowBeanImpl.class);
        FlowBeanImpl newFlowBean = Mockito.mock(FlowBeanImpl.class);
        String flowModeId = "tm-test";
        SectionViewConfiguration.ImportType importType = SectionViewConfiguration.ImportType.PREVIOUS_CTM;

        MainForm mainForm = Mockito.mock(MainForm.class);
        PriorityForm priorityForm = Mockito.mock(PriorityForm.class);
        SeniorityForm seniorityForm = Mockito.mock(SeniorityForm.class);
        ExhPriorityForm exhPriorityForm = Mockito.mock(ExhPriorityForm.class);
        TransformationForm transformationForm = Mockito.mock(TransformationForm.class);
        ApplicantForm applicantForm = Mockito.mock(ApplicantForm.class);
        RepresentativeForm representativeForm = Mockito.mock(RepresentativeForm.class);
        GoodAndServiceForm goodAndServiceForm = Mockito.mock(GoodAndServiceForm.class);

        Mockito.when(viewConfiguration.getImportableSection(Mockito.any(AvailableSection.class),
                Mockito.anyString(), Mockito.any(SectionViewConfiguration.ImportType.class)))
                .thenReturn(true);

        Mockito.when(originalFlowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(newFlowBean.getMainForm()).thenReturn(mainForm);
        Mockito.when(mainForm.getMarkType()).thenReturn("wordmark");

        Mockito.when(newFlowBean.getPriorities()).thenReturn(Collections.nCopies(1, priorityForm));
        Mockito.when(newFlowBean.getSeniorities()).thenReturn(Collections.nCopies(1, seniorityForm));
        Mockito.when(newFlowBean.getExhpriorities()).thenReturn(Collections.nCopies(1, exhPriorityForm));
        Mockito.when(newFlowBean.getTransformations()).thenReturn(Collections.nCopies(1, transformationForm));
        Mockito.when(newFlowBean.getApplicants()).thenReturn(Collections.nCopies(1, applicantForm));
        Mockito.when(newFlowBean.getRepresentatives()).thenReturn(Collections.nCopies(1, representativeForm));
        Mockito.when(newFlowBean.getGoodsAndServices()).thenReturn(new HashSet<>(Collections.nCopies(1, goodAndServiceForm)));

        filterImportable.filterFlowBean(originalFlowBean, newFlowBean, flowModeId, importType);

        verify(originalFlowBean, atLeastOnce()).getMainForm();
        verify(newFlowBean, atLeastOnce()).getMainForm();

	}

}
