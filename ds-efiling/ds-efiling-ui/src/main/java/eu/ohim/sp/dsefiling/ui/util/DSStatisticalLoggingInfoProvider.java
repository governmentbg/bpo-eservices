package eu.ohim.sp.dsefiling.ui.util;

import eu.ohim.sp.common.logging.model.ApplicationType;
import eu.ohim.sp.common.ui.util.StatisticalLoggingInfoProvider;

public class DSStatisticalLoggingInfoProvider extends StatisticalLoggingInfoProvider{

    @Override
	public  ApplicationType getApplicationType(String flowModeId)
    {
        if(flowModeId == null)
        {
            return ApplicationType.OTHER;
        }
        if(flowModeId.equals("oneform"))
        {
            return ApplicationType.DS_EFILING_NORMAL_FORM;
        }
        if(flowModeId.equals("wizard"))
        {
            return ApplicationType.DS_EFILING_WIZARD_FORM;
        }
        return ApplicationType.OTHER;
    }
}
