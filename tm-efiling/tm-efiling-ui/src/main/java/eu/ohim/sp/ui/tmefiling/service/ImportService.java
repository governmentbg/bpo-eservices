package eu.ohim.sp.ui.tmefiling.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.claim.*;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.SoundSpecification;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ImportServiceInterface;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "importService")
public class ImportService implements ImportServiceInterface {

    /** Logger for this class and subclasses */
    private static final Logger logger = Logger.getLogger(ImportService.class);

    private static final String WIZARD = "wizard";

    @Autowired
    private TradeMarkSearchService tradeMarkSearchService;

    @Autowired
    private FileServiceInterface fileService;

    @Autowired
    private PriorityFactory priorityFactory;

    @Autowired
    private SeniorityFactory seniorityFactory;

    @Autowired
    private TransformationFactory transformationFactory;

    @Autowired
    private FlowBeanFactory flowBeanFactory;

    @Autowired
    private SimilarMarkFactory similarMarkFactory;

    @Autowired
    DocumentFactory documentFactory;

    @Autowired
    private FilterImportable filterImportable;

    @Autowired
    private GoodsServicesServiceInterface goodsServicesServiceInterface;


    @Override
    public FlowBeanImpl getTradeMark(String office, String tradeMarkId, Boolean extraImport, String flowModeId,
            FlowBeanImpl originalFlowBean) {

        TradeMark tradeMark = null;
        try {
            tradeMark = searchTradeMark(office, tradeMarkId, originalFlowBean.getIdreserventity());
        } catch (Exception e) {
            logger.error("ImportService getTradeMark error: " + e.getMessage());
            throw new SPException("Error importing trademark","error.import.trademark");
        }

        if (tradeMark != null) {
            FlowBeanImpl flowBeanImpl = flowBeanFactory.convertFrom(tradeMark);

            FlowBeanImpl filtered = (FlowBeanImpl)filterImportable.filterFlowBean(new FlowBeanImpl(), flowBeanImpl, flowModeId, SectionViewConfiguration.ImportType.PREVIOUS_CTM);

            if(filtered.getGoodsAndServices() != null && filtered.getGoodsAndServices().size()>0){
                filtered.getGoodsAndServices().forEach( e-> e.setLangId(filtered.getFirstLang()));
                goodsServicesServiceInterface.verifyListOfGS(filtered);
            }

            checkIdReserventity(originalFlowBean, filtered);
            setUserDetails(originalFlowBean, filtered);
            return filtered;
        } else {
            throw new NoResultsException("Trademark not found", null, "error.import.noObjectFound.trademark");
        }
    }

    @Override
    public FlowBeanImpl getTradeMarkPriority(FlowBeanImpl originalFlowBean, String office, String tradeMarkId,
            Boolean extraImport, String flowModeId) {
        TradeMark tm = null;
        try {
            tm = tradeMarkSearchService.getForeignTradeMark(office, tradeMarkId);
        } catch (Exception e) {
            logger.error("ImportService getTradeMark error: " + e.getMessage());
            throw new SPException("Error importing trademark","error.import.trademark");
        }

        if (tm != null) {
            flowBeanFactory.convertFrom(tm);
            if (extraImport) {
                originalFlowBean = importTradeMarkDetails(originalFlowBean, flowModeId, tm);
            }

            // Add the priority based on the imported trademark
            PriorityForm priorityForm = new PriorityForm();
            priorityForm.setCountry(tm.getRegistrationOffice());
            priorityForm.setNumber(tm.getApplicationNumber());
            priorityForm.setDate(tm.getApplicationDate());
            priorityForm.setImported(true);
            originalFlowBean.addObject(priorityForm);
        } else {
            throw new NoResultsException("Trademark not found", null, "error.import.noObjectFound.trademark");
        }

        return originalFlowBean;
    }

