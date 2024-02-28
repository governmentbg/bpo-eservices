package eu.ohim.sp.ui.tmefiling.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.service.interfaces.FastTrackServiceInterface;
import eu.ohim.sp.common.ui.service.interfaces.FeeServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.core.domain.fasttrack.FastTrackFail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.flow.section.TrademarkFlowBean;
import eu.ohim.sp.common.ui.form.application.SubmittedForm;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import org.springframework.webflow.execution.RequestContextHolder;

@Service(value="draftApplicationService")
public class DraftApplicationService implements eu.ohim.sp.ui.tmefiling.service.interfaces.ApplicationServiceInterface {

    @Value("${sp.office}")
    private String office;

    @Autowired
    private SubmittedMap submittedMap;

    @Autowired
    private ApplicationService applicationServiceInterface;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private SectionViewConfiguration sectionViewConfiguration;
    
    @Autowired
    private FlowBeanFactory flowBeanFactory;

    @Autowired
    private FeeServiceInterface feeService;

    @Autowired(required=false)
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private FastTrackServiceInterface fastTrackService;

    @Autowired
    private FormUtil formUtil;

    private static final String TM_EFILING_MODULE = "tmefiling";
    private static final FormatXML EXPORT_FORMAT = FormatXML.APPLICATION_XML;

    @Override
    public String provideProvisionalID() {
        DraftApplication draftApplication =  applicationServiceInterface.initDraftApplication(office, TM_EFILING_MODULE, formUtil.getType());
        return draftApplication.getProvisionalId();
    }

    @Override
    public String providePaymentID(String filingNumber) {
        boolean retrievePaymentNumber = configurationServiceDelegator.isServiceEnabled(AvailableServices.RETRIEVE_PAYMENT_NUMBER.value());
        if(retrievePaymentNumber) {
            DraftApplication draftApplication = applicationServiceInterface.getDraftApplication(office, TM_EFILING_MODULE, filingNumber);
            String paymentId = filingNumber;
            draftApplication.setPaymentId(draftApplication.getProvisionalId());
            applicationServiceInterface.updateDraftApplication(draftApplication);
            return paymentId;
        }
        return null;

    }

    @Override
    public byte[] saveApplicationLocally(TrademarkFlowBean flowBean) {
    	TradeMarkApplication application = flowBeanFactory.convertToTradeMarkApplication((FlowBeanImpl) flowBean);

    	applicationServiceInterface.persistApplication(office, TM_EFILING_MODULE, EXPORT_FORMAT, application);
        return applicationServiceInterface.exportApplication(office, TM_EFILING_MODULE, EXPORT_FORMAT,
                                                        application.getApplicationFilingNumber());
    }

    @Override
    public String loadApplicationLocally(byte[] data) {
    	TradeMarkApplication tradeMarkApplication = (TradeMarkApplication) applicationServiceInterface.
    			importApplication(office, TM_EFILING_MODULE, data, formUtil.getType(), EXPORT_FORMAT);
    	if (tradeMarkApplication != null) {
            if(((tradeMarkApplication.getApplicationType() == null || tradeMarkApplication.getApplicationType().equals("TM")) && !flowScopeDetails.getFlowModeId().equals("wizard"))
                || (tradeMarkApplication.getApplicationType() != null && tradeMarkApplication.getApplicationType().equals("GI_EFILING") && !flowScopeDetails.getFlowModeId().equals("gi-efiling"))){
                throw new SPException("Wrong application type loaded for "+flowScopeDetails.getFlowModeId());
            }
    		return tradeMarkApplication.getApplicationFilingNumber();
    	} else {
    		throw new SPException("Xml content not valid");
    	}
    }

    @Override
    public TrademarkFlowBean loadApplicationLocally(String provisionalId) {
    	TradeMarkApplication tradeMarkApplication = (TradeMarkApplication) applicationServiceInterface.
    			retrieveApplication(office, TM_EFILING_MODULE, EXPORT_FORMAT, provisionalId);
        FlowBeanImpl flowBean = flowBeanFactory.convertFromTradeMarkApplication(tradeMarkApplication);
        Collection<FastTrackFail> fastTrackFails = fastTrackService.calculateFastTrackFails(flowBean, getFlowModeId(flowScopeDetails), null);
        flowBean.setFastTrack(fastTrackFails == null || fastTrackFails.isEmpty());
    	return flowBean;
    }

    @Override
    public TrademarkFlowBean loadApplication(String draftId, boolean finalDraft) {
        return loadApplicationLocally(draftId);
    }

    @Override
    public void saveApplication(TrademarkFlowBean flowBean, boolean finalDraft) {
    }

