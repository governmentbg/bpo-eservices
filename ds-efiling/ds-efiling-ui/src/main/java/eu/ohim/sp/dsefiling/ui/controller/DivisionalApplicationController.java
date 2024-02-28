/*******************************************************************************
 * * $Id:: SearchTrademarkController.java 36713 2014-02-25 14:19:16Z segovro     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.dsefiling.ui.controller;

import com.google.common.collect.Lists;
import eu.ohim.sp.common.ui.adapter.design.ProductIndicationFactory;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.util.LocarnoFormComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Controller to handle divisional section calls
 * 
 * @author velosma
 * 
 */
@Controller
public class DivisionalApplicationController  extends AddDesignsLinkedController {

	/** Logger for this class and subclasses */
	private static final Logger logger = Logger.getLogger(DivisionalApplicationController.class);

    private static final String VIEW_ADD_DIVISIONAL_APPLICATION = "divisionalApplication/divisional_application";
    private static final String MODEL_DIVISIONAL_APPLICATION = "divisionalApplicationForm";
    private static final String DIVISIONAL_DESIGN_ID = "numberDivisionalApplication";

    private LocarnoFormComparator locarnoFormComparator = new LocarnoFormComparator();

    /**
     * Custom Date Editor
     */
    @Autowired
    private CustomDateEditor customDateEditor;

    @Autowired
    private DSFlowBean flowBean;

	@Autowired
	private DesignSearchService designSearchService;

    @Autowired
    private ProductIndicationFactory indicationProductFactory;

    @Value("${defaultLanguage}")
    private String defaultLanguage;

    @Value("${sp.office}")
    private String office;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, customDateEditor);
    }

    /**
     * It returns imported divisional application view.
     *
     * @param id the id of the design
     * @return a modelAndView object with the object
     */
    @PreAuthorize("hasRole('Import_Divisional')")
    @RequestMapping(value = "importDivisionalApplication", method = RequestMethod.POST)
    public ModelAndView importDivisionalApplication(
            @ModelAttribute(MODEL_DIVISIONAL_APPLICATION) DSDivisionalApplicationForm divisionalApplicationForm,
    		@RequestParam(required = true, value = DIVISIONAL_DESIGN_ID) String id) {
		logger.info("START divisional import");

        ModelAndView modelAndView = new ModelAndView();

        DesignApplication designApplication = designSearchService.getDesignApplication(office, id, null,false);

        if (designApplication != null) {
            setData(divisionalApplicationForm, designApplication);
            modelAndView.setViewName(VIEW_ADD_DIVISIONAL_APPLICATION);
        } else {
            modelAndView.setViewName("errors/importError");
            modelAndView.addObject("errorCode", "error.import.noObjectFound.design");
        }

        logger.info("END divisional import");
		return modelAndView;
    }

    /**
     * Sets data to flowBean object
     * @param designApplication the design application
     */
    private void setData(DSDivisionalApplicationForm divisionalApplicationForm, DesignApplication designApplication) {
        divisionalApplicationForm.setNumberDivisionalApplication(designApplication.getApplicationNumber());
        divisionalApplicationForm.setDateDivisionalApplication(designApplication.getApplicationDate());

        String appLanguage = flowBean.getFirstLang()==null?defaultLanguage:flowBean.getFirstLang();

        divisionalApplicationForm.setLocarno(new ArrayList<>());
        if (CollectionUtils.isNotEmpty(designApplication.getDesignDetails())) {
            for (Design design : designApplication.getDesignDetails()) {
                if (CollectionUtils.isNotEmpty(designApplication.getDesignDetails().get(0).getProductIndications())) {
                    for (ProductIndication productIndication : design.getProductIndications()) {
                        LocarnoAbstractForm locarno = indicationProductFactory.convertFrom(productIndication);
                        if ((productIndication.getLanguageCode()==null || appLanguage.equals(productIndication.getLanguageCode())) &&
                            !divisionalApplicationForm.getLocarno().stream().anyMatch(loc -> locarnoFormComparator.compare(loc,locarno)==0)) {
                            divisionalApplicationForm.getLocarno().add(locarno);
                        }
                    }
                }
            }
        }
        // Set locarno classes into flowBean
        flowBean.setLocarno(divisionalApplicationForm.getLocarno());
    }
}
