/*
 * ReportService:: ReportService 23/10/13 12:53 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.report;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.path.PathResolutionStrategy;
import eu.ohim.sp.common.path.SpConfigDirPathResolutionStrategy;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * ReportService implementation that uses BIRT Engine 4.2
 * It loads the BIRT Engine in the initilization and exposes one method
 * to generate a pdf report given the template name (rptdesign file) and the CORE object with the data
 * 
 * @author soriama
 * 
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class ReportServiceBean implements ReportServiceRemote, ReportServiceLocal {

    /**
     * Error code for SPException related with the report generation
     */
    public static final String ERROR_REPORT_CREATION = "error.report.creation";


    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger(ReportServiceBean.class);
    public static final String TEMPLATE_PATH = "template_path";

    /**
     * Injected instance of the configurationService EJB to get parameters from the CMS
     */
    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService configurationService;

    @EJB(lookup = "java:global/documentLocal")
    private DocumentService documentService;

    /**
     * BIRT ReportEngine object used to create the reports
     */
    private ReportEngine reportEngine;

    private PathResolutionStrategy pathResolver;

    /**
     * Initialization method that initializes de BIRT engine
     */
    @PostConstruct
    public void init() {
        if (pathResolver == null) {
            pathResolver = new SpConfigDirPathResolutionStrategy();
        }
    }

    @Override
    public byte[] generateReport(String module, String template, String localeCode, Object... args) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start pdf generation for template: " + template);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String resourceFolder = pathResolver.resolvePath(configurationService.getValue(TEMPLATE_PATH, module));

        if (resourceFolder == null) {
            resourceFolder = this.getClass().getClassLoader().getResource("img/draft.png").toString();
            resourceFolder = resourceFolder.substring(0, resourceFolder.lastIndexOf('/'));
        }

        BirtResourceLocator resourceLocator = new BirtResourceLocator();
        resourceLocator.setResourcePath(resourceFolder);

        IRunAndRenderTask task = null;
        IReportRunnable report;

        try {
            // Get report design as stream from CMS
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Getting rptdesign file from the CMS. Template: " + template);
            }

            report = BirtEngineSingleton.getInstance().getDesign(template, configurationService, module, resourceLocator);
            task = BirtEngineSingleton.getInstance().getReportEngine().createRunAndRenderTask(report);

            task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ReportService.class.getClassLoader());
            task.getAppContext().put("args", args);
            if (localeCode != null) {
                task.setLocale(new Locale(localeCode));
            }

            // Set the rendering options as pdf
            PDFRenderOption options = new PDFRenderOption();
            options.setOutputFormat("pdf");
            options.setOutputStream(out);
            task.setRenderOption(options);

            // Execute the BIRT task to generate the PDF
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Executing pdf generation. Template: " + template);
            }
            task.run();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(template + " pdf created succesfully");
            }
            // Return the pdf as a byte[]
            return out.toByteArray();
        } catch (EngineException e) {
            LOGGER.error("Error executing createRunAndRenderTask in Birt Engine");
            LOGGER.error(e.getMessage());
            throw new SPException("Exception creating pdf report with Birt Engine", e, ERROR_REPORT_CREATION);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error getting ByteArrayInputStream of the rptdesign file");
            LOGGER.error(e.getMessage());
            throw new SPException("Exception creating pdf report with Birt Engine", e, ERROR_REPORT_CREATION);
        } finally {
            // Close the task if everything went well
            if (task != null) {
                task.close();
            }
        }
    }

    @Override
    public byte[] generateSimilaritySpreadSheet(String module, List<TradeMark> tmList) {
        String resourceFolder = pathResolver.resolvePath(configurationService.getValue(TEMPLATE_PATH, module));
        return new ExcelSimilarityReportGenerator(resourceFolder)
            .generate(tmList)
            .toByteArray();
    }

    @Override
    public void sendMail(String mail, String subject, String content, List<Document> documentList) {
        MailCore mailCore = new MailCore(configurationService, documentService);

        mailCore.sendEmail(mail, subject, content, documentList);
    }

}
