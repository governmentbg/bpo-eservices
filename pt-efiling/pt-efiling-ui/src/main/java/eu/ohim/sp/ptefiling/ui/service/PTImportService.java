package eu.ohim.sp.ptefiling.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.ExhibitionPriorityFactory;
import eu.ohim.sp.common.ui.adapter.patent.InventorFactory;
import eu.ohim.sp.common.ui.adapter.patent.PCTFactory;
import eu.ohim.sp.common.ui.adapter.patent.PTPriorityFactory;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.register.*;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTImportServiceInterface;
import eu.ohim.sp.ptefiling.ui.validator.PTFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Raya
 * 18.06.2019
 */
@Service("importService")
public class PTImportService implements PTImportServiceInterface {

    private static final Logger logger = Logger.getLogger(PTImportService.class);

    @Autowired
    private BPOUMSearchService umSearchService;

    @Autowired
    private BPOPlantSearchService svSearchService;

    @Autowired
    private BPOPatentSearchService patentSearchService;

    @Autowired
    private BPOEBDPatentSearchService ebdPatentSearchService;

    @Autowired
    private BPOEPOPatentSearchService epoPatentSearchService;

    @Autowired
    private InventorFactory inventorFactory;

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    private PersonServiceInterface personServiceInterface;

    @Autowired
    private ExhibitionPriorityFactory exhibitionPriorityFactory;

    @Autowired
    private PTPriorityFactory ptPriorityFactory;

    @Autowired
    private PCTFactory pctFactory;

    @Autowired(required=false)
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private PTFormValidator validator;

    @Value("${sp.office}")
    public String office;

    @Value("${euipo.office}")
    public String euipoOffice;


    @Override
    public PTFlowBean getPatentDivisional(PTFlowBean originalFlowBean, String patentId) {
        Patent patent = patentSearchService.getPatentByAppNumber(patentId);
        importDivisionalApplication(originalFlowBean, patent);
        return originalFlowBean;
    }

    @Override
    public PTFlowBean getUMDivisional(PTFlowBean originalFlowBean, String modelId) {
        Patent patent = umSearchService.getUtilityModelByAppNumber(modelId);
        importDivisionalApplication(originalFlowBean, patent);
        return originalFlowBean;
    }

    @Override
    public PTFlowBean getSVDivisional(PTFlowBean originalFlowBean, String modelId) {
        Patent patent = svSearchService.getPlantByAppNumber(modelId);
        importDivisionalApplication(originalFlowBean, patent);
        return originalFlowBean;
    }

    private void importDivisionalApplication(PTFlowBean originalFlowBean, Patent patent){
        if(patent != null) {
            PTDivisionalApplicationForm ptDivisionalApplicationForm = new PTDivisionalApplicationForm();
            ptDivisionalApplicationForm.setImported(true);
            ptDivisionalApplicationForm.setDateDivisionalApplication(patent.getApplicationDate());
            ptDivisionalApplicationForm.setNumberDivisionalApplication(patent.getApplicationNumber());

            originalFlowBean.addObject(ptDivisionalApplicationForm);
            originalFlowBean.setEarlierAppImported(true);

            commonPreviousPatentImport(originalFlowBean, patent, office);
        } else {
            throw new NoResultsException("Not found", null, "error.import.noObjectFound.patent");
        }
    }

    @Override
    public PTFlowBean getPatentTransformation(PTFlowBean originalFlowBean, String patentId) {
        Patent patent = patentSearchService.getPatentByAppNumber(patentId);
        importTransformation(originalFlowBean, patent, office);
        return originalFlowBean;
    }

    @Override
    public PTFlowBean getPatentConversion(PTFlowBean originalFlowBean, String euPatentId) {
        Patent patent = ebdPatentSearchService.getPatentByAppNumber(euPatentId);
        importTransformation(originalFlowBean, patent, euipoOffice);
        return originalFlowBean;
    }

    private void importTransformation(PTFlowBean originalFlowBean, Patent patent, String countryCode){
        if(patent != null) {
            PTTransformationForm transformationForm = null;

            if(countryCode.equalsIgnoreCase(office)){
                transformationForm = new PTNationalTransformationForm();
            } else if(countryCode.equalsIgnoreCase(euipoOffice)) {
                transformationForm = new PTConversionForm();
                transformationForm.setPublicationNumber(patent.getPublicationNumber());
            } else {
                throw new SPException("Bad country code for import of transformation");
            }

            transformationForm.setImported(true);
            transformationForm.setCountryCode(countryCode);
            transformationForm.setApplicationNumber(patent.getApplicationNumber());
            transformationForm.setApplicationDate(patent.getApplicationDate());

            originalFlowBean.addObject(transformationForm);
            originalFlowBean.setEarlierAppImported(true);

            commonPreviousPatentImport(originalFlowBean, patent, countryCode);
        } else {
            throw new NoResultsException("Not found", null, "error.import.noObjectFound.patent");
        }
    }

