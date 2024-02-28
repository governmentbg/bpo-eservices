package eu.ohim.sp.dsefiling.ui.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.FastTrackServiceInterface;
import eu.ohim.sp.common.ui.service.interfaces.FeeServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.domain.fasttrack.FastTrackFail;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.application.SubmittedForm;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSApplicationServiceInterface;
import org.springframework.webflow.execution.RequestContextHolder;


@Service(value="draftApplicationService")
public class DSDraftApplicationService implements DSApplicationServiceInterface {

	private static final Logger LOGGER = Logger.getLogger(DSDraftApplicationService.class);

	@Value("${sp.office}")
	private String office;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;

	@Autowired
	private DSFlowBeanFactory flowBeanFactory;

	@Autowired
	private SubmittedMap submittedMap;

	@Autowired
	private FeeServiceInterface feeService;

	@Autowired
	private ImportServiceInterface importServiceInterface;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired(required=false)
	private FlowScopeDetails flowScopeDetails;

	private static final String MODULE = "dsefiling";
	private static final String APPLICATION_TYPE = "DS";
	private static final FormatXML EXPORT_FORMAT = FormatXML.APPLICATION_XML;

	@Autowired
	private FastTrackServiceInterface fastTrackService;

	@Override
	public String provideProvisionalID() {
		DraftApplication draft = applicationService.initDraftApplication(office, MODULE, APPLICATION_TYPE);
		return draft.getProvisionalId();
	}

	@Override
	public byte[] saveApplicationLocally(DSFlowBean flowBean) {

		DesignApplication application = flowBeanFactory.convertTo(flowBean);

		applicationService.persistApplication(office, MODULE, EXPORT_FORMAT, application);
		byte[] b = applicationService.exportApplication(office, MODULE, EXPORT_FORMAT,
			application.getApplicationFilingNumber());

		return b;
	}

	@Override
	public void saveApplication(DSFlowBean flowBean, boolean finalDraft) {
		// Nothing to do yet.
	}

	@Override
	public String storeSubmittedApplication(DSFlowBean flowBean) {
		String receiptId = fileApplication(flowBean);

		SubmittedForm submittedForm = new SubmittedForm();
		submittedForm.setProvisionalId(flowBean.getIdreserventity());
		submittedForm.setDocumentId(receiptId);
		submittedForm.setFlowBean(flowBean);

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
	public String loadApplicationLocally(byte[] data) {
		DesignApplication designApplication =
			(DesignApplication) applicationService.importApplication(office, MODULE, data, APPLICATION_TYPE, EXPORT_FORMAT);
		if (designApplication != null) {
			return designApplication.getApplicationFilingNumber();
		} else {
			throw new SPException("Xml content not valid");
		}
	}

	@Override
	public DSFlowBean loadApplicationLocally(String provisionalId) {
		DesignApplication designApplication =
			(DesignApplication) applicationService.retrieveApplication(office, MODULE, EXPORT_FORMAT, provisionalId);
		DSFlowBean flowBean = flowBeanFactory.convertFrom(designApplication);
		Collection<FastTrackFail> fastTrackFails = fastTrackService.calculateFastTrackFails(flowBean, getFlowModeId(flowScopeDetails), null);
		flowBean.setFastTrack(fastTrackFails == null || fastTrackFails.isEmpty());

		if (flowBean.getDesigns() != null) {
			for (DesignForm designForm : flowBean.getDesigns()) {
				if (designForm.getLocarno() != null) {
					for (LocarnoAbstractForm form : designForm.getLocarno()) {
						importServiceInterface.validateLocarnoForm(flowBean.getFirstLang(), form);
					}
				}
			}
		}
		return flowBean;
	}

	@Override
	public DSFlowBean loadApplication(String draftId, boolean finalDraft) {
		// Nothing to do yet.
		return null;
	}

	@Override
	public String fileApplication(DSFlowBean flowBean) {
		//Recalculating fees in order to avoid some problems that have occurred in the past leading to incorrectly calculated taxes
		DSFlowBeanImpl dsFlowBeanImpl = (DSFlowBeanImpl) flowBean;
		feeService.getFeesInformation(dsFlowBeanImpl);
		Collection<FastTrackFail> fastTrackFails = fastTrackService.calculateFastTrackFails(dsFlowBeanImpl, getFlowModeId(flowScopeDetails), null);
		dsFlowBeanImpl.setFastTrack(fastTrackFails == null || fastTrackFails.isEmpty());

		LOGGER.info("***************** Entered fileApplication");
		SPUser user = AuthenticationUtil.getAuthenticatedUser();
		DesignApplication designApplication = flowBeanFactory.convertTo(flowBean);
		if (user != null) {
			designApplication.setUser(user.getUsername());
		}
		LOGGER.info("***************** Converted to core designApplication");
		designApplication.setApplicationType(APPLICATION_TYPE);
		LOGGER.info("***************** Set application type");
		DesignApplication filedDesignApplication =
			(DesignApplication) applicationService.fileApplication(office, MODULE, designApplication);
		LOGGER.info("***************** Successfully ran through applicationService.fileApplication");
		LOGGER.info("***************** Preparing to return document id...");

		String receiptId = "";
		for (AttachedDocument attachedDocument : filedDesignApplication.getDocuments()) {
			if (DocumentKindDefault.APPLICATION_RECEIPT.equals(attachedDocument.getDocumentKind())) {
				receiptId = attachedDocument.getDocument().getDocumentId();
				break;
			}
		}
		return receiptId;

	}

	@Override
	public ErrorList validateApplication(DSFlowBean flowBean, RulesInformation rulesInformation, String flowModeId) {
		DesignApplication designApplication = flowBeanFactory.convertTo(flowBean);

		Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
		rulesInformation.getCustomObjects().put("sections", sections);
		return applicationService.validateApplication(MODULE, designApplication, rulesInformation);
	}

	@Override
	public ErrorList validateApplication(DSFlowBean flowBean, RulesInformation rulesInformation, String flowModeId, String stateId) {
		DesignApplication designApplication = flowBeanFactory.convertTo(flowBean);

		List<Section> sections = sectionViewConfiguration.getSortedSections(flowModeId, stateId);
		rulesInformation.getCustomObjects().put("sections", sections);
		return applicationService.validateApplication(MODULE, designApplication, rulesInformation);
	}

	public boolean redirectToPortal(DSFlowBean flowBean) {
		boolean submitToPortal = configurationServiceDelegator.isServiceEnabled(AvailableServices.SUBMIT_PORTAL.value());
		return submitToPortal;
	}

	public String getRedirectUrl(String provisionalId) {
		String url = configurationServiceDelegator.getApplicationManagementUrl(MODULE, "application.management.url", null);
		return url + provisionalId;
	}

	private String getFlowModeId(FlowScopeDetails flowScopeDetails) {
		String flowModeId = flowScopeDetails != null ? flowScopeDetails.getFlowModeId() : null;
		if (flowModeId == null && RequestContextHolder.getRequestContext() != null && RequestContextHolder.getRequestContext().getActiveFlow() != null) {
			flowModeId = RequestContextHolder.getRequestContext().getActiveFlow().getId();
		}
		return flowModeId;
	}
}