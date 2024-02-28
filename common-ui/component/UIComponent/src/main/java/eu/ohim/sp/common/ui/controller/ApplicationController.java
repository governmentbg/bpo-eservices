/*******************************************************************************
 * * $Id:: ApplicationController.java 113496 2013-04-22 15:03:04Z karalc#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.flow.section.FileUploadFlowBean;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ApplicationServiceInterface;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import java.util.UUID;

/**
 * Controller that manages the saving/loading of the application
 * 
 */
@Controller
public class ApplicationController {

    private static final Logger logger = Logger.getLogger(ApplicationController.class);

    /** The locale resolver. */
    @Autowired
    private SessionLocaleResolver localeResolver;

    @Autowired
    private FlowBean flowBean;
    
    @Autowired
    private FileUploadFlowBean fileUploadSection;

    @Autowired
    private FileServiceInterface fileService;

    @Autowired
    private ApplicationServiceInterface draftApplicationService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private FlowScopeDetails flowScopeSession;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private SubmittedMap submittedMap;

    @Autowired
    private AbstractMessageSource messageSource;

    @Autowired
    private LocaleComponent localeComponent;
    
    @Autowired
    private FileUploadController fileController;


    /**
     * Controller that is called to save an application
     * Check how is better to submit the whole form
     */
    @PreAuthorize("hasRole('Save_Load_Application')")
    @RequestMapping(value = "saveApplication", method = RequestMethod.POST)
    @ResponseBody
    public String saveApplication() {
        draftApplicationService.saveApplication(flowBean, false);
        return "Success";
    }

    /**
     * Method that manages the view when an SPException
     * is thrown
     * 
     * @param e Exception
     * @return String "Failed" message
     */
    @ExceptionHandler(SPException.class)
    @ResponseBody
    public String handleException(Exception e) {
        logger.error(e);
        return "Failed";
    }

    @RequestMapping(value = "submitted")
    public ModelAndView submitPage(@RequestParam String uuid) {
        ModelAndView mav = new ModelAndView("submittedpage");
        mav.addObject("submittedForm", submittedMap.get(uuid));
        return mav;
    }

    @RequestMapping(value = "submitted_notmdn")
    public ModelAndView submitPageNoTMDN(@RequestParam String uuid) {
        ModelAndView mav = new ModelAndView("submittedpage.notmdn");
        mav.addObject("submittedForm", submittedMap.get(uuid));
        return mav;
    }
    
    @RequestMapping(value = "finalReceipt")
    public ModelAndView downloadReceipt(@RequestParam String uuid, HttpServletResponse response) throws Exception {
    	return fileController.handleRequest(submittedMap.get(uuid).getDocumentId(), response);
    }
    

    /**
     * Controller that is called to save locally
     * Check how is better to submit the whole form
     */
    @PreAuthorize("hasRole('Save_Load_Locally')")
    @RequestMapping(value = "saveToPC", method = RequestMethod.GET)
    @ResponseBody
    public byte[] saveToPC(HttpServletResponse response) {        
        byte[] b = draftApplicationService.saveApplicationLocally(flowBean);
        logger.debug(b.length);
        response.setHeader("Content-Disposition", "attachment; filename=application.xml");
        return b;
    }

    /**
     * Controller that is called to save locally
     * Check how is better to submit the whole form
     */
    @RequestMapping(value = "callback")
    public ModelAndView callback() {
        return new ModelAndView("callback");
    }

    /**
     * Controller that is called to load a stored application
     * 
     * @param loadedApplication the loaded application
     * @return url
     * @throws IOException 
     */
    @PreAuthorize("hasRole('Save_Load_Locally')")
    @RequestMapping(value = "loadApplication", method = RequestMethod.POST)
    public String loadApplication(HttpServletResponse response,
            @RequestParam("loadApplicationXML") MultipartFile loadedApplication) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading a locally saved application : " + loadedApplication.getName());
        }

        String provisionalId = draftApplicationService.loadApplicationLocally(loadedApplication.getBytes());

        try {
            String random_load = UUID.randomUUID().toString();
            flowScopeSession.setLid(random_load);
            String s = flowScopeDetails.getFlowModeId() + ".htm?form_id=" + provisionalId + "&lid=" + random_load;
            response.getOutputStream().write(s.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * Action used to clear the cache in the {@link ConfigurationServiceDelegatorInterface}
     * 
     * @return String
     */
    @RequestMapping(value = "emptyCache", method = RequestMethod.GET)
    @ResponseBody
    public String emptyCache(@RequestParam(required=false, value="cache") String cacheName) {
    	logger.debug("START emptyCache : " + cacheName);
        if (StringUtils.isNotBlank(cacheName)) {
            configurationServiceDelegator.emptyCache(cacheName);
        } else {
            configurationServiceDelegator.emptyCache();
        }
        return "cleared";
    }

    /**
     * Action used to clear the cache in the {@link ConfigurationServiceDelegatorInterface}
     * 
     * @return String
     */
    @RequestMapping(value = "emptyCacheEServices", method = RequestMethod.GET)
    @ResponseBody
    public String emptyCacheEServices(@RequestParam(required=false, value="cache") String cacheName) {
    	logger.debug("START emptyCacheEServices : " + cacheName);
        if (StringUtils.isNotBlank(cacheName)) {
            configurationServiceDelegator.emptyCache(cacheName);
        } else {
            configurationServiceDelegator.emptyCacheEServices();
        }
        return "cleared";
    }
    
    @RequestMapping(value = "message", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String getMessage(HttpServletRequest request, @RequestParam("code") String code,
            @RequestParam(value = "args", required = false) String args) {
        return messageSource.getMessage(code, StringUtils.isNotBlank(args) ? args.split(",") : null,
                localeResolver.resolveLocale(request));
    }

}
