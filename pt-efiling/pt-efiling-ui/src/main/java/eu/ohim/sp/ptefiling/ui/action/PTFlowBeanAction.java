package eu.ohim.sp.ptefiling.ui.action;

import eu.ohim.sp.common.ui.adapter.patent.InventorFactory;
import eu.ohim.sp.common.ui.form.patent.PatentApplicationKind;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBeanImpl;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTApplicationServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Raya
 * 15.04.2019
 */
@Service(value = "flowBeanAction")
public class PTFlowBeanAction {

    private static final Logger LOGGER = Logger.getLogger(PTFlowBeanAction.class);

    @Autowired
    private PTApplicationServiceInterface draftApplicationService;

    @Autowired
    private FlowScopeDetails flowScopeSession;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private InventorFactory inventorFactory;


    public UserPersonDetails addUserPersonDetails(PTFlowBean flowBean, String flowModeId) {
        try {
            UserPersonDetails userPersonDetails = personService.addUserPersonDetails(flowBean, flowModeId);
            List<Designer> designers = userPersonDetails.getDesigners();
            if(designers != null && designers.size() > 0 && designers.get(0) != null){
                InventorForm inventorForm = inventorFactory.convertFromDesigner(designers.get(0));
                personService.addPersonFormDetails(flowBean, inventorForm, flowModeId);
            }
            return userPersonDetails;
        } catch (Exception e) {
            LOGGER.warn("Error obtaining user person details", e);
            return null;
        }
    }

    public void clearInventors(PTFlowBean flowBean){
        flowBean.getCollection(InventorForm.class).clear();
    }

    public UserPersonDetails clearUserApplicantDetails(PTFlowBean flowBean, String flowModeId) {
        try {
            return personService.clearUserApplicantDetails(flowBean, flowModeId);
        } catch (Exception e) {
            LOGGER.warn("Error obtaining user person details", e);
            return null;
        }
    }

    public UserPersonDetails clearUserInventorDetails(PTFlowBean flowBean, String flowModeId) {
        try {
            return personService.clearUserInventorDetails(flowBean, flowModeId);
        } catch (Exception e) {
            LOGGER.warn("Error obtaining user person details", e);
            return null;
        }
    }

    public void keepOnlyIntlPRepresentatives(PTFlowBean flowBean){
        List<RepresentativeForm> intlPs = new ArrayList<>();
        flowBean.getCollection(RepresentativeForm.class).forEach(representativeForm -> {
            if(representativeForm instanceof IntlPRepresentativeForm){
                intlPs.add(representativeForm);
            }
        });
        flowBean.getCollection(RepresentativeForm.class).clear();
        flowBean.getCollection(RepresentativeForm.class).addAll(intlPs);
    }


    public boolean addInventorFromApplicant(PTFlowBean flowBean){
        boolean isThere = false;

        Map<String, String> inventorsFromApplicants = flowBean.getInventorsFromApplicants();
        NaturalPersonForm applicant;
        InventorForm inventorForm;

        // Remove imported inventors
        purgeImportedInventorsFromXml(flowBean);
        // To check if it's deleted some applicant
        checkApplicants(flowBean, inventorsFromApplicants, flowBean.getApplicants());

        for (ApplicantForm applicantForm : flowBean.getApplicants()) {
            if (applicantForm instanceof NaturalPersonForm) {
                applicant = (NaturalPersonForm) applicantForm;
                if (applicant.getDesignerIndicator()) {

                    if (inventorsFromApplicants.containsKey(applicant.getId())) {
                        String id = inventorsFromApplicants.get(applicant.getId());
                        inventorForm = flowBean.getObject(InventorForm.class, id);
                    } else {
                        inventorForm = new InventorForm();
                        if(applicant.getImported()) {
                            inventorForm.setImported(true);
                            inventorForm.setId(applicant.getId());
                        }
                    }
                    inventorForm.setNationality(applicant.getNationality());
                    inventorForm.setFirstName(applicant.getFirstName());
                    inventorForm.setMiddleName(applicant.getMiddleName());
                    inventorForm.setSurname(applicant.getSurname());
                    inventorForm.setAddress(applicant.getAddress());
                    inventorForm.setCorrespondenceAddresses(applicant.getCorrespondenceAddresses());
                    inventorForm.setEmail(applicant.getEmail());
                    inventorForm.setFixed(applicant.getFixed());
                    inventorForm.setFax(applicant.getFax());
                    inventorForm.setPhone(applicant.getPhone());
                    inventorForm.setWebsite(applicant.getWebsite());
                    inventorForm.setImportedFromApplicant(Boolean.TRUE);
                    inventorForm.setTaxIdNumber(applicant.getTaxIdNumber());
                    inventorForm.setImportedFromApplicant(true);

                    if (!inventorsFromApplicants.containsKey(applicant.getId())) {
                        if(!flowBean.existsObject(InventorForm.class, inventorForm.getId())) {
                            flowBean.addObject(inventorForm);
                        }
                        inventorsFromApplicants.put(applicant.getId(), inventorForm.getId());
                    }

                    isThere = true;
                }
            }
        }
        return isThere;
    }

