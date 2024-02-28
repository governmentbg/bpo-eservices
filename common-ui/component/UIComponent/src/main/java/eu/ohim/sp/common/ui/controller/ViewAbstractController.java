/*******************************************************************************
 * * $Id:: ViewAbstractController.java 49751 2012-11-05 10:06:51Z villama        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.ViewParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;

@Controller
public class ViewAbstractController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(ViewAbstractController.class);

    /**
     * Generic Controller for viewing the details of an entity(Applicant, Representative, Priority, Seniority, ...)<br/>
     * It is important to exist a method <b>getId()</b> in the application to identify the entities<br/>
     * Values to be defined in efiling-servlet is <br/>
     * <strong>commandClass</strong> the class of this entity<br/>
     * <strong>commandName</strong> the name of the variable that will be passed in the returned page<br/>
     * <strong>viewName</strong> the page where the entity will be displayed
     * @param id
     * @param flowBean
     * @param viewParameters
     * @return modelAndView object
     */
    public ModelAndView handle(String id, FlowBean flowBean, ViewParameters viewParameters) {
        logger.info("START handle");
        ModelAndView model = new ModelAndView(viewParameters.getFormView());
        try {
            if (StringUtils.isNotBlank(id)) {
                logger.debug("parameter exists");
                Object returnedObj = flowBean.getObject(viewParameters.getCommandClass(), id);

                if (returnedObj!=null) {
                    logger.debug("entity exists");
                    model.addObject(viewParameters.getCommandName(), returnedObj);
                    model.setViewName(viewParameters.getSuccessView());
                }
            }
        } catch (SPException e) {
            logger.error("Err", e);
            throw new SPException("Failed to find any object", e, "error.generic.view");
        }
        return model;
    }

}
