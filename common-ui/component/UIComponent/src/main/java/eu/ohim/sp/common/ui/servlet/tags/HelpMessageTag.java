package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.ConfigurationServiceRemote;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * @author ionitdi
 */
public class HelpMessageTag extends FlowContextAwareTag
{
    private String textKey;

    public String getTextKey()
    {
        return textKey;
    }

    public void setTextKey(String textKey)
    {
        this.textKey = textKey;
    }

    /**
     *
     */
    private static final long serialVersionUID = -2290152088501092762L;

    @Override
    protected int doStartTagInternal()
    {
        ConfigurationServiceDelegatorInterface configuration = (ConfigurationServiceDelegatorInterface) getRequestContext().getWebApplicationContext().getBean(
                "configurationServiceDelegator");
        
       /* ConfigurationServiceRemote configurationService = (ConfigurationServiceRemote) getRequestContext().getWebApplicationContext().getBean(
                "configurationService");
*/
        boolean messageServiceEnabled = configuration.isServiceEnabled(AvailableServices.HELP_MESSAGE_SERVICE.value());
        if (messageServiceEnabled)
        {
            boolean usingHelpFileResources = configuration.isServiceEnabled(AvailableServices.HELP_MESSAGE_SERVICE_USES_FILES.value());

            if (usingHelpFileResources)
            {
                return EVAL_BODY_INCLUDE;
            }

            // call the service to get the corresponding help message
            String messageFromService = null;

            try
            {
                messageFromService = configuration.getMessage(getTextKey());
            }
            catch (Exception e)
            {
                // Because this is not a vital service, whatever exceptions occur in the service call
                // should not stop the normal run of the application. Therefore, just an informational
                // message is logged and the program continues without notifying the user of this exception.
                logger.info("Could not get message from help message service for key: " + getTextKey());
                logger.info(e.getMessage());

                // TODO: consider whether to return the message from the alternative help file resource
            }

            // if the message is not empty or null, display it
            if (!StringUtils.isBlank(messageFromService))
            {
                try
                {
                    pageContext.getOut().print(messageFromService);
                }
                catch (IOException e)
                {
                    logger.info("Failed to print help message information. Message to print: " + messageFromService);
                    logger.info(e.getMessage());
                }
            }

            // otherwise, skip body
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }
}
