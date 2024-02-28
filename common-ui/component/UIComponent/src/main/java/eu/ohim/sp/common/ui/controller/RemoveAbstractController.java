/*******************************************************************************
 * * $Id:: RemoveAbstractController.java 49751 2012-11-05 10:06:51Z villama      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;

/**
 * Generic controller that could be used to remove an object from the collection
 * The request should contain the id of the object that we want to remove
 * 
 * @author ckara
 * 
 */
public class RemoveAbstractController {

    private static final Logger logger = Logger.getLogger(RemoveAbstractController.class);

    /**
     * Generic Controller for removing an entity(Applicant, Representative,
     * Priority, Seniority, ...) from the session<br/> It is important to exist
     * a method <b>getId()</b> in the bean to identify the entities<br/>
     * Values to be defined in efiling-servlet is <br/> <strong>commandClass</strong>
     * the class of this entity<br/> <strong>commandName</strong> the name of
     * the variable that will be passed in the returned page<br/>
     * <strong>viewName</strong> the page where the entity will be displayed
     * 
     * @param id
     * @param flowBean
     * @param removeParameters
     * @return ModelAndView
     */
    public ModelAndView handle(String id, FlowBean flowBean, RemoveParameters removeParameters) {
        logger.info("START handle");
        ModelAndView model = new ModelAndView(removeParameters.getFormView());
        try {
            if (id != null && !id.trim().equals("")) {
                logger.debug("parameter exists");
                Object command = flowBean.getObject(removeParameters.getCommandClass(), id);

                if (command != null) {
                    flowBean.removeObject(removeParameters.getCommandClass(), id);
                    model.setViewName(removeParameters.getSuccessView());
                    model.getModelMap().put("command", command);
                }

            }
        } catch (SPException ex) {
            throw new SPException(ex.getMessage(), ex,
                    "efiling.remove." + removeParameters.getCommandName());
        }
        return model;
    }


}


