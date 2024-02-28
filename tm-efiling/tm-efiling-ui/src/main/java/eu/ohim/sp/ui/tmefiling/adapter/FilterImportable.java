/*******************************************************************************
 * * $Id::                                                          $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.adapter;

import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.common.ui.service.constant.AppConstants;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration.ImportType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.util.MarkViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component that is used to filter elements and fields according to configuration
 * 
 * @author soriama
 */
@Component
public class FilterImportable extends eu.ohim.sp.common.ui.adapter.FilterImportable<FlowBeanImpl> {

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
    public FlowBeanImpl filterFlowBean(FlowBeanImpl originalFlowBean, FlowBeanImpl newFlowBean, String flowModeId, ImportType importType) {
        // If originalFlowBean is null just create a new one
        if (originalFlowBean == null) {
            originalFlowBean = new FlowBeanImpl();
        }

        // All the sections contained in the flowBean have to be filtered one by one

        // Filter Priorities Section
        if (viewConfiguration.getImportableSection(AvailableSection.PRIORITY, flowModeId, importType)) {
            for (PriorityForm so : newFlowBean.getPriorities()) {
                filterSingleObject(so, flowModeId, AvailableSection.PRIORITY, importType);
                so.setImported(true);
            }
            // Replace the priorities with the filtered ones in the newFlowBean object
            originalFlowBean.clearPriorities();
            originalFlowBean.addAll(newFlowBean.getPriorities());
        }

        // Filter Seniorities Section
        if (viewConfiguration.getImportableSection(AvailableSection.SENIORITY, flowModeId, importType)) {
            for (SeniorityForm so : newFlowBean.getSeniorities()) {
                filterSingleObject(so, flowModeId, AvailableSection.SENIORITY, importType);
                so.setImported(true);
            }
            // Replace the seniorities with the filtered ones in the newFlowBean object
            originalFlowBean.clearSeniorities();
            originalFlowBean.addAll(newFlowBean.getSeniorities());
        }

        // Filter Exhibitions Section
        if (viewConfiguration.getImportableSection(AvailableSection.EXHIBITION, flowModeId, importType)) {
            for (ExhPriorityForm so : newFlowBean.getExhpriorities()) {
                filterSingleObject(so, flowModeId, AvailableSection.EXHIBITION, importType);
                so.setImported(true);
            }
            // Replace the exhibitions with the filtered ones in the newFlowBean object
            originalFlowBean.clearExhibitions();
            originalFlowBean.addAll(newFlowBean.getExhpriorities());
        }

        // Filter Transformations Section
        if (viewConfiguration.getImportableSection(AvailableSection.TRANSFORMATION, flowModeId, importType)) {
            for (TransformationForm so : newFlowBean.getTransformations()) {
                filterSingleObject(so, flowModeId, AvailableSection.TRANSFORMATION, importType);
                so.setImported(true);
            }
            // Replace the transformations with the filtered ones in the newFlowBean object
            originalFlowBean.clearTransformations();
            originalFlowBean.addAll(newFlowBean.getTransformations());
        }

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

        // Filter Goods and Services Section
        if (viewConfiguration.getImportableSection(AvailableSection.GOODSANDSERVICES, flowModeId, importType)) {
            for (GoodAndServiceForm so : newFlowBean.getGoodsAndServices()) {
                filterSingleObject(so, flowModeId, AvailableSection.GOODSANDSERVICES, importType);
                so.setImported(true);
            }
            // Replace the G&S with the filtered ones in the newFlowBean object
            originalFlowBean.clearGandS();
            for (GoodAndServiceForm gs : newFlowBean.getGoodsAndServices()) {
                originalFlowBean.addGoodAndService(gs);
            }
        }

        // Filter Languages Section
        if (viewConfiguration.getImportableSection(AvailableSection.LANGUAGES, flowModeId, importType)) {
            copySingleObject(newFlowBean, originalFlowBean, flowModeId, AvailableSection.LANGUAGES, importType);
        }

        // Filter TypeMark Section
        if (viewConfiguration.getImportableSection(AvailableSection.TYPEMARK, flowModeId, importType)) {
            AvailableSection a = null;
            if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.WordMarkType)) {
                a = AvailableSection.WORDMARK;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.FigurativeMarkType)) {
                a = AvailableSection.FIGURATIVE;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.FigurativeWordMark)) {
                a = AvailableSection.FIGWORDMARK;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.Mark3DType)) {
                a = AvailableSection.ThreeDMark;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.ColorMarkType)) {
                a = AvailableSection.COLOURMARK;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.SoundMarkType)) {
                a = AvailableSection.SOUNDMARK;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.OtherMarkType)) {
                a = AvailableSection.OTHER;
            } else if (newFlowBean.getMainForm().getMarkType().equals(AppConstants.Mark3DWord)) {
                a = AvailableSection.ThreeDWordMark;
            } else if(newFlowBean.getMainForm().getMarkType().equals(AppConstants.MarkMultimedia)){
                a = AvailableSection.MULTIMEDIAMARK;
            } else if(newFlowBean.getMainForm().getMarkType().equals(AppConstants.MarkPattern)){
                a = AvailableSection.PATTERNMARK;
            } else if(newFlowBean.getMainForm().getMarkType().equals(AppConstants.MarkPosition)){
                a = AvailableSection.POSITIONMARK;
            } else if(newFlowBean.getMainForm().getMarkType().equals(AppConstants.MarkHologram)){
                a = AvailableSection.HOLOGRAMMARK;
            } else if(newFlowBean.getMainForm().getMarkType().equals(AppConstants.MarkMotion)){
                a = AvailableSection.MOTIONMARK;
            }

            if (a != null) {
                copySingleObject(newFlowBean, originalFlowBean, flowModeId, a, importType);
            }
        }

        if (viewConfiguration.getImportableSection(AvailableSection.fromValue(MarkViewUtil.createViewsSectionId(originalFlowBean.getMainForm().getMarkType())), flowModeId, importType)) {
            originalFlowBean.clearMarkViews();
            if(newFlowBean.getMarkViews() != null && newFlowBean.getMarkViews().size()> 0){
                for (MarkViewForm so : newFlowBean.getMarkViews()) {
                    so.setImported(true);
                    if (viewConfiguration.getImportableSection(AvailableSection.fromValue(MarkViewUtil.createViewsSectionId(originalFlowBean.getMainForm().getMarkType())), flowModeId, importType)) {
                        filterSingleObject(so, flowModeId, so.getAvailableSectionName(), importType);
                        originalFlowBean.addObject(so);
                    }
                }
            }

        }

        // Filter MarkReference Section
        if (viewConfiguration.getImportableSection(AvailableSection.MARKREFERENCE, flowModeId, importType)) {
            copySingleObject(newFlowBean, originalFlowBean, flowModeId, AvailableSection.MARKREFERENCE, importType);
        }

        // Filter DivisionalApplication Section
        if (viewConfiguration.getImportableSection(AvailableSection.DIVISIONAL_APPLICATION_SECTION, flowModeId, importType)) {
            originalFlowBean.getMainForm().setNumberDivisionalApplication(newFlowBean.getMainForm().getNumberDivisionalApplication());
            originalFlowBean.getMainForm().setDateDivisionalApplication(newFlowBean.getMainForm().getDateDivisionalApplication());
            originalFlowBean.getMainForm().setClaimDivisionalApplication(newFlowBean.getMainForm().getClaimDivisionalApplication());
        }

        originalFlowBean.setImported(true);

        return originalFlowBean;
    }

}
