package eu.ohim.sp.ptefiling.ui.util;

import eu.ohim.sp.common.logging.model.ApplicationType;
import eu.ohim.sp.common.ui.util.StatisticalLoggingInfoProvider;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTStatisticalLoggingInfoProvider extends StatisticalLoggingInfoProvider {

    @Override
    public ApplicationType getApplicationType(String flowModeId) {
        return ApplicationType.OTHER;
    }
}
