package eu.ohim.sp.dsefiling.ui.adapter;

import eu.ohim.sp.common.service.Component;
import eu.ohim.sp.common.ui.adapter.FilterImportable;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration.ImportType;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

@Component
public class DSFilterImportable extends FilterImportable<DSFlowBean>{

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
    public DSFlowBean filterFlowBean(DSFlowBean originalFlowBean, DSFlowBean newFlowBean, String flowModeId,
            ImportType importType) {
    	return originalFlowBean;
    }

}
