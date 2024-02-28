package eu.ohim.sp.common.logging;

import eu.ohim.sp.common.logging.model.StatisticalInfo;
import org.apache.log4j.Logger;

/**
 * @author ionitdi
 */
public class StatisticalLoggerImpl implements StatisticalLogger
{
    private static final Logger errorLogger = Logger.getLogger(StatisticalLoggerImpl.class);

    private static final String SEPARATOR = "|";

    private StatisticalLogWriter logWriter;

    public StatisticalLoggerImpl(StatisticalLogWriter logWriter)
    {
        this.logWriter = logWriter;
    }


    @Override
    public void log(StatisticalInfo statInfo)
    {
        if(statInfo == null || statInfo.getBasicInfo() == null)
        {
            errorLogger.debug("There was no statistical information passed.");
            return;
        }

        StringBuilder logLine = new StringBuilder();

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getTimestamp());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getEFilingId());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getUserLanguage());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getIP());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getExternalOrInternal());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getUserGroup());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getUsername());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getApplicationType());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getAction());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getEntityStatus());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getEntityType());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getEntityAffected());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getGroundOfContactForm());

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().isEntityBlockedUnderRenewalProcess() ? "true" : "false");

        appendNonFinalToLogLine(logLine, statInfo.getBasicInfo().getInspectionRequestType());

        // final information to log
        appendFinalToLogLine(logLine, statInfo.getBasicInfo().getPaymentType());

        logWriter.writeLine(logLine.toString());
    }

    private void appendNonFinalToLogLine(StringBuilder logLine, String informationToAppend)
    {
        if(informationToAppend != null)
        {
            logLine.append(informationToAppend);
        }
        logLine.append(SEPARATOR);
    }

    private void appendNonFinalToLogLine(StringBuilder logLine, Enum informationToAppend)
    {
        if(informationToAppend != null)
        {
            logLine.append(informationToAppend.toString());
        }
        logLine.append(SEPARATOR);
    }

    private void appendFinalToLogLine(StringBuilder logLine, Enum informationToAppend)
    {
        if(informationToAppend != null)
        {
            logLine.append(informationToAppend.toString());
        }
    }
}