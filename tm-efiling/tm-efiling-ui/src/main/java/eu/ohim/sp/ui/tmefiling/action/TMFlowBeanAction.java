package eu.ohim.sp.ui.tmefiling.action;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.service.constant.AppConstants;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eu.ohim.sp.common.ui.flow.section.TrademarkFlowBean;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ApplicationServiceInterface;
import java.util.List;

/**
 * Contains actions invoked during webflow lifecycle, for example on-start.
 */
@Service(value = "flowBeanAction")
public class TMFlowBeanAction {
	private static final Logger LOGGER = Logger.getLogger(TMFlowBeanAction.class);

	private PersonServiceInterface personService;
	
	private ApplicantFactory applicantFactory;

    @Autowired
    private ApplicationServiceInterface draftApplicationService;

    @Autowired
	private GoodsServicesServiceInterface goodsServicesServiceInterface;

	@Autowired
	public TMFlowBeanAction(PersonServiceInterface personService, ApplicantFactory applicantFactory) {
		this.personService = personService;
		this.applicantFactory = applicantFactory;
	}

	/**
	 * Action called when flow starts - listed in <on-starts /> for example in oneform-flow.xml
	 *
	 * Gets currently logged user data and sets it as applicant details
	 *
	 * @param flowBean - webflow scoped flow data
	 * @param flowModeId - flow mode: oneform or wizard
	 *
	 * @return added user details, or null if not found
	 */
	public UserPersonDetails addUserPersonDetails(FlowBean flowBean, String flowModeId) {
		try {
			UserPersonDetails userPersonDetails = personService.addUserPersonDetails(flowBean, flowModeId);

			// FIXME: adding applicant details seems to be already done during executing personService.addUserPersonDetails
			if (userPersonDetails != null) {
				addApplicantsDetails(flowBean, userPersonDetails.getApplicants(), flowModeId);
			}
			return userPersonDetails;
		} catch (Exception e) {
			LOGGER.warn("Error obtaining user person details", e);
			return null;
		}
	}

	private void addApplicantsDetails(FlowBean flowBean, List<Applicant> applicants, String flowModeId) {
		if (CollectionUtils.isNotEmpty(applicants)) {
			for (Applicant applicant : applicants) {
				ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);

				if (applicantForm != null) {
					personService.addPersonFormDetails(flowBean, applicantForm, flowModeId);
				}
			}
		}
	}

    public TrademarkFlowBean loadApplication(String formId, String lid, FlowScopeDetails flowScopeSession) {
        String temp = flowScopeSession.getLid();
        TrademarkFlowBean tmFlowBean = new FlowBeanImpl();
        if(formId == null || lid == null) {
            return tmFlowBean;
        } else if(lid.equals(temp)) {
			FlowBeanImpl returnedBean = (FlowBeanImpl)draftApplicationService.loadApplicationLocally(formId);
			if(returnedBean == null || returnedBean.getFirstLang() == null) {
				returnedBean.setInitializationErrorCode("import.application.xml.contentNotValid");
			}

			if(returnedBean.getGoodsAndServices() != null  && returnedBean.getGoodsAndServices().size()>0){
				returnedBean.getGoodsAndServices().forEach( e-> e.setLangId(returnedBean.getFirstLang()));
				goodsServicesServiceInterface.verifyListOfGS(returnedBean);
			}


			return returnedBean;
		}
        return tmFlowBean;
    }

	public void clearUnnecessaryGSDescriptionFields(FlowBean flowBean){
		FlowBeanImpl flowBeanImpl = (FlowBeanImpl) flowBean;
		if(flowBeanImpl.getMainForm() != null && flowBeanImpl.getMainForm().getMarkType() != null && !AppConstants.OriName.equals(flowBeanImpl.getMainForm().getMarkType())){
			flowBeanImpl.getMainForm().setGoodsFactorsDescription(null);
		}
	}
}