    @Override
    public PTFlowBean getPatentNationalParallel(PTFlowBean originalFlowBean, String patentId) {
        Patent patent = patentSearchService.getPatentByAppNumber(patentId);
        importParallelApp(originalFlowBean, patent, office);
        return originalFlowBean;
    }

    @Override
    public PTFlowBean getPatentEUParallel(PTFlowBean originalFlowBean, String euPatentId) {
        Patent patent = ebdPatentSearchService.getPatentByAppNumber(euPatentId);
        importParallelApp(originalFlowBean, patent, euipoOffice);
        return originalFlowBean;
    }

    private void importParallelApp(PTFlowBean originalFlowBean, Patent patent, String countryCode){
        if(patent != null) {
            PTParallelApplicationForm parallelApplicationForm = null;

            if(countryCode.equalsIgnoreCase(office)){
                parallelApplicationForm = new PTNationalParallelApplicationForm();
            } else if(countryCode.equalsIgnoreCase(euipoOffice)) {
                parallelApplicationForm = new PTEuropeanParallelApplicationForm();
            } else {
                throw new SPException("Bad country code for import of parallel app");
            }

            parallelApplicationForm.setImported(true);
            parallelApplicationForm.setCountryCode(countryCode);
            parallelApplicationForm.setApplicationNumber(patent.getApplicationNumber());
            parallelApplicationForm.setApplicationDate(patent.getApplicationDate());

            originalFlowBean.addObject(parallelApplicationForm);
            originalFlowBean.setEarlierAppImported(true);

            commonPreviousPatentImport(originalFlowBean, patent, countryCode);
        } else {
            throw new NoResultsException("Not found", null, "error.import.noObjectFound.patent");
        }
    }

    private void commonPreviousPatentImport(PTFlowBean originalFlowBean, Patent patent, String office){
        clearImportedFromEarlierApp(originalFlowBean);

        inventorsImport(originalFlowBean, patent, office);
        applicantsImport(originalFlowBean, patent, office);
        representativesImport(originalFlowBean, patent, office);
        exhibitionsImport(originalFlowBean, patent);
        prioritiesImport(originalFlowBean, patent);
    }

    public PTFlowBean clearImportedFromEarlierApp(PTFlowBean originalFlowBean){
        originalFlowBean.clearInventors();
        originalFlowBean.clearExhibitions();
        originalFlowBean.clearPriorities();
        return originalFlowBean;
    }

    @Override
    public PTFlowBean getPatentTemplateImport(PTFlowBean originalFlowBean, String patentId, String patentIdType, Boolean forceReimport) {
        Patent patent = null;
        Patent epoPatent = null;
        if(patentIdType.equals("application")){
            patent = ebdPatentSearchService.getPatentByAppNumber(patentId);
        } else if(patentIdType.equals("registration")){
            patent = ebdPatentSearchService.getPatentByRegNumber(patentId);
        }

        if(patent != null){
            if(patent.getExternalReferenceNumber() != null && patent.getApplicationNumber() != null){
                epoPatent = epoPatentSearchService.getPatentByAppNumber(patent.getApplicationNumber());
            }

            if(epoPatent != null && epoPatent.getPatentValidated() != null) {
                originalFlowBean.getPatent().setPatentValidated(epoPatent.getPatentValidated());
            } else {
                originalFlowBean.getPatent().setPatentValidated(false);
            }

            if(epoPatent != null && originalFlowBean.getMainForm().getApplicationKind() != null &&
                originalFlowBean.getMainForm().getApplicationKind().equals(PatentApplicationKind.EP_VALIDATION_REQUEST_CHANGED_PATENT)){
                if(forceReimport) {
                    originalFlowBean.getPatent().setPatentTitle(epoPatent.getPatentTitle());
                    if (epoPatent.getRepresentatives() != null && epoPatent.getRepresentatives().size() > 0) {
                        epoPatent.getRepresentatives().stream().filter(r -> r.getIdentifiers() != null && r.getIdentifiers().size() > 0
                            && r.getIdentifiers().get(0) != null && r.getIdentifiers().get(0).getValue() != null).forEach(r -> {
                            try {
                                RepresentativeForm representativeForm = personServiceInterface.importRepresentative(r.getIdentifiers().get(0).getValue(), flowScopeDetails.getFlowModeId());
                                if(!originalFlowBean.existsObject(RepresentativeForm.class, representativeForm.getId())) {
                                    originalFlowBean.addObject(representativeForm);
                                }
                            } catch (Exception e) {
                                logger.error("Failed to find representative with id " + r.getIdentifiers().get(0).getValue());
                            }
                        });
                    }
                }
            }
            originalFlowBean.getPatent().setPatentTitleSecondLang(patent.getPatentTitleSecondLang());
            originalFlowBean.getPatent().setApplicationNumber(patent.getApplicationNumber());
            originalFlowBean.getPatent().setRegistrationNumber(patent.getRegistrationNumber());
            originalFlowBean.getPatent().setApplicationDate(patent.getApplicationDate());
            originalFlowBean.getPatent().setRegistrationDate(patent.getRegistrationDate());
            originalFlowBean.getPatent().setRegistrationPublicationDate(patent.getRegistrationPublicationDate());
            originalFlowBean.getPatent().setImported(true);
            originalFlowBean.getPatent().setId(patent.getApplicationNumber());
            originalFlowBean.getPatent().setExternalReferenceNumber(patent.getExternalReferenceNumber());
            originalFlowBean.getPatent().setPatentPublicationsInfo(patent.getPatentPublicationsInfo());


            originalFlowBean.clearInventors();
            originalFlowBean.clearPriorities();
            originalFlowBean.clearPcts();

            if((forceReimport != null && forceReimport) || originalFlowBean.getApplicantsImportedFromTemplate()){
                originalFlowBean.clearApplicants();
                applicantsImport(originalFlowBean, patent, euipoOffice);
                originalFlowBean.setApplicantsImportedFromTemplate(true);
            }

            inventorsImport(originalFlowBean, patent, euipoOffice);
            prioritiesImport(originalFlowBean, patent);
            pctsImport(patent, originalFlowBean);

            validator.collectApplicationValidationWarnings(originalFlowBean, flowScopeDetails);

            return originalFlowBean;
        } else {
            throw new NoResultsException("Not found", null, "error.import.noObjectFound.patentTemplate");
        }
    }

