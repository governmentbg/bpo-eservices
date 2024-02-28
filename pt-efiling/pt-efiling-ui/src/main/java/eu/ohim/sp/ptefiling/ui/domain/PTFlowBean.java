package eu.ohim.sp.ptefiling.ui.domain;

import eu.ohim.sp.common.ui.flow.section.*;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Raya
 * 09.04.2019
 */
public interface PTFlowBean extends FeesFlowBean, FileUploadFlowBean,
    LanguagesFlowBean, PaymentFlowBean, PersonFlowBean, SignaturesFlowBean, PatentFlowBean{

    void clearApplicationCA();
    void clearParallelApplications();
    void clearPcts();
    void clearExhibitions();
    void clearDivisionalApplications();
    void clearPriorities() ;
    void clearTransformations();
    void clearInventorsFromApplicants();
    void clearPatentViews();
    void clearInventors();
    void clearSpcPatents();

    List<InventorForm> getInventors();

    void setInventors(List<InventorForm> inventors);

    Map<String, String> getInventorsFromApplicants();

    void setInventorsFromApplicants(Map<String, String> inventorsFromApplicants);

    List<InventorForm> getUserDataInventors();

    List<InventorForm> getAddedInventors();

    List<PersonForm> getPersons();

    @Override
    PTMainForm getMainForm();

    void setMainForm(PTMainForm mainForm);


    List<PTPriorityForm> getPriorities();

    void setPriorities(List<PTPriorityForm> priorities);

    List<PTTransformationForm> getTransformations();

    void setTransformations(List<PTTransformationForm> transformations);

    List<PTDivisionalApplicationForm> getDivisionalApplications();

    void setDivisionalApplications(List<PTDivisionalApplicationForm> divisionalApplications);

    FileWrapper getOtherAttachments();

    void setOtherAttachments(FileWrapper otherAttachments);

    String getComment();

    void setComment(String comment);

    List<ExhPriorityForm> getExhibitions();

    void setExhibitions(List<ExhPriorityForm> exhibitions);

    List<PTParallelApplicationForm> getParallelApplications();

    void setParallelApplications(List<PTParallelApplicationForm> parallelApplications);

    Boolean getTrueDocumentsIndicator();

    void setTrueDocumentsIndicator(Boolean trueDocumentsIndicator);

    List<PCTForm> getPcts();

    void setPcts(List<PCTForm> pcts);

    List<String> getEarlierAppRepresentatives();

    void setEarlierAppRepresentatives(List<String> earlierAppRepresentatives);

    boolean isEarlierAppImported();

    void setEarlierAppImported(boolean earlierAppImported);

    List<String> getFormWarnings();

    void setFormWarnings(List<String> formWarnings);

    boolean getApplicantsImportedFromTemplate();

    void setApplicantsImportedFromTemplate(boolean applicantsImportedFromTemplate);

    boolean getPatentTemplateImported();

    TechnicalQuestionnaireForm getTechnicalQuestionnaireForm();

    void setTechnicalQuestionnaireForm(TechnicalQuestionnaireForm technicalQuestionnaireForm);

    List<ESPatentDetailsForm> getSpcPatents();

    void setSpcPatents(List<ESPatentDetailsForm> spcPatent);

    Boolean getCertificateRequestedIndicator();

    void setCertificateRequestedIndicator(Boolean certificateRequestedIndicator);
}
