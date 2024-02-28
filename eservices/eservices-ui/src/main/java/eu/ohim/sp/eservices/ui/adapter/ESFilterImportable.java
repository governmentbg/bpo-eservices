package eu.ohim.sp.eservices.ui.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.FilterImportable;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration.ImportType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;

@Component
public class ESFilterImportable extends FilterImportable<ESFlowBean>{
    @Autowired
    private SectionViewConfiguration viewConfiguration;

    /**
     * It filters an imported form and checks if each element and each field should be imported according to
     * the sectionViewConfiguration
     * 
     * @param originalFlowBean the original form before the import
     * @param newFlowBean the imported form object
     * @param flowModeId the flow mode id in which the import takes place
     * @param importType the import type
     * @return the filtered imported data
     */
    @Override
    public ESFlowBean filterFlowBean(ESFlowBean originalFlowBean, ESFlowBean newFlowBean, String flowModeId, ImportType importType) {
        // If originalFlowBean is null just create a new one
        if (originalFlowBean == null) {
            originalFlowBean = new ESFlowBeanImpl();
        }

        // All the sections contained in the flowBean have to be filtered one by one

       
        // Filter Applicant Section
        if (viewConfiguration.getImportableSection(AvailableSection.APPLICANT, flowModeId, importType)) {
            // Import applicants
            originalFlowBean.clearApplicants();
            for (ApplicantForm so : newFlowBean.getApplicants()) {
                so.setImported(true);
                if (viewConfiguration.getImportableSection(so.getAvailableSectionName(), flowModeId, importType)) {
                    filterSingleObject(so, flowModeId, so.getAvailableSectionName(), importType);
                    originalFlowBean.addObject(so);
                }

            }
        }

        // Filter Representative Section
        if (viewConfiguration.getImportableSection(AvailableSection.REPRESENTATIVE, flowModeId, importType)) {
            // Import representatives
            originalFlowBean.clearRepresentatives();
            for (RepresentativeForm so : newFlowBean.getRepresentatives()) {
                so.setImported(true);
                if (viewConfiguration.getImportableSection(so.getAvailableSectionName(), flowModeId, importType)) {
                    filterSingleObject(so, flowModeId, so.getAvailableSectionName(), importType);
                    originalFlowBean.addObject(so);
                }
            }
        }

      

        // Filter Languages Section
        if (viewConfiguration.getImportableSection(AvailableSection.LANGUAGES, flowModeId, importType)) {
            copySingleObject(newFlowBean, originalFlowBean, flowModeId, AvailableSection.LANGUAGES, importType);
        }

       

        // Filter MarkReference Section
        if (viewConfiguration.getImportableSection(AvailableSection.MARKREFERENCE, flowModeId, importType)) {
            copySingleObject(newFlowBean, originalFlowBean, flowModeId, AvailableSection.MARKREFERENCE, importType);
        }

       
        ((AbstractImportableForm) originalFlowBean).setImported(true);

        return originalFlowBean;
    }



}
