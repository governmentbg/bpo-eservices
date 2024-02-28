package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBeanImpl;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 09.11.2018
 */
@Component
public class PTFilterImportable extends eu.ohim.sp.common.ui.adapter.FilterImportable<PTFlowBeanImpl> {

    @Override
    public PTFlowBeanImpl filterFlowBean(PTFlowBeanImpl originalFlowBean, PTFlowBeanImpl newFlowBean, String flowModeId, SectionViewConfiguration.ImportType importType) {
        return originalFlowBean;
    }
}
