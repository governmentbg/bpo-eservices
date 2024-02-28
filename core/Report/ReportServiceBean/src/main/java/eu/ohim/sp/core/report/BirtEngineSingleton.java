package eu.ohim.sp.core.report;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.ReportEngine;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gutieal on 09/04/2014.
 * First implementation for the sharing of the BIRT engine.
 */
public final class BirtEngineSingleton {

    private static BirtEngineSingleton singleton;

    private static final Map<String, IReportRunnable> designMap = Collections.synchronizedMap(new HashMap<String, IReportRunnable>());

    /**
     * Logger for this class
     */
    private static Logger logger = Logger.getLogger(BirtEngineSingleton.class);

    /**
     * BIRT ReportEngine object used to create the reports
     */
    private ReportEngine reportEngine;

    private final String TEMPLATE_PATH = "template_path";


    private BirtEngineSingleton() {
        reportEngine = new ReportEngine(new EngineConfig());
    }

    public static synchronized BirtEngineSingleton getInstance() {
        if (singleton == null) {
            singleton = new BirtEngineSingleton();
        }

        return singleton;
    }

    public IReportRunnable getDesign(String template, ConfigurationService configurationService,
                                     String module, BirtResourceLocator resourceLocator)
            throws UnsupportedEncodingException, EngineException {

        synchronized (designMap) {
            String reportDesign = module+"-"+template;
            if (designMap.get(reportDesign) == null) {
                String reportString = configurationService.getXml(template, module);
                if (reportString == null) {
                    throw new SPException("Error reading template " + template + " from the CMS", null,
                            ReportServiceBean.ERROR_REPORT_CREATION);
                }
                ByteArrayInputStream reportStream = new ByteArrayInputStream(reportString.getBytes("UTF-8"));

                // Birt tasks to set up the pdf generation
                if (logger.isDebugEnabled()) {
                    logger.debug("Set up pdf generation. Template: " + template);
                }
                IReportRunnable report = reportEngine.openReportDesign("report", reportStream, resourceLocator);

                designMap.put(reportDesign, report);
            }
            return designMap.get(reportDesign);
        }
    }

    public ReportEngine getReportEngine() {
        return reportEngine;
    }

}