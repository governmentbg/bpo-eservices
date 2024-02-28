/*******************************************************************************
 * * $Id:: ImportRepresentativeController.java 50771 2012-11-14 15:10:27Z karalc#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.getViewByRepresentativeType;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.getFormFromResponse;

import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.PersonChangeControllerHelper;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * @author velosma
 */
@Controller
public class ImportPersonChangeController extends ImportAbstractController {

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired
	private PersonServiceInterface personService;

	/**
	 * flow bean
	 */
	@Autowired
	protected FlowBean flowBean;

	/**
	 * It returns a new Change of Representative obj if no parameters are passed, so a new
	 * Change of Representative to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 * 
	 * @param request
	 * @param identifier
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@PreAuthorize("hasRole('Representative_Import')")
	@RequestMapping(value = "importChangePerson", method = RequestMethod.GET)
    public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String identifier,
            @RequestParam(required = false, value = "changeType") ChangePersonType changeType, BindingResult result) {
		if (StringUtils.isBlank(identifier)) {
			return null;
		}

		String flowModeId = (String) request.getAttribute("flowModeId");
        ModelAndView response = importForm(identifier, null, flowBean, flowModeId);
		if (response.getModelMap().containsKey("errorCode")) {
			response.setViewName("errors/importError");
            String errorCode = response.getModelMap().get("errorCode").toString();
			response.addObject("errorCode", errorCode + ".representative");
		}
        if (changeType != null) {
            RepresentativeForm form = getFormFromResponse(response);
            if (form != null) {
                form.setChangeType(changeType);
            }
        }

		return response;
	}

	@Override
    protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
        RepresentativeForm uiForm = personService.importRepresentative(identifier, flowModeId);
        if (!contains(configurationServiceDelegator.getRepresentativeTypes(flowModeId), uiForm.getType().name()
                .toLowerCase())) {
            if (uiForm.getType().name().toLowerCase().equalsIgnoreCase("association")) {
                uiForm = association2LegalEntityForm((ChangeRepresentativeAssociationForm) uiForm);
                uiForm.setType(RepresentativeKindForm.LEGAL_ENTITY);
            } else {
                return null;
            }
        }
        Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.PERSONCHANGE_ADD_MAXNUMBER, "eu.ohim.sp.core.person");
        ImportableWrapper wrapper = new ImportableWrapper();
        if (uiForm != null) {
            wrapper.setImportable(uiForm);

            PersonChangeControllerHelper.ViewInfo viewInfo = getViewByRepresentativeType(uiForm.getType());

            wrapper.setImportParameters(new ImportParameters(ChangeProfessionalPractitionerForm.class,
                    viewInfo.getFormName(), viewInfo.getViewName(), maxNoOfEntities, "representatives"));
        }
        return wrapper;
    }

     private ChangeRepresentativeLegalEntityForm association2LegalEntityForm(ChangeRepresentativeAssociationForm myEntityOrig) {
        ChangeRepresentativeLegalEntityForm myEntiryDest = new ChangeRepresentativeLegalEntityForm();
        BeanUtilsBean instance = BeanUtilsBean.getInstance();
        try {
            instance.copyProperties(myEntiryDest, myEntityOrig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        myEntiryDest.setNameOfLegalEntity(myEntityOrig.getName());
        myEntiryDest.setBusinessVatNumber(myEntityOrig.getAssociationId());
        myEntiryDest.setLegalForm("");
        myEntiryDest.setName(myEntityOrig.getName());

        return myEntiryDest;
    }	
	
}
