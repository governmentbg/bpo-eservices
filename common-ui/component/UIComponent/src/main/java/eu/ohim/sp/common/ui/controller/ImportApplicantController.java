/*******************************************************************************
 * * $Id:: ImportApplicantController.java 50771 2012-11-14 15:10:27Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.form.person.UniversityForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ionitdi
 */
@Controller
public class ImportApplicantController extends ImportAbstractController {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Autowired
    private PersonServiceInterface personService;

    /**
	 * flow bean
	 */
	@Autowired
	protected FlowBean flowBean;

	@Autowired
	private FlowScopeDetails flowScopeDetails;

	/**
	 * It returns a new Applicant obj if no parameters are passed, so a new
	 * Applicant to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 *
	 * @param request
	 * @param identifier the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@PreAuthorize("hasRole('Applicant_Import')")
	@RequestMapping(value = "importApplicant", method = RequestMethod.GET)
	public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String identifier, BindingResult result) {
        if(StringUtils.isBlank(identifier))
        {
            return null;
        }
		String flowModeId = (String) request.getAttribute("flowModeId");
        ModelAndView response = importForm(identifier, null, flowBean, flowModeId);
        if(response.getModelMap().containsKey("errorCode"))
        {
            response.setViewName("errors/importError");
            String errorCode = response.getModelMap().get("errorCode").toString();
            response.addObject("errorCode", errorCode + ".applicant");
        }
        return response;
	}

	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
		ApplicantForm uiForm = personService.importApplicant(identifier, flowModeId);
       
		if (uiForm!=null && !contains(configurationServiceDelegator.getApplicantTypes(flowModeId)
				,((uiForm.getType().name().toLowerCase())))) {
			return null;
		}

		String maxNumberSource = ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER;
		if(flowScopeDetails != null && flowScopeDetails.getFlowModeId() != null && flowScopeDetails.getFlowModeId().equals("gi-efiling")){
			maxNumberSource=ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER_GI_EFILING;
		}
		Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                                                    maxNumberSource, ConfigurationServiceDelegatorInterface.PERSON_COMPONENT);

		ImportableWrapper wrapper = new ImportableWrapper();
		if (uiForm!=null) {
	        wrapper.setImportable(uiForm);
			switch (uiForm.getType()) {
				case LEGAL_ENTITY: {
					wrapper.setImportParameters(new ImportParameters(LegalEntityForm.class, "applicantLegalEntityForm",
	                                             "applicant/applicant_legalentity", maxNoOfEntities,
												 "applicants"));
					break;
				}
				case NATURAL_PERSON: {
					wrapper.setImportParameters(new ImportParameters(NaturalPersonForm.class, "applicantNaturalPersonForm",
															 "applicant/applicant_naturalperson", maxNoOfEntities,
	                                                         "applicants"));
					break;
				}
				case UNIVERSITY: {
					wrapper.setImportParameters(new ImportParameters(UniversityForm.class, "applicantUniversityForm",
															 "applicant/applicant_university", maxNoOfEntities,
	                                                         "applicants"));
					break;

				}				
				case NATURAL_PERSON_SPECIAL: {
					wrapper.setImportParameters(new ImportParameters(NaturalPersonSpecialForm.class, "applicantNaturalPersonSpecialForm",
															 "applicant/applicant_naturalpersonspecial", maxNoOfEntities,
	                                                         "applicants"));
					break;
				}
			}
		}
        return wrapper;
	}
}
