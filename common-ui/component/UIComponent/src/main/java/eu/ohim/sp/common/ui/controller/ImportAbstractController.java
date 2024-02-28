/*******************************************************************************
 * * $Id:: ImportAbstractController.java 50771 2012-11-14 15:10:27Z karalch      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;

/**
 * Generic abstract controller that is
 * responsible for importing the details of
 * a form entity Must be extended by a class
 * which contains the actual mappings
 * 
 * @author soriama
 */
public abstract class ImportAbstractController {
    /**
     * Logger for this class and subclasses
     */
    protected static final Logger logger = Logger.getLogger(ImportAbstractController.class);

    /**
     * The actual controller with the mappings that
     * extends this class must call this method
     * 
     *
     * @param identifier
     *            : identifier of object to be filled with the imported data
     * @param office
     * 			  : 
     * @param flowBean
     *            : object which contains all the application data saved in the
     *            flow scope
     * @param flowModeId
     * @return the prepared model and view
     */
    public  ModelAndView importForm(String identifier, String office, FlowBean flowBean, String flowModeId) {
        logger.info("START importForm");
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (!StringUtils.isBlank(identifier)) {
                ImportableWrapper importedObject = importObject(identifier, office, flowModeId);
                if(importedObject == null || importedObject.getImportable() == null) {
                    modelAndView.addObject("errorCode", "error.import.noObjectFound");
                } else {
                    ImportParameters importParameters = importedObject.getImportParameters();
                    // Check whether the object already exists in the flowScope
                    Object objectFlowBean = flowBean.getObject(importParameters.getCommandClass(), identifier);
                    if (objectFlowBean == null) {
                        logger.debug("object to be imported does not exist in the application");
                        // Check whether the collection has reached the maximum
                        // entities allowed
                        if (flowBean.getCollection(importParameters.getCommandClass()) != null
                                && importParameters.getMaximumEntities() != null
                                && flowBean.getCollection(importParameters.getCommandClass()).size() >= importParameters
                                .getMaximumEntities()) {
                            modelAndView.addObject("errorCode", "error.import.maxEntitiesReached"
                                    + importParameters.getCollectionName());
                            modelAndView.addObject("errorCodeArgs", importParameters.getMaximumEntities().toString());
                        } else {
                            // Import the object from the corresponding service
                            modelAndView.addObject(importParameters.getCommandName(), importedObject.getImportable());
                            modelAndView.setViewName(importParameters.getFormView());
                        }
                    } else {
                        modelAndView.addObject("errorCode", "error.import.objectExists");
                    }
                }
            }
        } catch (SPException ex) {
            /*Since the exception is caught and not re-thrown
			so that is caught by the ExceptionResolver, print
			the stack trace manually into the logs*/
            logger.error("Err", ex);
            modelAndView.addObject("errorCode", "error.import");
        }

        logger.info("END importForm");
        return modelAndView;
    }

    protected abstract ImportableWrapper importObject(String command, String office, String flowModeId);

    protected  Integer getIntegerSetting(ConfigurationServiceDelegatorInterface configurationServiceDelegator,
            String setting, String component)
    {
        String result = configurationServiceDelegator.getValue(setting, component);
        try
        {
            if(result == null || result.isEmpty())
                return null;
            int max = Integer.parseInt(result);
            return max;
        }
        catch(NumberFormatException e)
        {
            throw new SPException("Error while parsing a configuration integer", e, "error.configuration.parsing");
        }
    }
    protected boolean contains(List<String> listTypes, String type) {
		if (listTypes != null && type != null) {
			for (String item : listTypes) {
				String itemFormatted = removeWrappedWords(item);
				String typeFormatted = removeWrappedWords(type);
				if (itemFormatted.equals(typeFormatted)) {
					return true;
				}
			}
		}
		return false;
	}

	String removeWrappedWords(String type){
		return type.toLowerCase()
		.replace("representative", "").replace("applicant", "").replace("_", "")
		.replace(" ", "");
	}
}
