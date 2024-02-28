package eu.ohim.sp.eservices.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.patent.ESPatentFactory;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.*;
import eu.ohim.sp.eservices.ui.service.interfaces.ESPatentServiceInterface;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * Created by Raya
 * 12.12.2019
 */
@Service
public class ESPatentService implements ESPatentServiceInterface {

    @Autowired
    private BPOPatentSearchService patentSearchService;

    @Autowired
    private BPOEPOPatentSearchService epoSearchService;

    @Autowired
    private BPOUMSearchService umSearchService;

    @Autowired
    private BPOSPCSearchService spcSearchService;

    @Autowired
    private BPOPlantSearchService plantSearchService;

    @Autowired
    private IPOAutocompleteSearchService ipoAutocompleteSearchService;

    @Autowired
    private SectionViewConfiguration sectionViewConfiguration;

    @Autowired
    private ESPatentFactory esPatentFactory;

    private void configureBlockingMessages(RulesInformation rulesInformation, Boolean mandatoryBlocking) {

    }

    @Override
    public ESPatentDetailsForm getPatent(String applicationId, String flowModeId) {
        Patent patent = null;
        try {
            if (flowModeId.startsWith("pt-")) {
                patent = patentSearchService.getPatentByAppNumber(applicationId);
            } else if (flowModeId.startsWith("um-")) {
                patent = umSearchService.getUtilityModelByAppNumber(applicationId);
            } else if (flowModeId.startsWith("ep-")) {
                patent = epoSearchService.getPatentByAppNumber(applicationId);
            } else if (flowModeId.startsWith("sv-")) {
                patent = plantSearchService.getPlantByAppNumber(applicationId);
            } else if (flowModeId.startsWith("spc-")) {
                patent = spcSearchService.getSPCByAppNumber(applicationId);
            }
        } catch (Exception e) {
            throw new SPException("Failed to call the service", e, "generic.errors.service.fail");
        }

        if(patent != null){
            ESPatentDetailsForm form = esPatentFactory.convertFrom(patent);
            form.setImported(true);
            return form;
        }

        return null;
    }

    @Override
    public String autocompletePatent(String word, int rows, String flowModeId) {
        return ipoAutocompleteSearchService.ipoAutocomplete(word, rows, flowModeId);
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
            rulesInformation.getCustomObjects().put(EServicesConstants.IMPORTACT, Boolean.TRUE);
        } else {
            configureBlockingMessages(rulesInformation, Boolean.FALSE);
            rulesInformation.getCustomObjects().put(EServicesConstants.IMPORTACT, Boolean.FALSE);
        }
        //convert to core domain
        Patent patent = esPatentFactory.convertTo(patentForm);
        //validate
        ErrorList errs = patentSearchService.validatePatent(EServicesConstants.TM_SV, patent, rulesInformation);
        if(errs!=null) {
            errs.getErrorList();
        }
        return errs;
    }
}
