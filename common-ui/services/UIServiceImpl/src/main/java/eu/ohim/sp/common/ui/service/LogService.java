package eu.ohim.sp.common.ui.service;

import eu.ohim.sp.common.logging.model.ActionType;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.LogServiceInterface;
import eu.ohim.sp.common.ui.util.LoggedForStatistics;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ionitdi
 */
@Service
public class LogService implements LogServiceInterface
{
    private static Logger logger = Logger.getLogger(LogService.class);

    @Autowired
    ConfigurationServiceDelegatorInterface config;

    @LoggedForStatistics(ActionType = ActionType.VISIT, passingFlowModeId = true)
    public void registerVisit(String flowModeId)
    {
        logger.debug("Visit registered");
    }
}
