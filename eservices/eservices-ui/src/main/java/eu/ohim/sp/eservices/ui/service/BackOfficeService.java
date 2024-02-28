package eu.ohim.sp.eservices.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.TrademarkFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.adapter.patent.ESPatentFactory;
import eu.ohim.sp.common.ui.adapter.userdoc.FilteredUserdocsFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.form.userdoc.FilteredUserdocsForm;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.core.domain.application.AppealKind;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.licence.LicenceKind;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ApplicationExtent;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.userdoc.FilteredUserdocs;
import eu.ohim.sp.core.register.BPOBackOfficeSearchService;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.BackOfficeServiceInterface;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BackOfficeService implements BackOfficeServiceInterface {

    @Autowired
    private BPOBackOfficeSearchService bpoBackOfficeSearchService;

    @Autowired
    private TrademarkFactory tmFactory;

    @Autowired
    private ESDesignFactory dsFactory;

    @Autowired
    private ESPatentFactory ptFactory;

    @Autowired
    private FileServiceInterface fileService;

    @Autowired
    private ESFlowBeanFactory esFlowBeanFactory;

    @Autowired
    private FilteredUserdocsFactory filteredUserdocsFactory;

    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");

    @Override
    public String unpublishedAppsAutocomplete(String user, String flowModeId, String text) {
        return bpoBackOfficeSearchService.getUnpublishedApplicationsAutocomplete(user, flowModeId, text);
    }

    @Override
    public TMDetailsForm getUnpublishedTradeMark(String user, String tradeMarkId, String provisionalId) {
        TradeMark core = bpoBackOfficeSearchService.getUnpublishedTrademark(user, tradeMarkId);
        TMDetailsForm tmDetailsForm = tmFactory.convertFrom(core);
        tmDetailsForm.setUnpublished(true);
        if(core.getImageSpecifications() != null && core.getImageSpecifications().size() >0 && core.getImageSpecifications().get(0).getRepresentation() != null &&
                core.getImageSpecifications().get(0).getRepresentation().getData() != null) {
            Document coreImg = core.getImageSpecifications().get(0).getRepresentation();
            StoredFile saved = null;
            try {
                String imgName = coreImg.getFileName() != null ? coreImg.getFileName(): tmDetailsForm.getApplicationNumber();
                String format = coreImg.getFileFormat() != null ? coreImg.getFileFormat().replace("image/", "."): "";
                saved = fileService.addFile(provisionalId, imgName+format,
                        coreImg.getFileFormat(), (long)coreImg.getData().length, coreImg.getData(), tmDetailsForm.getRepresentationAttachment());
                tmDetailsForm.getRepresentationAttachment().getStoredFiles().add(saved);
            } catch (IOException e) {
                throw new SPException(e);
            }
        }

        return tmDetailsForm;
    }

    @Override
    public List<ESDesignDetailsForm> getUnpublishedDesignApplication(String user, String designAppId, String provisionalId) {
        DesignApplication core = bpoBackOfficeSearchService.getUnpublishedDesignApp(user, designAppId);
        List<ESDesignDetailsForm> dsForms = dsFactory.convertListFromSingle(core);
        if(dsForms != null && dsForms.size()>0) {
            dsForms.stream().forEach(dsForm -> {
                dsForm.setUnpublished(true);
                Design design = core.getDesignDetails().stream().filter(d -> d.getDesignIdentifier().equals(dsForm.getDesignIdentifier())).findFirst().orElse(null);
                if(design != null && design.getViews() != null && design.getViews().size()>0 && design.getViews().get(0).getView() != null &&
                        design.getViews().get(0).getView().getDocument() != null && design.getViews().get(0).getView().getDocument().getData() != null){
                    Document coreImg = design.getViews().get(0).getView().getDocument();
                    StoredFile saved = null;
                    try {
                        String imgName = coreImg.getFileName() != null ? coreImg.getFileName(): dsForm.getDesignIdentifier();
                        String format = coreImg.getFileFormat() != null ? coreImg.getFileFormat().replace("image/", "."): "";
                        dsForm.getRepresentationAttachment().getStoredFiles().clear();
                        saved = fileService.addFile(provisionalId, imgName+format,
                                coreImg.getFileFormat(), (long)coreImg.getData().length, coreImg.getData(), dsForm.getRepresentationAttachment());

                        dsForm.getRepresentationAttachment().getStoredFiles().add(saved);
                    } catch (IOException e) {
                        throw new SPException(e);
                    }
                }
            });
        }

        return dsForms;
    }

    @Override
    public ESPatentDetailsForm getUnpublishedPatent(String user, String patentId, String provisionalId) {
        Patent core = bpoBackOfficeSearchService.getUnpublishedPatent(user, patentId);
        ESPatentDetailsForm patentDetailsForm = ptFactory.convertFrom(core);
        patentDetailsForm.setUnpublished(true);
        patentDetailsForm.setImported(true);
        return patentDetailsForm;
    }

    @Override
    public FilteredUserdocsForm getUserdocsForApplication(ESFlowBean esFlowBean) {
        String applicationNumber = null, applicationYear = null, username = null;
        User user = AuthenticationUtil.getAuthenticatedUser();
        if (user!=null){
            username=user.getUsername();
        }
        if(esFlowBean.getTmsList() != null && esFlowBean.getTmsList().size() >0){
            TMDetailsForm tm = esFlowBean.getTmsList().get(0);
            applicationNumber = tm.getApplicationNumber();
            applicationYear = YEAR_FORMAT.format(tm.getApplicationDate());
        } else if(esFlowBean.getDssList() != null && esFlowBean.getDssList().size()>0){
            ESDesignDetailsForm ds = esFlowBean.getDssList().get(0);
            applicationNumber = ds.geteSDesignApplicationData().getApplicationNumber();
            applicationYear = YEAR_FORMAT.format(ds.geteSDesignApplicationData().getApplicationDate());
        } else if(esFlowBean.getPatentsList() != null && esFlowBean.getPatentsList().size()>0){
            ESPatentDetailsForm pt = esFlowBean.getPatentsList().get(0);
            applicationNumber = pt.getApplicationNumber();
            applicationYear = YEAR_FORMAT.format(pt.getApplicationDate());
        }

        EServiceApplication eServiceApplication = esFlowBeanFactory.convertTo(esFlowBean);
        Map<String, Object> eserviceDetailsMap = createEserviceDetailsMap(eServiceApplication);

        FilteredUserdocs filteredUserdocs = bpoBackOfficeSearchService.getUserdocsForApplication(applicationNumber, applicationYear, username, eserviceDetailsMap);
        return filteredUserdocsFactory.convertFrom(filteredUserdocs);
    }

    private Map<String, Object> createEserviceDetailsMap(EServiceApplication eServiceApplication){
        Map<String, Object> appDetailsMap = new HashMap<>();
        appDetailsMap.put("applicationType", eServiceApplication.getApplicationType());
        boolean isRegistered = false;
        if (eServiceApplication instanceof TMeServiceApplication) {
            LimitedTradeMark tm = ((TMeServiceApplication) eServiceApplication).getTradeMarks() == null ? null : ((TMeServiceApplication) eServiceApplication).getTradeMarks().get(0);
            isRegistered = tm != null && tm.getRegistrationDate() != null && !StringUtils.isEmpty(tm.getRegistrationNumber());
        } else if (eServiceApplication instanceof DSeServiceApplication) {
            DesignApplication d = ((DSeServiceApplication) eServiceApplication).getDesignDetails() == null ? null : ((DSeServiceApplication) eServiceApplication).getDesignDetails().get(0);
            Design dd = d == null || d.getDesignDetails() == null ? null : d.getDesignDetails().get(0);
            isRegistered = dd != null && dd.getRegistrationDate() != null && !StringUtils.isEmpty(dd.getRegistrationNumber());
        } else if (eServiceApplication instanceof PTeServiceApplication) {
            Patent pt = ((PTeServiceApplication) eServiceApplication).getPatentList() == null ? null : ((PTeServiceApplication) eServiceApplication).getPatentList().get(0);
            isRegistered = pt != null && pt.getRegistrationDate() != null && !StringUtils.isEmpty(pt.getRegistrationNumber());
        }
        appDetailsMap.put("registered", isRegistered);
        if(eServiceApplication.getGsHelpers() != null && eServiceApplication.getGsHelpers().size()>0) {
            boolean res = eServiceApplication.getGsHelpers().get(0).getApplicationExtent() == ApplicationExtent.PARTIAL_GOODS_AND_SERVICES;
            appDetailsMap.put("partialGoodsAndServices", res);
        }
        if(eServiceApplication.getAppeals() != null && eServiceApplication.getAppeals().size()>0){
            AppealKind appealKind = eServiceApplication.getAppeals().get(0) != null ? eServiceApplication.getAppeals().get(0).getAppealKind(): null;
            if(appealKind != null){
                appDetailsMap.put("appealAgainstRefusal", appealKind.equals(AppealKind.APPEAL_AGAINST_REFUSAL));
                appDetailsMap.put("appealAgainstOppositionDecision", appealKind.equals(AppealKind.APPEAL_AGAINST_OPPOSITION_DECISION));
                appDetailsMap.put("appealNotAgainstOppositionDecisionAndRefusal", !appealKind.equals(AppealKind.APPEAL_AGAINST_REFUSAL) && !appealKind.equals(AppealKind.APPEAL_AGAINST_OPPOSITION_DECISION));
            }
        }
        if(eServiceApplication.getLicences() != null && eServiceApplication.getLicences().size()>0){
            LicenceKind licenceKind = eServiceApplication.getLicences().get(0) != null? eServiceApplication.getLicences().get(0).getLicenceKind(): null;
            if(licenceKind != null){
                appDetailsMap.put("nonExclusiveLicence", licenceKind.equals(LicenceKind.NONEXCLUSIVE));
            }
        }
        if(eServiceApplication.getChangeType() != null){
            appDetailsMap.put("changeType", eServiceApplication.getChangeType());
        }

        return appDetailsMap;
    }

}
