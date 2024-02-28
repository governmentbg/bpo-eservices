package eu.ohim.sp.eservices.ui.adapterTest;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration.ImportType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.eservices.ui.adapter.ESFilterImportable;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;

public class ESFilterImportableTest {

	@Mock
	private SectionViewConfiguration viewConfiguration;
	@Mock
	private ApplicantFactory applicantFactory;

	@InjectMocks
	ESFilterImportable eSFilterImportable = new ESFilterImportable();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void filterFlowBean() {
		ESFlowBean originalFlowBean = Mockito.mock(ESFlowBeanImpl.class);

		String flowModeId = new String();
		
		List<ApplicantForm> applicantForm = new ArrayList<ApplicantForm>();

		applicantForm.add(new ApplicantForm());

		Mockito.when(originalFlowBean.getApplicants()).thenReturn(applicantForm);

		List<RepresentativeForm> representativeForm = new ArrayList<RepresentativeForm>();

		representativeForm.add(new RepresentativeForm());

		Mockito.when(originalFlowBean.getRepresentatives()).thenReturn(
				representativeForm);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.APPLICANT, flowModeId, ImportType.PRIORITY)).thenReturn(true);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.REPRESENTATIVE, flowModeId, ImportType.PRIORITY)).thenReturn(true);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.PERSON, "", ImportType.PRIORITY)).thenReturn(true);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.REPRESENTATIVE, flowModeId, ImportType.PRIORITY)).thenReturn(true);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.LANGUAGES, flowModeId, ImportType.PRIORITY)).thenReturn(true);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.MARKREFERENCE, flowModeId, ImportType.PRIORITY)).thenReturn(true);
		
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.APPLICANT, flowModeId, ImportType.PRIORITY)).thenReturn(true);
			
		GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		goodAndServiceForm.setClassId("classId");
		
		originalFlowBean.addGoodAndService(goodAndServiceForm);

		eSFilterImportable.filterFlowBean(originalFlowBean, originalFlowBean, flowModeId, ImportType.PRIORITY);
	}
	
	@Test
	public void filterFlowBeanCases(){
		Mockito.when(viewConfiguration.getImportableSection(Mockito.any(AvailableSection.class), Mockito.anyString(), Mockito.any(ImportType.class))).thenReturn(false);
		eSFilterImportable.filterFlowBean(null, null, null, ImportType.PRIORITY);
		
		String flowModeId="flowModeId";
		ImportType importType= ImportType.PRIORITY;
		ESFlowBean newFlowBean=Mockito.mock(ESFlowBean.class);
		RepresentativeForm so=Mockito.mock(RepresentativeForm.class);
		Mockito.when(so.getAvailableSectionName()).thenReturn(AvailableSection.APPLICANT_NATURALPERSON);
		List<RepresentativeForm> list=new ArrayList<RepresentativeForm>();
		list.add(so);
		Mockito.when(newFlowBean.getRepresentatives()).thenReturn(list);
		ApplicantForm af=Mockito.mock(ApplicantForm.class);
		Mockito.when(af.getAvailableSectionName()).thenReturn(AvailableSection.APPLICANT_NATURALPERSON);
		List<ApplicantForm> value=new ArrayList<ApplicantForm>();
		value.add(af);
		Mockito.when(newFlowBean.getApplicants()).thenReturn(value);
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.APPLICANT, flowModeId, importType)).thenReturn(true);
		Mockito.when(viewConfiguration.getImportableSection(AvailableSection.REPRESENTATIVE, flowModeId, importType)).thenReturn(true);
		Mockito.when(viewConfiguration.getImportableSection(so.getAvailableSectionName(), flowModeId, importType)).thenReturn(false);
		eSFilterImportable.filterFlowBean(null, newFlowBean, flowModeId, importType);
	}

}
