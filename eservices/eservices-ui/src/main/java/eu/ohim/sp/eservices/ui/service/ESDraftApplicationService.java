package eu.ohim.sp.eservices.ui.service;

import java.util.UUID;

import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.service.interfaces.FeeServiceInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.util.GroundsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.application.SubmittedForm;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;

@Service(value="draftApplicationService")
public class ESDraftApplicationService implements ESApplicationServiceInterface{

    @Value("${sp.office}")
    private String office;

    @Autowired
    private SubmittedMap submittedMap;
    
    @Autowired
    private ApplicationService applicationServiceInterface;
	
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Autowired
    private ESFlowBeanFactory flowBeanFactory;
    
	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;
	
	@Autowired(required=false)
	private FlowScopeDetails flowScopeDetails;
	
	@Autowired
	private FormUtil formUtil;

	@Autowired
	private GroundsUtil groundsUtil;

	@Autowired
	private FeeServiceInterface feeService;

	@Autowired
	private LegalActVersionFactory legalActVersionFactory;
		
    private static final String ESERVICES_MODULE = "eservices"; //TODO should be eservices
    private static final FormatXML EXPORT_FORMAT = FormatXML.APPLICATION_XML;
    private static final FormatXML SAVE_PORTAL_FORMAT = FormatXML.APPLICATION_EPUB;
    
    
    @Override
    public String provideProvisionalID() {
    	DraftApplication draftApplication =  applicationServiceInterface.initDraftApplication(office, ESERVICES_MODULE, formUtil.getType());    	    	
        return draftApplication.getProvisionalId();
    }
    
    @Override
    public String providePaymentID(String filingNumber) {
    	boolean retrievePaymentNumber = configurationServiceDelegator.isServiceEnabled(AvailableServices.RETRIEVE_PAYMENT_NUMBER.value());    	
    	if(retrievePaymentNumber) {    		
    		DraftApplication draftApplication = applicationServiceInterface.getDraftApplication(office, ESERVICES_MODULE, filingNumber);
    		String paymentId = filingNumber;
    		draftApplication.setPaymentId(draftApplication.getProvisionalId());
    		applicationServiceInterface.updateDraftApplication(draftApplication);
    		return paymentId;
    	}
        return null;

    }

    @Override
    public byte[] saveApplicationLocally(ESFlowBean flowBean) {    	
    	IPApplication eServiceApplication = flowBeanFactory.convertTo(flowBean);
    	applicationServiceInterface.persistApplication(office, ESERVICES_MODULE, EXPORT_FORMAT, eServiceApplication);    	
    	return applicationServiceInterface.exportApplication(office, ESERVICES_MODULE, EXPORT_FORMAT, eServiceApplication.getApplicationFilingNumber());
    }
    
    @Override
    public String loadApplicationLocally(byte[] data) {
    	IPApplication eServiceApplication = applicationServiceInterface.importApplication(office, ESERVICES_MODULE, data, formUtil.getType(), EXPORT_FORMAT);
    	if(eServiceApplication != null && eServiceApplication.getApplicationType() != null &&
    			!flowScopeDetails.getFlowModeId().replaceAll("-", "_").equalsIgnoreCase(eServiceApplication.getApplicationType())){
    		throw new SPException("Wrong application type loaded for "+flowScopeDetails.getFlowModeId());
		}
    	return eServiceApplication.getApplicationFilingNumber();
    }
    
