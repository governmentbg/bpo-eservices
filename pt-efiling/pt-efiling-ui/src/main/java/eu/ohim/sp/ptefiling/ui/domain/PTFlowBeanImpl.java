package eu.ohim.sp.ptefiling.ui.domain;

import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTFlowBeanImpl extends CommonSpFlowBean implements PTFlowBean {

    private static transient final Logger LOGGER = Logger.getLogger(PTFlowBeanImpl.class);

    private PTMainForm mainForm;

    private PatentForm patent;

    private List<ESPatentDetailsForm> spcPatents;

    private TechnicalQuestionnaireForm technicalQuestionnaireForm;

    private List<PTPriorityForm> priorities;

    private List<ExhPriorityForm> exhibitions;

    private List<PTParallelApplicationForm> parallelApplications;

    private List<PCTForm> pcts;

    private List<PTTransformationForm> transformations;

    private List<PTDivisionalApplicationForm> divisionalApplications;

    private List<InventorForm> inventors;

    private Map<String, String> inventorsFromApplicants;

    private FileWrapper otherAttachments;

    private String comment;

    private Boolean trueDocumentsIndicator;

    private Boolean certificateRequestedIndicator;

    private List<String> earlierAppRepresentatives;

    private boolean earlierAppImported;

    private List<String> formWarnings;

    private boolean applicantsImportedFromTemplate;

    public PTFlowBeanImpl(){
        super.clear();

        id = null;
        mainForm = new PTMainForm();
        patent = new PatentForm();
        technicalQuestionnaireForm = new TechnicalQuestionnaireForm();
        otherAttachments = new FileWrapper();
        earlierAppRepresentatives = new ArrayList<>();
        earlierAppImported = false;
        formWarnings = new ArrayList<>();
        applicantsImportedFromTemplate = false;

        clearPriorities();
        clearExhibitions();
        clearParallelApplications();
        clearPcts();
        clearTransformations();
        clearDivisionalApplications();
        clearInventors();
        clearInventorsFromApplicants();
        clearPatentViews();
        clearApplicationCA();
        clearSpcPatents();
    }

    @Override
    public void clearApplicationCA() {
        map.put(ApplicationCAForm.class, mainForm.getCorrespondanceAddresses());
    }

    @Override
    public void clearParallelApplications(){
        parallelApplications = new ArrayList<>();
        map.put(PTNationalParallelApplicationForm.class, parallelApplications);
        map.put(PTInternationalParallelApplicationForm.class, parallelApplications);
        map.put(PTEuropeanParallelApplicationForm.class, parallelApplications);
        map.put(PTParallelApplicationForm.class, parallelApplications);
    }

    @Override
    public void clearPcts(){
        pcts = new ArrayList<>();
        map.put(PCTForm.class, pcts);
    }


    @Override
    public void clearExhibitions(){
        exhibitions = new ArrayList<>();
        map.put(ExhPriorityForm.class, exhibitions);
    }

    @Override
    public void clearDivisionalApplications() {
        divisionalApplications = new ArrayList<PTDivisionalApplicationForm>();
        map.put(PTDivisionalApplicationForm.class, divisionalApplications);
    }

    @Override
    public void clearPriorities() {
        priorities = new ArrayList<PTPriorityForm>();
        map.put(PTPriorityForm.class, priorities);
    }

    @Override
    public void clearTransformations() {
        transformations = new ArrayList<PTTransformationForm>();
        map.put(PTNationalTransformationForm.class, transformations);
        map.put(PTConversionForm.class, transformations);
        map.put(PTInternationalTransformationForm.class, transformations);
        map.put(PTTransformationForm.class, transformations);
    }


    @Override
    public void clearInventorsFromApplicants() {
        if (this.inventorsFromApplicants == null) {
            this.inventorsFromApplicants = new HashMap<String, String>();
        }
    }

    @Override
    public void clearPatentViews(){
        patent.setPatentViews(new ArrayList<>());
        map.put(PatentViewForm.class, patent.getPatentViews());
    }

    @Override
    public void clearInventors() {
        inventors = new ArrayList<>();
        map.put(InventorForm.class, inventors);
    }

    @Override
    public void clearSpcPatents() {
        spcPatents = new ArrayList<>();
        map.put(ESPatentDetailsForm.class, spcPatents);
    }

    @Override
    public List<ApplicationCAForm> getCorrespondanceAddresses() {
        return mainForm.getCorrespondanceAddresses();
    }

    @Override
    public PTMainForm getMainForm() {
        return mainForm;
    }

    public void setMainForm(PTMainForm mainForm) {
        this.mainForm = mainForm;
    }

    public PatentForm getPatent() {
        return patent;
    }

    public void setPatent(PatentForm patent) {
        this.patent = patent;
    }

    @Override
    public List<ESPatentDetailsForm> getSpcPatents() {
        return spcPatents;
    }

    public void setSpcPatents(List<ESPatentDetailsForm> spcPatents) {
        this.spcPatents = spcPatents;
    }

    public List<PTPriorityForm> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<PTPriorityForm> priorities) {
        this.priorities = priorities;
    }

    public List<ExhPriorityForm> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<ExhPriorityForm> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<PTParallelApplicationForm> getParallelApplications() {
        return parallelApplications;
    }

    public void setParallelApplications(List<PTParallelApplicationForm> parallelApplications) {
        this.parallelApplications = parallelApplications;
    }

    public List<PTTransformationForm> getTransformations() {
        return transformations;
    }

    public void setTransformations(List<PTTransformationForm> transformations) {
        this.transformations = transformations;
    }

    public List<PTDivisionalApplicationForm> getDivisionalApplications() {
        return divisionalApplications;
    }

    public void setDivisionalApplications(List<PTDivisionalApplicationForm> divisionalApplications) {
        this.divisionalApplications = divisionalApplications;
    }

    public List<InventorForm> getInventors() {
        return inventors;
    }

    public void setInventors(List<InventorForm> inventors) {
        this.inventors = inventors;
    }

    public Map<String, String> getInventorsFromApplicants() {
        return inventorsFromApplicants;
    }

    public void setInventorsFromApplicants(Map<String, String> inventorsFromApplicants) {
        this.inventorsFromApplicants = inventorsFromApplicants;
    }

    public FileWrapper getOtherAttachments() {
        return otherAttachments;
    }

    public void setOtherAttachments(FileWrapper otherAttachments) {
        this.otherAttachments = otherAttachments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public Boolean getTrueDocumentsIndicator() {
        return trueDocumentsIndicator;
    }

    @Override
    public void setTrueDocumentsIndicator(Boolean trueDocumentsIndicator) {
        this.trueDocumentsIndicator = trueDocumentsIndicator;
    }

    public Boolean getCertificateRequestedIndicator() {
        return certificateRequestedIndicator;
    }

    public void setCertificateRequestedIndicator(Boolean certificateRequestedIndicator) {
        this.certificateRequestedIndicator = certificateRequestedIndicator;
    }

    @Override
    public List<InventorForm> getUserDataInventors() {
        ArrayList<InventorForm> userData = new ArrayList<InventorForm>();
        for (InventorForm form : inventors) {
            if (form.getCurrentUserIndicator()) {
                userData.add(form);
            }
        }
        return userData;
    }

    @Override
    public List<InventorForm> getAddedInventors() {
        ArrayList<InventorForm> added = new ArrayList<InventorForm>();
        for (InventorForm form : inventors) {
            if (!form.getCurrentUserIndicator()) {
                added.add(form);
            }
        }
        return added;
    }

    @Override
    public List<PersonForm> getPersons() {
        List<PersonForm> personsForm = new ArrayList<PersonForm>();
        for (PersonForm form : this.getRepresentatives()) {
            personsForm.add(form);
        }
        for (PersonForm form : this.getApplicants()) {
            personsForm.add(form);
        }
        return personsForm;
    }

    public List<PCTForm> getPcts() {
        return pcts;
    }

    public void setPcts(List<PCTForm> pcts) {
        this.pcts = pcts;
    }

    public List<String> getEarlierAppRepresentatives() {
        return earlierAppRepresentatives;
    }

    public void setEarlierAppRepresentatives(List<String> earlierAppRepresentatives) {
        this.earlierAppRepresentatives = earlierAppRepresentatives;
    }

    public boolean isEarlierAppImported() {
        return earlierAppImported;
    }

    public void setEarlierAppImported(boolean earlierAppImported) {
        this.earlierAppImported = earlierAppImported;
    }

    public List<String> getFormWarnings() {
        return formWarnings;
    }

    public void setFormWarnings(List<String> formWarnings) {
        this.formWarnings = formWarnings;
    }

    public boolean getApplicantsImportedFromTemplate() {
        return applicantsImportedFromTemplate;
    }

    public void setApplicantsImportedFromTemplate(boolean applicantsImportedFromTemplate) {
        this.applicantsImportedFromTemplate = applicantsImportedFromTemplate;
    }

    @Override
    public boolean getPatentTemplateImported() {
        return patent != null && patent.getImported();
    }

    public TechnicalQuestionnaireForm getTechnicalQuestionnaireForm() {
        return technicalQuestionnaireForm;
    }

    public void setTechnicalQuestionnaireForm(TechnicalQuestionnaireForm technicalQuestionnaireForm) {
        this.technicalQuestionnaireForm = technicalQuestionnaireForm;
    }
}
