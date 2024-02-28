package eu.ohim.sp.ptefiling.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.ExhibitionPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.patent.*;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.patent.*;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.*;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTPatentServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * Created by Raya
 * 12.07.2019
 */
@Service
public class PTPatentService implements PTPatentServiceInterface {

    private static final Logger LOGGER = Logger.getLogger(PTPatentService.class);

    @Autowired
    private IPApplicationService ipApplicationService;

    @Autowired
    private PatentApplicationService patentApplicationService;

    @Autowired
    private SectionViewConfiguration sectionViewConfiguration;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PatentViewFactory patentViewFactory;

    @Autowired
    private DocumentFactory documentFactory;

    @Autowired
    private PTDivisionalApplicationFactory divisionalApplicationFactory;

    @Autowired
    private PTPriorityFactory ptPriorityFactory;

    @Autowired
    private PTTransformationFactory ptTransformationFactory;

    @Autowired
    private PTParallelApplicationFactory ptParallelApplicationFactory;

    @Autowired
    private ContactDetailsFactory contactDetailsFactory;

    @Autowired
    private PCTFactory pctFactory;

    @Autowired
    private ExhibitionPriorityFactory exhibitionPriorityFactory;

    @Autowired
    private BPOPatentSearchService patentSearchService;

    @Autowired
    private BPOEPOPatentSearchService epoSearchService;

    @Autowired
    private ESPatentFactory esPatentFactory;

    @Autowired
    private IPOAutocompleteSearchService ipoAutocompleteSearchService;

    @Autowired
    private BPOEBDPatentsAutocompleteService bpoebdPatentsAutocompleteService;

    @Value("${sp.office}")
    public String office;

    @Value("${euipo.office}")
    public String euipoOffice;

    private void configureBlockingMessages(RulesInformation rulesInformation, Boolean mandatoryBlocking) {

    }

    private void putSections(RulesInformation rules, String flowModeId) {
        Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
        rules.getCustomObjects().put("sections", sections);
    }

    @Override
    public ErrorList validatePriority(String module, PTPriorityForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);

        PatentPriority priority = ptPriorityFactory.convertTo(form);
        return ipApplicationService.validatePriorityClaim(module, priority, rules);
    }

    @Override
    public ErrorList validateTransformation(String module, PTTransformationForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);
        PatentTransformation transformationPriority = ptTransformationFactory.convertTo(form);
        return patentApplicationService.validatePatentTransformation(module, transformationPriority, rules);
    }

    @Override
    public ErrorList validateDivisionalApplication(String module, PTDivisionalApplicationForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);
        DivisionalApplicationDetails divisionalApplicationDetails =  divisionalApplicationFactory.convertTo(form);
        return patentApplicationService.validateDivisionalApplication(module, divisionalApplicationDetails, rules);
    }

    @Override
    public ErrorList validatePatentView(String module, PatentViewForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);
        PatentView patentView  = patentViewFactory.convertTo(form);
        return patentApplicationService.validatePatentView(module, patentView, rules);
    }

    @Override
    public ErrorList validateStoredFile(String module, StoredFile storedFile, RulesInformation rules, Errors errors,
                                        String flowModeId) {
        putSections(rules, flowModeId);
        Document document = documentFactory.convertTo(storedFile);
        return documentService.validateDocument(module, document, rules);
    }

    @Override
    public ErrorList validateParallelApplication(String module, PTParallelApplicationForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);
        ParallelApplication parallelApplication = ptParallelApplicationFactory.convertTo(form);
        return patentApplicationService.validateParallelApplication(module, parallelApplication, rules);

    }

    @Override
    public ErrorList validateExhibition(String module, ExhPriorityForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);
        ExhibitionPriority exhibitionPriority = exhibitionPriorityFactory.convertTo(form);
        return ipApplicationService.validateExhibitionPriorityClaim(module, exhibitionPriority, rules);
    }

    @Override
    public ErrorList validatePCT(String module, PCTForm form, RulesInformation rules, Errors errors, String flowModeId) {
        putSections(rules, flowModeId);
        PCT pct = pctFactory.convertTo(form);
        return patentApplicationService.validatePCT(module, pct, rules);
    }

    @Override
    public ErrorList validateApplicationCA(String module, ApplicationCAForm applicationCAForm, RulesInformation rules, Errors errors, String flowModeId) {
       putSections(rules, flowModeId);
       ContactDetails contactDetails = contactDetailsFactory.convertTo(applicationCAForm.getCorrespondenceAddressForm());
       return patentApplicationService.validateApplicationCA(module, contactDetails, rules);
    }

    @Override
    public ESPatentDetailsForm getNationalPatent(String applicationId) {
        Patent patent = null;
        try {
           patent = patentSearchService.getPatentByAppNumber(applicationId);
        } catch (Exception e) {
            throw new SPException("Failed to call the service", e, "generic.errors.service.fail");
        }
        return convertPatentToForm(patent);
    }

    @Override
    public ESPatentDetailsForm getEPOPatent(String applicationId) {
        Patent patent = null;
        try {
            patent = epoSearchService.getPatentByAppNumber(applicationId);
        } catch (Exception e) {
            throw new SPException("Failed to call the service", e, "generic.errors.service.fail");
        }
        return convertPatentToForm(patent);
    }

    private ESPatentDetailsForm convertPatentToForm(Patent patent) {
        if(patent != null){
            ESPatentDetailsForm form = esPatentFactory.convertFrom(patent);
            form.setImported(true);
            return form;
        } else {
            return null;
        }
    }

    @Override
    public ErrorList validatePatent(String module, ESPatentDetailsForm patentForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
        Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
        rulesInformation.getCustomObjects().put("sections", sections);
        rulesInformation.getCustomObjects().put("eservice", flowModeId);
        rulesInformation.getCustomObjects().put("imported", Boolean.valueOf(patentForm.getImported()));
        //blocking on import (no db check), db check otherwise
        if(!patentForm.getCheckBdBlocking()) {
            configureBlockingMessages(rulesInformation, Boolean.TRUE);
            rulesInformation.getCustomObjects().put("importAction", Boolean.TRUE);
        } else {
            configureBlockingMessages(rulesInformation, Boolean.FALSE);
            rulesInformation.getCustomObjects().put("importAction", Boolean.FALSE);
        }
        //convert to core domain
        Patent patent = esPatentFactory.convertTo(patentForm);
        //validate
        ErrorList errs = patentSearchService.validatePatent("eservices", patent, rulesInformation);
        if(errs!=null) {
            errs.getErrorList();
        }
        return errs;
    }

    @Override
    public String autocompletePatent(String office, String word, int rows, String flowModeId) {
        if(office.equals(euipoOffice)){
            return bpoebdPatentsAutocompleteService.ebdPatentsAutocomplete(word, rows);
        } else if(office.equals(this.office)){
            return ipoAutocompleteSearchService.ipoAutocomplete(word, rows, flowModeId);
        }
        return null;
    }
}
