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

import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

/**
 * @author ionitdi
 */
@Controller
public class ImportRepresentativeController extends ImportAbstractController {

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
	 * It returns a new Representative obj if no parameters are passed, so a new
	 * Representative to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 * 
	 * @param request
	 * @param identifier
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@PreAuthorize("hasRole('Representative_Import')")
	@RequestMapping(value = "importRepresentative", method = RequestMethod.GET)
    public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String identifier,
            BindingResult result) {
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
		return response;
	}

	@Override
    protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
        RepresentativeForm uiForm = personService.importRepresentative(identifier, flowModeId);
        Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_ADD_MAXNUMBER, "eu.ohim.sp.core.person");
        ImportableWrapper wrapper = new ImportableWrapper();
        if (uiForm != null) {
            wrapper.setImportable(uiForm);
            switch (uiForm.getType()) {
                case PROFESSIONAL_PRACTITIONER: {
                    wrapper.setImportParameters(new ImportParameters(ProfessionalPractitionerForm.class,
                            "representativeProfessionalPractitionerForm",
                            "representative/representative_professionalpractitioner", maxNoOfEntities,
                            "representatives"));
                    break;
                }
                case EMPLOYEE_REPRESENTATIVE: {
                    wrapper.setImportParameters(new ImportParameters(EmployeeRepresentativeForm.class,
                            "representativeEmployeeRepresentativeForm",
                            "representative/representative_employeerepresentative", maxNoOfEntities, "representatives"));
                    break;
                }
                case LEGAL_PRACTITIONER: {
                    wrapper.setImportParameters(new ImportParameters(LegalPractitionerForm.class,
                            "representativeLegalPractitionerForm", "representative/representative_legalpractitioner",
                            maxNoOfEntities, "representatives"));
                    break;
                }
                case LEGAL_ENTITY: {
                    wrapper.setImportParameters(new ImportParameters(RepresentativeLegalEntityForm.class,
                            "representativeLegalEntityForm", "representative/representative_legalentity",
                            maxNoOfEntities, "representatives"));
                    break;
                }
                case NATURAL_PERSON: {
                    wrapper.setImportParameters(new ImportParameters(RepresentativeNaturalPersonForm.class,
                            "representativeNaturalPersonForm", "representative/representative_naturalperson",
                            maxNoOfEntities, "representatives"));
                    break;
                }
                case ASSOCIATION: {
                    wrapper.setImportParameters(new ImportParameters(RepresentativeAssociationForm.class,
                            "representativeAssociationForm", "representative/representative_association",
                            maxNoOfEntities, "representatives"));
                    break;
                }
            }
        }
        return wrapper;
    }
    
     private RepresentativeLegalEntityForm association2LegalEntityForm(RepresentativeAssociationForm myEntityOrig) {
        RepresentativeLegalEntityForm myEntiryDest = new RepresentativeLegalEntityForm();
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

    private RepresentativeNaturalPersonForm professional2NaturalPersonForm(ProfessionalPractitionerForm myEntityOrig) {
        RepresentativeNaturalPersonForm myEntiryDest = new RepresentativeNaturalPersonForm();
        BeanUtilsBean instance = BeanUtilsBean.getInstance();
        try {
            instance.copyProperties(myEntiryDest, myEntityOrig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        myEntiryDest.setName(myEntityOrig.getName());

        return myEntiryDest;
    }

}
