package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.Representative;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Raya
 * 11.12.2019
 */
@Component
public class ESPatentFactory implements UIFactory<Patent, ESPatentDetailsForm> {

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    private RepresentativeFactory representativeFactory;

    @Autowired
    private InventorFactory inventorFactory;

    @Override
    public Patent convertTo(ESPatentDetailsForm form) {
        Patent core = new Patent();
        if(form == null){
            return core;
        }

        core.setPatentTitle(form.getPatentTitle());
        core.setPatentTitleSecondLang(form.getPatentTitleSecondLang());
        core.setPatentAbstract(form.getPatentAbstract());
        core.setPatentAbstractSecondLang(form.getPatentAbstractSecondLang());

        core.setApplicationNumber(form.getApplicationNumber());
        core.setRegistrationNumber(form.getRegistrationNumber());
        core.setApplicationDate(form.getApplicationDate());
        core.setEntitlementDate(form.getEntitlementDate());
        core.setRegistrationDate(form.getRegistrationDate());
        core.setExpirationDate(form.getExpirationDate());

        core.setCurrentStatusCode(form.getPatentCurrentStatus());
        core.setGroupStatusCode(form.getPatentGroupStatus());
        core.setCurrentStatusDate(form.getPatentCurrentStatusDate());
        core.setUnpublished(form.getUnpublished());

        if(form.getApplicants() != null){
            List<Applicant> coreApplicants = new ArrayList<Applicant>();
            for(ApplicantForm applicantForm : form.getApplicants()){
                if(applicantForm.getType() == null){
                    applicantForm.setType(ApplicantKindForm.NATURAL_PERSON);
                }
                Applicant applicant = applicantFactory.convertTo(applicantForm);
                if(applicant != null) {
                    applicant.setDomicileLocality(applicantForm.getDomicile());
                }
                coreApplicants.add(applicant);
            }
            core.setApplicants(coreApplicants);
        }

        if(form.getInventors() != null){
            List<Inventor> coreInventors = new ArrayList<Inventor>();
            for(InventorForm inventorForm : form.getInventors()){
                Inventor inventor = inventorFactory.convertTo(inventorForm);
                coreInventors.add(inventor);
            }
            core.setInventors(coreInventors);
        }

        if (!CollectionUtils.isEmpty(form.getIpcClasses())){
            core.setIpcClasses(form.getIpcClasses());
        }

        return core;
    }

    @Override
    public ESPatentDetailsForm convertFrom(Patent core) {
        ESPatentDetailsForm form = new ESPatentDetailsForm();
        if(core == null){
            return form;
        }

        form.setPatentTitle(core.getPatentTitle());
        form.setPatentTitleSecondLang(core.getPatentTitleSecondLang());
        form.setPatentAbstract(core.getPatentAbstract());
        form.setPatentAbstractSecondLang(core.getPatentAbstractSecondLang());

        form.setApplicationNumber(core.getApplicationNumber());
        form.setRegistrationNumber(core.getRegistrationNumber());
        form.setApplicationDate(core.getApplicationDate());
        form.setEntitlementDate(core.getEntitlementDate());
        form.setRegistrationDate(core.getRegistrationDate());
        form.setExpirationDate(core.getExpirationDate());

        form.setPatentCurrentStatus(core.getCurrentStatusCode());
        form.setPatentGroupStatus(core.getGroupStatusCode());
        form.setPatentCurrentStatusDate(core.getCurrentStatusDate());
        form.setUnpublished(core.getUnpublished());

        List<ApplicantForm> uiApplicants = new ArrayList<ApplicantForm>();
        List<Applicant> coreApplicants = core.getApplicants();

        if(!CollectionUtils.isEmpty(coreApplicants)){
            for(Applicant applicant : coreApplicants){
                if(applicant.getKind() == PersonKind.LEGAL_ENTITY && applicant.getName() != null &&applicant.getName().getOrganizationName() != null &&
                    applicant.getName().getOrganizationName().matches("\\d")){
                    applicant.getName().setOrganizationName(applicant.getName().getLastName());
                }
                ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);
                if (applicantForm!=null){
                    applicantForm.setInternalApplicantId(applicantForm.getId());
                    if(applicantForm.getAddress() != null){
                        StringBuilder infoString = new StringBuilder();
                        infoString.append(applicantForm.getAddress().getStreet() == null? "" : applicantForm.getAddress().getStreet().trim());
                        infoString.append(applicantForm.getAddress().getHouseNumber() == null? "" : ", "+applicantForm.getAddress().getHouseNumber().trim());
                        infoString.append(applicantForm.getAddress().getPostalCode() == null? "" : ".\n"+applicantForm.getAddress().getPostalCode().trim());
                        infoString.append(applicantForm.getAddress().getCity() == null? "" : ".\n"+applicantForm.getAddress().getCity().trim());
                        infoString.append(applicantForm.getAddress().getCountry() == null? "" : " - "+applicantForm.getAddress().getCountry().trim());
                        if(infoString.length() > 0) {
                            infoString.append(".");
                        } else {
                            infoString.append(applicant.getDomicileLocality() == null? "" : applicant.getDomicileLocality());
                        }

                        applicantForm.setDomicile(infoString.toString());
                    }
                    uiApplicants.add(applicantForm);
                }
            }
        }

        form.setApplicants(uiApplicants);

        if(!CollectionUtils.isEmpty(core.getRepresentatives())) {
            form.setRepresentatives(new ArrayList<RepresentativeForm>());
            for (Representative representative : core.getRepresentatives()) {
                if(representative.getRepresentativeKind() == null) {
                    if (representative.getIdentifiers() != null && representative.getIdentifiers().size() > 0
                            && representative.getIdentifiers().get(0) != null && representative.getIdentifiers().get(0).getValue() != null) {
                        RepresentativeNaturalPersonForm representativeForm = new RepresentativeNaturalPersonForm();
                        representativeForm.setFirstName(representative.getName().getFirstName());
                        representativeForm.setMiddleName(representative.getName().getMiddleName());
                        representativeForm.setSurname(representative.getName().getLastName() + (StringUtils.isEmpty(representative.getName().getOrganizationName()) ? "" : (" " + representative.getName().getOrganizationName())));
                        representativeForm.setImported(true);
                        representativeForm.setId(representative.getIdentifiers().get(0).getValue());
                        form.getRepresentatives().add(representativeForm);
                    }
                } else {
                    RepresentativeForm representativeForm = representativeFactory.convertFrom(representative);
                    form.getRepresentatives().add(representativeForm);
                }
            }
        }

        if(!CollectionUtils.isEmpty(core.getInventors())) {
            form.setInventors(new ArrayList<InventorForm>());
            for (Inventor inventor : core.getInventors()) {
                InventorForm inventorForm = inventorFactory.convertFrom(inventor);
                form.getInventors().add(inventorForm);
            }
        }

        if (!CollectionUtils.isEmpty(core.getIpcClasses())){
            form.setIpcClasses(core.getIpcClasses());
        }

        return form;
    }
}
