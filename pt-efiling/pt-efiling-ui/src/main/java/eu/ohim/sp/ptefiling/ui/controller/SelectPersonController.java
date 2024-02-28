package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SelectPersonController {

    @Autowired
    private PTFlowBean flowBean;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private SectionViewConfiguration sectionViewConfiguration;

    @RequestMapping(value = "getSelectablePersonsList", method = RequestMethod.GET)
    public ModelAndView getSelectablePersonsList(String sectionId) {
        AvailableSection availableSection = AvailableSection.fromValue(sectionId);
        List<PersonOptionForm> personOptions = new ArrayList<>();

        if (availableSection.equals(AvailableSection.APPLICANT)) {
            fillApplicantOptions(availableSection, personOptions);
        }

        ModelAndView mav = new ModelAndView("/person/person_select");
        mav.addObject("personOptionsList", personOptions);
        mav.addObject("sectionId", sectionId);
        return mav;
    }

    private void fillApplicantOptions(AvailableSection availableSection, List<PersonOptionForm> personOptions) {
        if (sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_APPLICANTS_FROM_OWNERS.getValue(), flowScopeDetails.getFlowModeId())) {
            fillOptionsFromOwners(personOptions);
        }
        if (sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR.getValue(), flowScopeDetails.getFlowModeId())) {
            fillApplicantOptionsFromProfile(personOptions);
        }
    }

    private void fillOptionsFromOwners(List<PersonOptionForm> selectableOptions) {
        if (flowBean == null) {
            throw new SPException("Null flow bean");
        }
        if (flowBean.getSpcPatents() != null && !flowBean.getSpcPatents().isEmpty()) {
            for (ESPatentDetailsForm esPatentDetailsForm : flowBean.getSpcPatents()) {
                if (esPatentDetailsForm.getApplicants() != null) {
                    addOwnersToOptions(esPatentDetailsForm.getApplicants(), selectableOptions);
                }
            }
        }
    }

    private void addOwnersToOptions(List<ApplicantForm> ownerList, List<PersonOptionForm> selectableOptions) {
        ownerList.stream()
                .filter(owner -> owner != null && owner.getImported())
                .map(owner -> {
                    try {
                        return personService.importApplicant(owner.getId(), flowScopeDetails.getFlowModeId());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(applicantForm -> applicantForm != null)
                .map(applicantForm -> new PersonOptionForm(
                        PersonOptionSourceField.FIELD_APPLICANTS_FROM_OWNERS,
                        applicantForm,
                        isPersonAlreadyAdded(ApplicantForm.class, applicantForm)
                ))
                .forEach(selectable -> {
                    if (!selectableOptions.contains(selectable)) {
                        selectableOptions.add(selectable);
                    }
                });
    }

    private void fillApplicantOptionsFromProfile(List<PersonOptionForm> selectableOptions) {
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List<Applicant> applicants = userPersonDetails.getApplicants();
        if (applicants != null) {
            applicants.stream()
                    .map(applicant -> {
                        ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);
                        applicantForm.setImported(true);
                        return applicantForm;
                    })
                    .map(applicantForm -> new PersonOptionForm(
                            PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR,
                            applicantForm,
                            isPersonAlreadyAdded(ApplicantForm.class, applicantForm)
                    ))
                    .forEach(selectable -> {
                        if (!selectableOptions.contains(selectable)) {
                            selectableOptions.add(selectable);
                        }
                    });
        }
    }

    @RequestMapping(value = "addPersonListSelection", method = RequestMethod.POST, consumes = "application/json")
    public ModelAndView addPersonListSelection(@RequestParam String sectionId, @RequestBody PersonOptionSelectedForm[] selectedPersonForms) {
        AvailableSection section = AvailableSection.fromValue(sectionId);
        if (section == AvailableSection.APPLICANT) {
            addSelectedOptionsAsApplicants(selectedPersonForms);
            return new ModelAndView("applicant/applicant_card_list");
        }
        throw new SPException("Bad section to add selected persons");
    }

    private void addSelectedOptionsAsApplicants(PersonOptionSelectedForm[] selectedPersonForms) {
        Arrays.asList(selectedPersonForms).stream().forEach(selected -> {
            ApplicantForm applicantForm = null;
            switch (selected.getField()) {
                case FIELD_APPLICANTS_FROM_OWNERS:
                    applicantForm = personService.importApplicant(selected.getId(), flowScopeDetails.getFlowModeId());
                    break;
                case FIELD_CURRENT_USER_INDICATOR:
                    applicantForm = personService.importApplicant(selected.getId(), flowScopeDetails.getFlowModeId());
                    applicantForm.setCurrentUserIndicator(true);
                    break;
            }
            addPersonToFlowBean(ApplicantForm.class, applicantForm);
        });
    }

    private void addPersonToFlowBean(Class<? extends PersonForm> cls, PersonForm personForm) {
        if (personForm != null) {
            if (!flowBean.existsObject(cls, personForm.getId())) {
                flowBean.addObject(personForm);
            } else {
                flowBean.replaceObject(personForm, personForm.getId());
            }
        }
    }

    private boolean isPersonAlreadyAdded(Class<? extends PersonForm> cls, PersonForm personForm) {
        if (personForm.getImported()) {
            return flowBean.existsObject(cls, personForm.getId());
        } else {
            return flowBean.existsObject(cls, personForm.getId()) && flowBean.getObject(cls, personForm.getId()).equals(personForm);
        }
    }
}