    public void updateApplicantFromInventor(PTFlowBean flowBean) {
        for (InventorForm inventorForm : flowBean.getInventors()) {
            Map<String, String> inventorsFromApplicants = flowBean.getInventorsFromApplicants();
            for (Iterator<Map.Entry<String, String>> it = inventorsFromApplicants.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = it.next();
                if (inventorForm.getId().equals(entry.getValue())) {
                    NaturalPersonForm commandNaturalPersonForm = flowBean.getObject(NaturalPersonForm.class,
                        entry.getKey());
                    if(commandNaturalPersonForm != null && commandNaturalPersonForm.getDesignerIndicator()){
                        commandNaturalPersonForm.setFirstName(inventorForm.getFirstName());
                        commandNaturalPersonForm.setMiddleName(inventorForm.getMiddleName());
                        commandNaturalPersonForm.setSurname(inventorForm.getSurname());
                        commandNaturalPersonForm.setAddress(inventorForm.getAddress());
                        commandNaturalPersonForm.setCorrespondenceAddresses(inventorForm.getCorrespondenceAddresses());
                        commandNaturalPersonForm.setEmail(inventorForm.getEmail());
                        commandNaturalPersonForm.setFax(inventorForm.getFax());
                        commandNaturalPersonForm.setPhone(inventorForm.getPhone());
                        commandNaturalPersonForm.setFixed(inventorForm.getFixed());
                        commandNaturalPersonForm.setTaxIdNumber(inventorForm.getTaxIdNumber());
                        commandNaturalPersonForm.setWebsite(inventorForm.getWebsite());
                        break;
                    } else {
                        it.remove();
                        inventorForm.setImportedFromApplicant(false);
                    }
                }
            }
        }
    }

    public PTFlowBean loadApplication(String formId, String lid) {
        String temp = flowScopeSession.getLid();
        PTFlowBean ptFlowBean = new PTFlowBeanImpl();
        if(formId == null || lid == null) {
            return ptFlowBean;
        } else if(lid.equals(temp)) {
            return draftApplicationService.loadApplicationLocally(formId);
        }
        return ptFlowBean;
    }

    public void purgeImportedInventorsFromXml(PTFlowBean flowBean) {
        if (flowBean.getInventors() != null) {
            for (Iterator<InventorForm> it = flowBean.getInventors().listIterator(); it.hasNext();) {
                InventorForm f = it.next();
                if (f.isImportedFromXml() && f.isImportedFromApplicant()) {
                    it.remove();
                }
            }
        }
    }

    private void checkApplicants(PTFlowBean flowBean, Map<String, String> inventorsFromApplicants,
                                 List<ApplicantForm> applicants) {

        boolean found = false;

        for (Iterator<Map.Entry<String, String>> it = inventorsFromApplicants.entrySet().iterator(); it.hasNext();) {
            if (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (flowBean.getObject(InventorForm.class, entry.getValue()) != null) {
                    for (ApplicantForm applicantForm : applicants) {
                        if (applicantForm instanceof NaturalPersonForm) {
                            NaturalPersonForm applicant = (NaturalPersonForm) applicantForm;
                            if (entry.getKey().equals(applicant.getId())) {
                                found = true;
                                break;
                            }
                        }
                    }
                }
                if (!found) {
                    InventorForm inventorForm = flowBean.getObject(InventorForm.class, entry.getValue());
                    if (inventorForm != null) {
                        inventorForm.setImportedFromApplicant(false);
                    }
                    it.remove();
                }
            }

        }
    }

    public void checkAllApplicantsAreInventors(PTFlowBean flowBean){
        List<ApplicantForm> applicantForms = flowBean.getCollection(ApplicantForm.class);
        if(applicantForms != null && applicantForms.size()>0){
            Optional<Boolean> optAllInventors = applicantForms.stream().map(a -> {
                if(a instanceof NaturalPersonForm) {
                   return ((NaturalPersonForm) a).getDesignerIndicator();
                } else {
                    return false;
                }
            }).reduce((b1, b2) -> b1&&b2);
            if(optAllInventors.isPresent() && optAllInventors.get().equals(Boolean.TRUE)){
                flowBean.getMainForm().setSmallCompany(true);
            }
        }
    }


    public PTFlowBean changeApplicationKind(PTFlowBean flowBean){
        if(flowBean.getMainForm().getApplicationKind() != null && flowBean.getMainForm().getApplicationKind().equals(PatentApplicationKind.EP_TEMPORARY_PROTECTION)) {
            flowBean.getPatent().setPagesCount(null);
            flowBean.getPatent().setClaimsCount(null);
            flowBean.getPatent().setDrawingsCount(null);
            flowBean.getPatent().setPatentDrawings(new FileWrapper());
            flowBean.getPatent().setSequencesListing(new FileWrapper());
            flowBean.getPatent().setPatentDescriptions(new FileWrapper());
            flowBean.getMainForm().setEpoTransferChangeForm(false);
            flowBean.getMainForm().setEpoDecisionCopy(false);
            if(flowBean.getOtherAttachments() != null && flowBean.getOtherAttachments().getStoredFiles() != null && flowBean.getOtherAttachments().getStoredFiles().size()>0){
                flowBean.getOtherAttachments().setStoredFiles(
                    flowBean.getOtherAttachments().getStoredFiles().stream().filter(a -> (!a.getDocType().equals(AttachmentDocumentType.EPO_DECISION_COPY) &&
                    !a.getDocType().equals(AttachmentDocumentType.EPO_DECISION_TRANSLATION) && !a.getDocType().equals(AttachmentDocumentType.EPO_TRANSFER_CHANGE_FORM_COPY) &&
                    !a.getDocType().equals(AttachmentDocumentType.EPO_TRANSFER_CHANGE_FORM_TRANSLATION))).collect(Collectors.toList())
                );
            }
        }
        flowBean.getPatent().setDrawingNumber(null);
        flowBean.getPatent().setIndependentClaimsCount(null);
        return flowBean;
    }

    public PTFlowBean setInitialEPValues(PTFlowBean flowBean){
        flowBean.getMainForm().setApplicationKind(PatentApplicationKind.EP_VALIDATION_REQUEST);
        return flowBean;
    }
}