    @Override
    public PTFlowBean clearPatentTemplateImportedApplicants(PTFlowBean originalFlowBean) {
        originalFlowBean.clearApplicants();
        originalFlowBean.setApplicantsImportedFromTemplate(false);

        return originalFlowBean;
    }

    private void pctsImport(Patent patent, PTFlowBean ptFlowBean) {
        if(patent.getPcts() != null && patent.getPcts().size()>0){
            patent.getPcts().stream().forEach(p -> {
                PCTForm form = pctFactory.convertFrom(p);
                ptFlowBean.addObject(form);
            });
        }
    }

    private void inventorsImport(PTFlowBean originalFlowBean, Patent patent, String office) {
        if(patent.getInventors() != null && patent.getInventors().size()>0){
            patent.getInventors().stream().forEach(i -> {
                InventorForm form = inventorFactory.convertFrom(i);
                if(office.equals(euipoOffice)){
                    form.setId(null);
                    form.setImported(false);
                }
                originalFlowBean.addObject(form);
            });
        }
    }

    private void applicantsImport(PTFlowBean originalFlowBean, Patent patent, String office) {
        if(patent.getApplicants() != null && patent.getApplicants().size() > 0){
            patent.getApplicants().stream().forEach(a -> {
                ApplicantForm form = null;
                if(office.equals(euipoOffice)){
                    form = applicantFactory.convertFrom(a);
                    form.setImported(false);
                    form.setId(null);
                } else {
                    if(a.getIdentifiers() != null){
                        Optional<PersonIdentifier> optionalPersonIdentifier =  a.getIdentifiers().stream().filter(i -> i.getIdentifierKindCode() == null && i.getValue() != null).findAny();
                        if(optionalPersonIdentifier.isPresent()) {
                            if(!originalFlowBean.existsObject(ApplicantForm.class, optionalPersonIdentifier.get().getValue())) {
                                form = personServiceInterface.importApplicant(optionalPersonIdentifier.get().getValue(), flowScopeDetails.getFlowModeId());
                            }
                        }
                    }
                }
                if(form != null) {
                    originalFlowBean.addObject(form);
                }
            });
        }
    }

    private void representativesImport(PTFlowBean originalFlowBean, Patent patent, String office) {
        if(patent.getRepresentatives() != null && patent.getRepresentatives().size() > 0){
            if(!office.equals(euipoOffice)) {
                originalFlowBean.setEarlierAppRepresentatives(patent.getRepresentatives().stream().map(r -> {
                    if(r.getIdentifiers() != null){
                        Optional<PersonIdentifier> optionalPersonIdentifier = r.getIdentifiers().stream().filter(i -> i.getIdentifierKindCode() == null && i.getValue() != null).findAny();
                        if(optionalPersonIdentifier.isPresent()) {
                            return optionalPersonIdentifier.get().getValue();
                        } else {
                            return null;
                        }
                    }
                    return null;
                }).filter(id -> id != null).collect(Collectors.toList()));
            }
        }
    }

    private void prioritiesImport(PTFlowBean originalFlowBean, Patent patent) {
        if (patent.getPriorities() != null && patent.getPriorities().size() > 0) {
            patent.getPriorities().stream().forEach(p -> {
                PTPriorityForm form = ptPriorityFactory.convertFrom(p);
                originalFlowBean.addObject(form);
            });
        }
    }

    private void exhibitionsImport(PTFlowBean originalFlowBean, Patent patent) {
        if(patent.getExhibitions() != null && patent.getExhibitions().size()>0){
            patent.getExhibitions().stream().forEach(e -> {
                ExhPriorityForm form = exhibitionPriorityFactory.convertFrom(e);
                originalFlowBean.addObject(form);
            });
        }
    }
}
