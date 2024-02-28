package eu.ohim.sp.ui.tmefiling.util;

import eu.ohim.sp.common.logging.model.ApplicationType;
import eu.ohim.sp.common.ui.util.StatisticalLoggingInfoProvider;

/**
 * @author karalch
 */
public class TMStatisticalLoggingInfoProvider extends StatisticalLoggingInfoProvider {
    @Override
    public ApplicationType getApplicationType(String s) {
        return ApplicationType.CTM_EFILING_NORMAL_FORM;
    }
}