    @Override
    public FlowBeanImpl getTradeMarkSeniority(FlowBeanImpl originalFlowBean, String office, String tradeMarkId,
            Boolean extraImport, String flowModeId) {

        TradeMark tm = null;
        try {
            tm = searchTradeMark(office, tradeMarkId, originalFlowBean.getIdreserventity());
        } catch (Exception e) {
            logger.error("ImportService getTradeMark error: " + e.getMessage());
            throw new SPException("Error importing trademark","error.import.trademark");
        }

        if (tm != null) {
            flowBeanFactory.convertFrom(tm);
            if (extraImport) {
                originalFlowBean = importTradeMarkDetails(originalFlowBean, flowModeId, tm);
            }

            // Add the priority based on the imported trademark
            SeniorityForm seniorityForm = new SeniorityForm();
            seniorityForm.setCountry(tm.getRegistrationOffice());
            seniorityForm.setFilingNumber(tm.getApplicationNumber());
            if (tm.getInternationalTradeMark() != null && tm.getInternationalTradeMark()) {
                seniorityForm.setNature(SeniorityKindForm.INTERNATIONAL_TRADE_MARK);
            } else {
                seniorityForm.setNature(SeniorityKindForm.NATIONAL_OR_REGIONAL_TRADE_MARK);
            }
            seniorityForm.setFilingDate(tm.getApplicationDate());
            seniorityForm.setRegistrationNumber(tm.getRegistrationNumber());
            seniorityForm.setRegistrationDate(tm.getRegistrationDate());
            seniorityForm.setImported(true);
            originalFlowBean.addObject(seniorityForm);
        } else {
            throw new NoResultsException("Trademark not found", null, "error.import.noObjectFound.trademark");
        }

        return originalFlowBean;
    }

    @Override
    public FlowBeanImpl getTradeMarkTransformation(FlowBeanImpl originalFlowBean, String office, String tradeMarkId,
            Boolean extraImport, String flowModeId) {

        TradeMark tm = null;
        try {
            tm = tradeMarkSearchService.getInternationalTradeMark(office, tradeMarkId, true);
        } catch (Exception e) {
            logger.error("ImportService getTradeMark error: " + e.getMessage());
            throw new SPException("Error importing trademark","error.import.trademark");
        }

        if (tm != null) {

            if(StringUtils.isNotEmpty(originalFlowBean.getMainForm().getWordRepresentation()) && tm.getWordSpecifications() != null) {
                boolean sameNameAsInForm = false;
                for(WordSpecification word : tm.getWordSpecifications()) {
                    if(originalFlowBean.getMainForm().getWordRepresentation().trim().equalsIgnoreCase(word.getWordElements())){
                        sameNameAsInForm = true;
                        break;
                    }
                }
                if (!sameNameAsInForm){
                    throw new NoResultsException("Trademark does not match", null, "error.import.nameDoesNotMatch.trademark");
                }
            }

            flowBeanFactory.convertFrom(tm);
            if (extraImport) {
                originalFlowBean = importTradeMarkDetails(originalFlowBean, flowModeId, tm);
            }

            // Add the IR based on the imported trademark
            TransformationForm transformationForm = new TransformationForm();
            transformationForm.setIrNumber(tm.getApplicationNumber());
            transformationForm.setIrDate(tm.getApplicationDate());
            transformationForm.setPriosenioDate(new Date());
            transformationForm.setCancelationDate(tm.getTerminationDate());
            transformationForm.setImported(true);
            transformationForm.setTransformationCountryCode(office);
            originalFlowBean.addObject(transformationForm);
        } else {
            throw new NoResultsException("Trademark not found", null, "error.import.noObjectFound.trademark");
        }

        return originalFlowBean;
    }

    @Override
    public FlowBeanImpl getCTMConversion(FlowBeanImpl originalFlowBean, String office, String tradeMarkId,
            Boolean extraImport, String flowModeId) {

        TradeMark tradeMark = searchTradeMark(office, tradeMarkId, originalFlowBean.getIdreserventity());

        if (tradeMark != null) {
            if (extraImport) {
                originalFlowBean = importTradeMarkDetails(originalFlowBean, flowModeId, tradeMark);
            }
            ConversionForm conversion = new ConversionForm();
            conversion.setConversionApplicationNumber(tradeMark.getApplicationNumber());
            conversion.setConversionApplicationDate(tradeMark.getApplicationDate());
            conversion.setConversionPriorityDate(new Date());
            conversion.setImported(true);
            originalFlowBean.addObject(conversion);
        } else {
            throw new NoResultsException("Trademark not found", null, "error.import.noObjectFound.trademark");
        }

        return originalFlowBean;
    }