    @Override
    public String storeSubmittedApplication(TrademarkFlowBean flowBean) {
    	String receiptId = fileApplication(flowBean);
        
    	FlowBeanImpl flowBeanImpl = (FlowBeanImpl) flowBean;
        
        SubmittedForm submittedForm = new SubmittedForm();
        submittedForm.setProvisionalId(flowBeanImpl.getIdreserventity());
        submittedForm.setDocumentId(receiptId);
        submittedForm.setFlowBean(flowBean);
        
        PaymentForm paymentForm = flowBeanImpl.getPaymentForm();
        if (paymentForm != null) {
        	submittedForm.setPaymentMethod(paymentForm.getPaymentMethod());
        	if (paymentForm.getPaymentResult() != null && 
        			paymentForm.getPaymentResult().getPaymentRequest() != null) {
        		submittedForm.setPaymentId(paymentForm.getPaymentResult().getPaymentRequest().getPaymentRequestId());
        	}
        }
        
        String uuid = UUID.randomUUID().toString();
        submittedMap.put(uuid, submittedForm);
        
        return uuid;
    }

    @Override
    public String fileApplication(TrademarkFlowBean flowBean) {
        //Recalculating fees in order to avoid some problems that have occurred in the past leading to incorrectly calculated taxes
        FlowBeanImpl flowBeanImpl = (FlowBeanImpl)flowBean;
        feeService.getFeesInformation(flowBeanImpl);
        Collection<FastTrackFail> fastTrackFails = fastTrackService.calculateFastTrackFails(flowBeanImpl, getFlowModeId(flowScopeDetails),null);
        flowBeanImpl.setFastTrack(fastTrackFails == null || fastTrackFails.isEmpty());

    	String receiptId = "";
        SPUser user = AuthenticationUtil.getAuthenticatedUser();
    	TradeMarkApplication tradeMarkApplication = flowBeanFactory.
    			convertToTradeMarkApplication((FlowBeanImpl)flowBean);
        if(user != null) {
            tradeMarkApplication.setUser(user.getUsername());
        }
    	if (tradeMarkApplication != null) {
    		tradeMarkApplication.setApplicationType(formUtil.getType());
    		TradeMarkApplication filedTradeMarkApplication = (TradeMarkApplication) applicationServiceInterface.
    				fileApplication(office, formUtil.getModule(), tradeMarkApplication);
    		if (filedTradeMarkApplication != null && filedTradeMarkApplication.getDocuments() != null
    				&& !filedTradeMarkApplication.getDocuments().isEmpty()) {
    			for (AttachedDocument attachedDocument : filedTradeMarkApplication.getDocuments()) {
					if (DocumentKindDefault.APPLICATION_RECEIPT.equals(attachedDocument.getDocumentKind())) {
						receiptId = attachedDocument.getDocument().getDocumentId();
						break;
					}
				}
    		}
    	}
    	return receiptId;
    }

    @Override
    public ErrorList validateApplication(TrademarkFlowBean flowBean, RulesInformation rulesInformation, String flowModeId) {
    	TradeMarkApplication tradeMarkApplication = flowBeanFactory.convertToTradeMarkApplication((FlowBeanImpl) flowBean);
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rulesInformation.getCustomObjects().put("sections", sections);
    	return applicationServiceInterface.validateApplication(TM_EFILING_MODULE, tradeMarkApplication, rulesInformation);
    }

    @Override
    public ErrorList validateApplication(TrademarkFlowBean flowBean,
                                         RulesInformation rulesInformation, String flowModeId, String stateId) {
    	TradeMarkApplication tradeMarkApplication = flowBeanFactory.convertToTradeMarkApplication((FlowBeanImpl) flowBean);
    	
    	List<Section> sections = sectionViewConfiguration.getSortedSections(flowModeId, stateId);
    	rulesInformation.getCustomObjects().put("sections", sections);
        return applicationServiceInterface.validateApplication(TM_EFILING_MODULE, tradeMarkApplication, rulesInformation);
    }

    public boolean redirectToPortal(TrademarkFlowBean flowBean){
        boolean submitToPortal = configurationServiceDelegator.isServiceEnabled(AvailableServices.SUBMIT_PORTAL.value());
        return submitToPortal;
    }

    public String getRedirectUrl(String provisionalId){
        String url = configurationServiceDelegator.getApplicationManagementUrl(formUtil.getModule(), "application.management.url", null);
        return url + provisionalId;
    }

    private String getFlowModeId(FlowScopeDetails flowScopeDetails) {
        String flowModeId = flowScopeDetails != null ? flowScopeDetails.getFlowModeId(): null;
        if (flowModeId == null && RequestContextHolder.getRequestContext() != null  && RequestContextHolder.getRequestContext().getActiveFlow() != null) {
            flowModeId = RequestContextHolder.getRequestContext().getActiveFlow().getId();
        }
        return flowModeId;
    }
}