	@Override
	public ESFlowBean loadApplicationLocally(String provisionalId) {
		EServiceApplication eServiceApplication = (EServiceApplication) applicationServiceInterface.retrieveApplication(office, ESERVICES_MODULE, EXPORT_FORMAT, provisionalId);
		if(!formUtil.getType().equals(eServiceApplication.getApplicationType())){
			throw new SPException("Incompatible form type, found: " + eServiceApplication.getApplicationType());
		}
		//TODO don't load fees
		ESFlowBean eSFlowBean = flowBeanFactory.convertFrom(eServiceApplication);
		if(eSFlowBean.getTmsList() != null && eSFlowBean.getTmsList().size() > 0){
			eSFlowBean.getTmsList().get(0).setImported(true);
		}
		if(eSFlowBean.getDssList() != null){
			for(ESDesignDetailsForm ds: eSFlowBean.getDssList()){
				ds.setImported(true);
			}
		}
		if(eSFlowBean.getPatentsList() != null && eSFlowBean.getPatentsList().size() > 0){
			eSFlowBean.getPatentsList().get(0).setImported(true);
		}
		String applicationType = flowScopeDetails.getFlowModeId();
    	if( "tm-opposition".equalsIgnoreCase(applicationType) || "tm-invalidity".equalsIgnoreCase(applicationType) ||
			"tm-revocation".equalsIgnoreCase(applicationType) || "tm-objection".equalsIgnoreCase(applicationType)){
    		if (eSFlowBean.getTmsList()!= null && eSFlowBean.getTmsList().size()>0){
    			TMDetailsForm tm = eSFlowBean.getTmsList().get(0);
				eSFlowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(tm, applicationType));
				groundsUtil.refreshOppositionBasisList(eSFlowBean);
    		}
    	}
		if("ds-invalidity".equalsIgnoreCase(applicationType)){
			eSFlowBean.refreshRelativeGroundsNoFilter(configurationServiceDelegator, applicationType, legalActVersionFactory);
            for(OppositionBasisForm oppo: eSFlowBean.getObsList()){
                if(oppo != null && oppo.getRelativeGrounds() != null
                        && oppo.getRelativeGrounds().getEarlierEntitlementRightInf() != null
                        && oppo.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails() != null
                        ){
                    if(oppo.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails() != null){
                        oppo.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails().setImported(true);
                    }
                    if(oppo.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns() != null){
                        for(ESDesignDetailsForm des: oppo.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns()){
                            des.setImported(true);
                        }
                    }

                }
            }
		}
		if("pt-invalidity".equalsIgnoreCase(applicationType) || "spc-invalidity".equalsIgnoreCase(applicationType) || "um-invalidity".equalsIgnoreCase(applicationType)){
			if(eSFlowBean.getPatentsList() != null && eSFlowBean.getPatentsList().size()>0){
				ESPatentDetailsForm pt = eSFlowBean.getPatentsList().get(0);
				eSFlowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(pt, applicationType));
				groundsUtil.refreshOppositionBasisList(eSFlowBean);
			}
		}
		return eSFlowBean;
	}
	
	@Override
	public ESFlowBean loadApplication(String draftId, boolean finalDraft) {
		User user = AuthenticationUtil.getAuthenticatedUser();
		String username=null;
		if (user!=null){
			username=user.getUsername();
		}
		EServiceApplication eServiceApplication = (EServiceApplication) applicationServiceInterface.loadApplication(office, ESERVICES_MODULE, username, formUtil.getType(), draftId);	
		//TODO don't load fees
		ESFlowBean eSFlowBean = flowBeanFactory.convertFrom(eServiceApplication); 
		String applicationType = flowScopeDetails.getFlowModeId();
    	if( "tm-opposition".equalsIgnoreCase(applicationType) || "tm-invalidity".equalsIgnoreCase(applicationType) || "tm-revocation".equalsIgnoreCase(applicationType)){
    		if (eSFlowBean.getTmsList()!= null && eSFlowBean.getTmsList().size()>0){
    			TMDetailsForm tm = eSFlowBean.getTmsList().get(0);
				eSFlowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(tm, applicationType));
				groundsUtil.refreshOppositionBasisList(eSFlowBean);
    		}
    	}
    	return eSFlowBean;
	}
    
    @Override
    public void saveApplication(ESFlowBean flowBean, boolean finalDraft) {
    	User user = AuthenticationUtil.getAuthenticatedUser();
    	if (user != null) {
    		IPApplication eServiceApplication = flowBeanFactory.convertTo(flowBean);	    
	    	applicationServiceInterface.persistApplication(office, ESERVICES_MODULE, SAVE_PORTAL_FORMAT, eServiceApplication);
	    	Result result = applicationServiceInterface.saveApplication(office, ESERVICES_MODULE, formUtil.getType(), user.getUsername(), eServiceApplication.getApplicationFilingNumber(), finalDraft);
	    	
	    	if(!result.getResult().equals(Result.SUCCESS)){
	    		throw new SPException(result.getErrorCode());
	    	}
    	}
    }
    
    @Override
    public String storeSubmittedApplication(ESFlowBean flowBean) {
    	
    	String documentId = fileApplication(flowBean);
    	
    	//save data that we need on the review page
    	SubmittedForm submittedForm = new SubmittedForm(); 
        submittedForm.setProvisionalId(flowBean.getIdreserventity());
        submittedForm.setDocumentId(documentId);
        
        submittedForm.setFlowBean(flowBean);
        submittedForm.setFlowModeId(flowScopeDetails.getFlowModeId());
        submittedForm.setApplicationId((applicationServiceInterface.getDraftApplication(office, "eu.ohim.sp.core.rules." + formUtil.getModule(), flowBean.getIdreserventity())).getApplicationId());

        PaymentForm paymentForm = flowBean.getPaymentForm(); 
        if (paymentForm != null) {
			submittedForm.setPaymentMethod(paymentForm.getPaymentMethod());
			if (paymentForm.getPaymentResult() != null && 
					paymentForm.getPaymentResult().getPaymentRequest() != null) {
				submittedForm.setPaymentId(flowBean.getPaymentForm().getPaymentResult().getPaymentRequest().getPaymentRequestId());
			}
		}
        
        
        String uuid = UUID.randomUUID().toString();
        submittedMap.put(uuid, submittedForm);

        return uuid;   
    }

	@Override
	public String fileApplication(ESFlowBean flowBean) {
		//Recalculating fees in order to avoid some problems that have occurred in the past leading to incorrectly calculated taxes
		feeService.getFeesInformation((ESFlowBeanImpl)flowBean);

		EServiceApplication eServiceApplication = flowBeanFactory.convertTo(flowBean);
		String module = "eu.ohim.sp.core.rules." + formUtil.getModule();
		//TODO change module name and test file application
		eServiceApplication.setApplicationType(formUtil.getType());
		eServiceApplication.setPreSubmit(false);
		//Setting the user
		SPUser user = AuthenticationUtil.getAuthenticatedUser();
		if(user != null){
			eServiceApplication.setUser(user.getUsername());
		}

		IPApplication filedApplication = applicationServiceInterface.fileApplication(office, module, eServiceApplication);
		
		String documentId = "";
		for(AttachedDocument document : filedApplication.getDocuments()){
			if(document.getDocumentKind() != null && document.getDocument() != null && document.getDocumentKind().equals(DocumentKindDefault.APPLICATION_RECEIPT)){
				documentId = document.getDocument().getDocumentId();
				break;
			}
		}
		return documentId;
	}

	@Override
	public ErrorList validateApplication(ESFlowBean flowBean, RulesInformation rulesInformation, String flowModeId) {
		IPApplication eServiceApplication = flowBeanFactory.convertTo(flowBean);    	
		
        Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
        rulesInformation.getCustomObjects().put("sections", sections);             
		
		return applicationServiceInterface.validateApplication(ESERVICES_MODULE, eServiceApplication, rulesInformation);

	}

	@Override
	public ErrorList validateApplication(ESFlowBean flowBean,
			RulesInformation rulesInformation, String flowModeId, String stateId) {
		return null;
	}
	
	@Override
	public Boolean checkExistingApplication(String applicationType, String formName, String applicationNumber, String registrationNumber) {
		return applicationServiceInterface.checkExistingApplication(applicationType, formName, applicationNumber, registrationNumber);
	}	
	
	public boolean redirectToPortal(ESFlowBean flowBean){
		boolean submitToPortal = configurationServiceDelegator.isServiceEnabled(AvailableServices.SUBMIT_PORTAL.value());
		return submitToPortal && !flowBean.isReadOnly();
	}

	public String getRedirectUrl(String provisionalId){
    	String url = configurationServiceDelegator.getApplicationManagementUrl(ESERVICES_MODULE, "application.management.url", null);
    	return url + provisionalId;
	}

}