    @Override
    public String getTradeMarkAutocomplete(String office, String text, Integer numberOfResults, boolean previousCTM) {

        String results = "";

        try {
            results = tradeMarkSearchService.getTradeMarkAutocomplete(office, text, 50);
        } catch (Exception e) {
            logger.debug("ImportService getTradeMarkAutocomplete error: " + e.getMessage());
        }

        return results;
    }

    @Override
    public List<SimilarMarkForm> getSimilarTradeMarks(String office, FlowBeanImpl flowBeanImpl) throws SPException{
        TradeMark tradeMark = flowBeanFactory.convertTo(flowBeanImpl);
        return similarMarkFactory.convertFrom(tradeMarkSearchService.getPreclearanceReport(office, tradeMark));
    }

    private TradeMark searchTradeMark(String office, String tradeMarkId, String idreserventity) {
        TradeMark tradeMark = null;
        if (StringUtils.isNotBlank(office) && StringUtils.isNotBlank(tradeMarkId)) {
            try {
                tradeMark = tradeMarkSearchService.getInternationalTradeMark(office, tradeMarkId, Boolean.TRUE);
            } catch (Exception e) {
                logger.error("ImportService getTradeMark error: " + e.getMessage());
                throw new SPException("Error importing trademark","error.import.trademark");
            }

            if (tradeMark != null) {

                if (tradeMark.getTradeMarkDocuments() != null && !tradeMark.getTradeMarkDocuments().isEmpty()) {
                    for (AttachedDocument attachment : tradeMark.getTradeMarkDocuments()) {
                        if (attachment.getDocument() != null) {
                            attachment.setDocument(checkDocumentId(attachment.getDocument(), idreserventity));
                        }
                    }
                }

                if (tradeMark.getImageSpecifications() != null && !tradeMark.getImageSpecifications().isEmpty()) {
                    for (ImageSpecification imageSpecification : tradeMark.getImageSpecifications()) {
                        if (imageSpecification.getRepresentation() != null) {
                            imageSpecification.setRepresentation(checkDocumentId(imageSpecification.getRepresentation(),
                                    idreserventity));
                        }
                    }
                }

                if (tradeMark.getSoundRepresentations() != null && !tradeMark.getSoundRepresentations().isEmpty()) {
                    for (SoundSpecification soundRepresentation : tradeMark.getSoundRepresentations()) {
                        if (soundRepresentation.getDocument() != null) {
                            soundRepresentation.setDocument(checkDocumentId(soundRepresentation.getDocument(),
                                    idreserventity));
                        }
                    }
                }

            }
        }
        return tradeMark;
    }

    private void checkIdReserventity(FlowBeanImpl originalFlowBean, FlowBeanImpl flowBeanImpl) {
        if (StringUtils.isBlank(flowBeanImpl.getIdreserventity())) {
            flowBeanImpl.setIdreserventity(originalFlowBean.getIdreserventity());
        }
    }

    private void setUserDetails(FlowBeanImpl originalFlowBean, FlowBeanImpl flowBeanImpl) {
        flowBeanImpl.setCurrentUserEmail(originalFlowBean.getCurrentUserEmail());
        flowBeanImpl.setCurrentUserName(originalFlowBean.getCurrentUserName());
    }

    private Document checkDocumentId(Document document, String idreserventity) {
        if (StringUtils.isBlank(document.getDocumentId())) {
            try {
                StoredFile storedFile = fileService.addFile(idreserventity, document.getFileName(),
                        document.getFileFormat(), document.getData(), false);
                if (storedFile != null) {
                    document = documentFactory.convertTo(storedFile);
                }
            } catch (IOException e) {
                logger.debug("CheckDocumentId error: " + e.getMessage());
            }
        }

        return document;

    }

    private FlowBeanImpl importTradeMarkDetails(FlowBeanImpl originalFlowBean, String flowModeId, TradeMark tradeMark) {
        FlowBeanImpl flowBeanImpl;
        flowBeanImpl = flowBeanFactory.convertFrom(tradeMark);
        // Don't Import G&S
        if (WIZARD.equals(flowModeId)) {
            flowBeanImpl.clearGandS();
            if (originalFlowBean.getGoodsAndServices() != null && !originalFlowBean.getGoodsAndServices().isEmpty()) {
                for (GoodAndServiceForm goodsAndServices : originalFlowBean.getGoodsAndServices()) {
                    flowBeanImpl.addGoodAndService(goodsAndServices);
                }
            }
        }
        return flowBeanImpl;
    }
}
