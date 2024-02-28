package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.adapter.patent.InventorFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.FileAttachmentStatus;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raya
 * 06.11.2020
 */
@Controller
public class SelectPersonController {

    @Autowired
    private ESFlowBean flowBean;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    private AssigneeFactory assigneeFactory;

    @Autowired
    private HolderFactory holderFactory;

    @Autowired
    private RepresentativeFactory representativeFactory;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private SectionViewConfiguration sectionViewConfiguration;

    @RequestMapping(value = "getSelectablePersonsList", method = RequestMethod.GET)
    public ModelAndView getSelectablePersonsList(String sectionId){
        AvailableSection availableSection = AvailableSection.fromValue(sectionId);
        List<PersonOptionForm> personOptions = new ArrayList<>();

        if(availableSection.equals(AvailableSection.APPLICANT)) {
            fillApplicantOptions(availableSection, personOptions);
        } else if(availableSection.equals(AvailableSection.ASSIGNEE)
            || availableSection.equals(AvailableSection.ASSIGNEE_SECURITY)
            || availableSection.equals(AvailableSection.LICENSEE)
            || availableSection.equals(AvailableSection.REMCREDITOR)
            || availableSection.equals(AvailableSection.INVENTOR)){
            fillAssigneeOptions(availableSection, personOptions);
        } else if(availableSection.equals(AvailableSection.REPRESENTATIVE)){
            fillRepresentativeOptions(availableSection, personOptions);
        } else if(availableSection.equals(AvailableSection.HOLDER)){
            fillHolderOptions(availableSection, personOptions);
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
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_APPLICANTS_FROM_HOLDERS.getValue(), flowScopeDetails.getFlowModeId())){
            fillOptionsFromHolders(personOptions);
        }
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_APPLICANTS_FROM_ASSIGNEES.getValue(), flowScopeDetails.getFlowModeId())){
            fillOptionsFromAssignees(personOptions);
        }
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR.getValue(), flowScopeDetails.getFlowModeId())) {
            fillApplicantOptionsFromProfile(personOptions);
        }
    }

    private void fillAssigneeOptions(AvailableSection availableSection, List<PersonOptionForm> personOptions) {
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR.getValue(), flowScopeDetails.getFlowModeId())){
            fillAssigneeOptionsFromProfile(personOptions);
        }
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_ASSIGNEES_FROM_INVENTORS.getValue(), flowScopeDetails.getFlowModeId())){
            fillInventorOptionsFromPatent(personOptions);
        }
    }

    private void fillRepresentativeOptions(AvailableSection availableSection, List<PersonOptionForm> personOptions) {
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR.getValue(), flowScopeDetails.getFlowModeId())){
            fillRepresentativeOptionsFromProfile(personOptions);
        }
    }

    private void fillHolderOptions(AvailableSection availableSection, List<PersonOptionForm> personOptions) {
        if(sectionViewConfiguration.getRender(availableSection, PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR.getValue(), flowScopeDetails.getFlowModeId())){
            fillHolderOptionsFromProfile(personOptions);
        }
    }

    private void fillOptionsFromOwners(List<PersonOptionForm> selectableOptions){
        if (flowBean == null) {
            throw new SPException("Null flow bean");
        }
        if (flowBean.getTmsList() != null && !flowBean.getTmsList().isEmpty()) {
            for (TMDetailsForm tmDetailsForm : flowBean.getTmsList()) {
                if (tmDetailsForm.getApplicants() != null) {
                    addOwnersToOptions(tmDetailsForm.getApplicants(), selectableOptions);
                }
            }
        } else if (flowBean.getDssList() != null && !flowBean.getDssList().isEmpty()) {
            for (ESDesignDetailsForm esDesignDetailsForm : flowBean.getDssList()) {
                if (esDesignDetailsForm.geteSDesignApplicationData() != null && esDesignDetailsForm.geteSDesignApplicationData().getHolders() != null) {
                    addOwnersToOptions(esDesignDetailsForm.geteSDesignApplicationData().getHolders(), selectableOptions);
                }
            }
        } else if (flowBean.getPatentsList() != null && !flowBean.getPatentsList().isEmpty()){
            for (ESPatentDetailsForm esPatentDetailsForm : flowBean.getPatentsList()) {
                if (esPatentDetailsForm.getApplicants() != null) {
                    addOwnersToOptions(esPatentDetailsForm.getApplicants(), selectableOptions);
                }
            }
        }
    }

    private void addOwnersToOptions(List<ApplicantForm> ownerList, List<PersonOptionForm> selectableOptions){
        ownerList.stream()
            .filter(owner -> owner != null && owner.getImported())
            .map(owner -> {
                try{
                    if(flowBean.getUnpublishedAppImported()){
                        return findApplicantInOwners(owner.getId());
                    } else {
                        return personService.importApplicant(owner.getId(), flowScopeDetails.getFlowModeId());
                    }
                } catch (Exception e){
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
                if(!selectableOptions.contains(selectable)){
                    selectableOptions.add(selectable);
                }
            });
    }

    private void fillOptionsFromHolders(List<PersonOptionForm> selectableOptions){
        if(flowBean.getHolders() != null) {
            flowBean.getHolders().stream()
                .map(holder -> applicantFactory.convertFromHolderForm(holder))
                .filter(applicantForm -> applicantForm != null)
                .map(applicantForm -> new PersonOptionForm(
                    PersonOptionSourceField.FIELD_APPLICANTS_FROM_HOLDERS,
                    applicantForm,
                    isPersonAlreadyAdded(ApplicantForm.class, applicantForm)
                ))
                .forEach(selectable -> {
                    if(!selectableOptions.contains(selectable)){
                        selectableOptions.add(selectable);
                    }
                });
        }
    }

    private void fillOptionsFromAssignees(List<PersonOptionForm> selectableOptions){
        if(flowBean.getAssignees() != null) {
            flowBean.getAssignees().stream()
                .map(assignee -> applicantFactory.convertFromAssigneeForm(assignee))
                .filter(applicantForm -> applicantForm != null)
                .map(applicantForm -> new PersonOptionForm(
                    PersonOptionSourceField.FIELD_APPLICANTS_FROM_ASSIGNEES,
                    applicantForm,
                    isPersonAlreadyAdded(ApplicantForm.class, applicantForm)
                ))
                .forEach(selectable -> {
                    if(!selectableOptions.contains(selectable)){
                        selectableOptions.add(selectable);
                    }
                });
        }
    }

    private void fillApplicantOptionsFromProfile(List<PersonOptionForm> selectableOptions){
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Applicant> applicants = userPersonDetails.getApplicants();
        if(applicants != null){
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
                    if(!selectableOptions.contains(selectable)){
                        selectableOptions.add(selectable);
                    }
                });
        }
    }

    private void fillAssigneeOptionsFromProfile(List<PersonOptionForm> selectableOptions){
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Applicant> applicants = userPersonDetails.getApplicants();
        if(applicants != null){
            applicants.stream()
                .map(applicant -> {
                    ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);
                    AssigneeForm assigneeForm = assigneeFactory.convertFromApplicantForm(applicantForm);
                    assigneeForm.setImported(true);
                    return assigneeForm;
                })
                .map(assigneeForm -> new PersonOptionForm(
                    PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR,
                    assigneeForm,
                    isPersonAlreadyAdded(AssigneeForm.class, assigneeForm)
                ))
                .forEach(selectable -> {
                    if(!selectableOptions.contains(selectable)){
                        selectableOptions.add(selectable);
                    }
                });
        }
    }

    private void fillInventorOptionsFromPatent(List<PersonOptionForm> selectableOptions){
        if (!CollectionUtils.isEmpty(flowBean.getPatentsList())){
            List<InventorForm> inventors = flowBean.getPatentsList().get(0).getInventors();
            if(inventors != null){
                inventors.stream()
                        .map(inventorForm -> {
                            AssigneeForm assigneeForm = assigneeFactory.convertFromInventorForm(inventorForm);
                            assigneeForm.setImported(true);
                            return assigneeForm;
                        })
                        .map(assigneeForm -> new PersonOptionForm(
                                PersonOptionSourceField.FIELD_ASSIGNEES_FROM_INVENTORS,
                                assigneeForm,
                                isPersonAlreadyAdded(AssigneeForm.class, assigneeForm)
                        ))
                        .forEach(selectable -> {
                            if(!selectableOptions.contains(selectable)){
                                selectableOptions.add(selectable);
                            }
                        });
            }
        }
    }

    private void fillRepresentativeOptionsFromProfile(List<PersonOptionForm> selectableOptions){
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Representative> representatives = userPersonDetails.getRepresentatives();
        if(representatives != null){
            representatives.stream()
                .map(representative -> {
                    RepresentativeForm representativeForm = representativeFactory.convertFrom(representative);
                    representativeForm.setImported(true);
                    return representativeForm;
                })
                .map(representativeForm -> new PersonOptionForm(
                    PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR,
                    representativeForm,
                    isPersonAlreadyAdded(RepresentativeForm.class, representativeForm)
                ))
                .forEach(selectable -> {
                    if(!selectableOptions.contains(selectable)){
                        selectableOptions.add(selectable);
                    }
                });
        }
    }

    private void fillHolderOptionsFromProfile(List<PersonOptionForm> selectableOptions){
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Applicant> applicants = userPersonDetails.getApplicants();
        if(applicants != null){
            applicants.stream()
                .map(applicant -> {
                    ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);
                    HolderForm holderForm = holderFactory.convertFromApplicantForm(applicantForm);
                    holderForm.setImported(true);
                    return holderForm;
                })
                .map(holderForm -> new PersonOptionForm(
                    PersonOptionSourceField.FIELD_CURRENT_USER_INDICATOR,
                    holderForm,
                    isPersonAlreadyAdded(HolderForm.class, holderForm)
                ))
                .forEach(selectable -> {
                    if(!selectableOptions.contains(selectable)){
                        selectableOptions.add(selectable);
                    }
                });
        }
    }

    @RequestMapping(value = "addPersonListSelection", method = RequestMethod.POST, consumes = "application/json")
    public ModelAndView addPersonListSelection(@RequestParam String sectionId, @RequestBody PersonOptionSelectedForm[] selectedPersonForms) {
        AvailableSection section = AvailableSection.fromValue(sectionId);
        switch(section){
            case APPLICANT:
                addSelectedOptionsAsApplicants(selectedPersonForms);
                return new ModelAndView("applicant/applicant_card_list");
            case ASSIGNEE_SECURITY: case REMCREDITOR: case ASSIGNEE: case LICENSEE: case INVENTOR:
                addSelectedOptionsAsAssignees(selectedPersonForms);
                return new ModelAndView("assignee/assignee_card_list");
            case REPRESENTATIVE:
                addSelectedOptionsAsRepresentatives(selectedPersonForms);
                return new ModelAndView("representative/representative_card_list");
            case HOLDER:
                addSelectedOptionsAsHolders(selectedPersonForms);
                return new ModelAndView("holder/holder_card_list");
            default:
                throw new SPException("Bad section to add selected persons");
        }
    }

    private void addSelectedOptionsAsApplicants(PersonOptionSelectedForm[] selectedPersonForms) {
        Arrays.asList(selectedPersonForms).stream().forEach(selected -> {
            ApplicantForm applicantForm = null;
            switch (selected.getField()){
                case FIELD_APPLICANTS_FROM_OWNERS:
                    if(flowBean.getUnpublishedAppImported()){
                        try {
                            ApplicantForm found = findApplicantInOwners(selected.getId());
                            applicantForm = found != null ? found.clone() : null;
                        } catch (CloneNotSupportedException e) {
                            throw new SPException(e);
                        }
                    } else {
                        applicantForm = personService.importApplicant(selected.getId(), flowScopeDetails.getFlowModeId());
                    }
                    break;
                case FIELD_CURRENT_USER_INDICATOR:
                    applicantForm = personService.importApplicant(selected.getId(), flowScopeDetails.getFlowModeId());
                    applicantForm.setCurrentUserIndicator(true);
                    break;
                case FIELD_APPLICANTS_FROM_HOLDERS:
                    applicantForm = applicantFactory.convertFromHolderForm(flowBean.getObject(HolderForm.class, selected.getId()));
                    break;
                case FIELD_APPLICANTS_FROM_ASSIGNEES:
                    applicantForm = applicantFactory.convertFromAssigneeForm(flowBean.getObject(AssigneeForm.class, selected.getId()));
                    break;
            }
            addPersonToFlowBean(ApplicantForm.class, applicantForm);
        });
    }

    private void addSelectedOptionsAsAssignees(PersonOptionSelectedForm[] selectedPersonForms) {
        Arrays.asList(selectedPersonForms).stream().forEach(selected -> {
            switch (selected.getField()){
                case FIELD_CURRENT_USER_INDICATOR:
                    ApplicantForm imported = personService.importApplicant(selected.getId(), flowScopeDetails.getFlowModeId());
                    AssigneeForm assigneeForm = assigneeFactory.convertFromApplicantForm(imported);
                    addPersonToFlowBean(AssigneeForm.class, assigneeForm);
                    break;
                case FIELD_ASSIGNEES_FROM_INVENTORS:
                    InventorForm importedInv;
                    if (flowBean.getUnpublishedAppImported()) {
                        try {
                            InventorForm found = findAssigneeInInventors(selected.getId());
                            importedInv = found != null ? found.clone() : null;
                        } catch (Exception e) {
                            throw new SPException(e);
                        }
                    } else {
                        importedInv = personService.importInventor(selected.getId(), flowScopeDetails.getFlowModeId());
                    }
                    AssigneeForm assigneeFormInv = assigneeFactory.convertFromInventorForm(importedInv);
                    addPersonToFlowBean(AssigneeForm.class, assigneeFormInv);
                    break;
            }
        });
    }

    private void addSelectedOptionsAsRepresentatives(PersonOptionSelectedForm[] selectedPersonForms) {
        Arrays.asList(selectedPersonForms).stream().forEach(selected -> {
            switch (selected.getField()) {
                case FIELD_CURRENT_USER_INDICATOR:
                    RepresentativeForm representativeForm = personService.importRepresentative(selected.getId(), flowScopeDetails.getFlowModeId());
                    boolean providedVisible = false;
                    if(representativeForm instanceof RepresentativeNaturalPersonForm) {
                        providedVisible = sectionViewConfiguration.getRender(AvailableSection.REPRESENTATIVE_NATURALPERSON, "representativeAttachment.provided", flowScopeDetails.getFlowModeId());
                    } else if(representativeForm instanceof RepresentativeAssociationForm){
                        providedVisible = sectionViewConfiguration.getRender(AvailableSection.REPRESENTATIVE_FORM_ASSOCIATION, "representativeAttachment.provided", flowScopeDetails.getFlowModeId());
                    }
                    if (providedVisible) {
                        representativeForm.getRepresentativeAttachment().setAttachmentStatus(FileAttachmentStatus.PROVIDED);
                    }
                    addPersonToFlowBean(RepresentativeForm.class, representativeForm);
            }
        });
    }

    private void addSelectedOptionsAsHolders(PersonOptionSelectedForm[] selectedPersonForms) {
        Arrays.asList(selectedPersonForms).stream().forEach(selected -> {
            switch (selected.getField()){
                case FIELD_CURRENT_USER_INDICATOR:
                    ApplicantForm imported = personService.importApplicant(selected.getId(), flowScopeDetails.getFlowModeId());
                    HolderForm holderForm = holderFactory.convertFromApplicantForm(imported);
                    addPersonToFlowBean(HolderForm.class, holderForm);
                    break;
            }
        });
    }

    private void addPersonToFlowBean(Class<? extends PersonForm> cls, PersonForm personForm) {
        if(personForm != null) {
            if(!flowBean.existsObject(cls, personForm.getId())) {
                flowBean.addObject(personForm);
            } else {
                flowBean.replaceObject(personForm, personForm.getId());
            }
        }
    }

    private boolean isPersonAlreadyAdded(Class<? extends PersonForm> cls, PersonForm personForm) {
        if(personForm.getImported()){
            return flowBean.existsObject(cls, personForm.getId());
        } else {
            return flowBean.existsObject(cls, personForm.getId()) && flowBean.getObject(cls, personForm.getId()).equals(personForm);
        }
    }

    private ApplicantForm findApplicantInOwners(String id){
        if (flowBean == null) {
            throw new SPException("Null flow bean");
        }
        List<ApplicantForm> owners = null;
        if (flowBean.getTmsList() != null && !flowBean.getTmsList().isEmpty()) {
            owners = flowBean.getTmsList().get(0).getApplicants();
        } else if (flowBean.getDssList() != null && !flowBean.getDssList().isEmpty()) {
            ESDesignDetailsForm esDesignDetailsForm = flowBean.getDssList().get(0);
            if (esDesignDetailsForm.geteSDesignApplicationData() != null && esDesignDetailsForm.geteSDesignApplicationData().getHolders() != null) {
                owners = esDesignDetailsForm.geteSDesignApplicationData().getHolders();
            }
        } else if (flowBean.getPatentsList() != null && !flowBean.getPatentsList().isEmpty()){
            owners = flowBean.getPatentsList().get(0).getApplicants();
        }
        if(owners != null && owners.size()>0){
            ApplicantForm found = owners.stream().filter(own -> own.getId().equals(id)).findFirst().orElse(null);
            return found;
        }
        return null;
    }

    private InventorForm findAssigneeInInventors(String id){
        if (flowBean == null) {
            throw new SPException("Null flow bean");
        }
        List<InventorForm> assignees = null;
        if (flowBean.getPatentsList() != null && !flowBean.getPatentsList().isEmpty()){
            assignees = flowBean.getPatentsList().get(0).getInventors();
        }
        if(assignees != null && assignees.size()>0){
            InventorForm found = assignees.stream().filter(own -> own.getId().equals(id)).findFirst().orElse(null);
            return found;
        }
        return null;
    }
}
