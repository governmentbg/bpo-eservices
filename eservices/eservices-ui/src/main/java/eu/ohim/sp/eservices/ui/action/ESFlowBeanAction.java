package eu.ohim.sp.eservices.ui.action;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.userdoc.FilteredUserdocsForm;
import eu.ohim.sp.common.ui.form.userdoc.UserdocForm;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.service.interfaces.BackOfficeServiceInterface;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains actions invoked during webflow lifecycle, for example on-start.
 */
@Service(value = "flowBeanAction")
public class ESFlowBeanAction {
    private static final Logger LOGGER = Logger.getLogger(ESFlowBeanAction.class);

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    protected RepresentativeFactory representativeFactory;

    @Autowired
    private ESApplicationServiceInterface draftApplicationService;

    @Autowired
    private BackOfficeServiceInterface backOfficeService;

    public ESFlowBean loadApplication(String formId, String lid, FlowScopeDetails flowScopeSession) {
        String temp = flowScopeSession.getLid();
        ESFlowBean esFlowBean = new ESFlowBeanImpl();
        if(formId == null || lid == null) {
            return esFlowBean;
        } else if(lid.equals(temp)) {
            return draftApplicationService.loadApplicationLocally(formId);
        }
        return esFlowBean;
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
            return personService.addUserPersonDetails(flowBean, flowModeId);
        } catch (Exception e) {
            LOGGER.warn("Error obtaining user person details", e);
            return null;
        }
    }

    public void fillInUserdocs(FlowBean flowBean){
        ESFlowBean esFlowBean = (ESFlowBean) flowBean;

        if(!esFlowBean.getObjectIsNational()){
            clearUserdocRelatedInformation(esFlowBean);
            return;
        }

        List<UserdocForm> currentUserdocsList = esFlowBean.getFetchedUserdocs();
        try {
            FilteredUserdocsForm filteredUserdocsForm = backOfficeService.getUserdocsForApplication(esFlowBean);
            esFlowBean.setUserdocMainObject(filteredUserdocsForm.getMainObject());

            if(currentUserdocsList.size() == filteredUserdocsForm.getUserdocFormList().size()
                    && currentUserdocsList.containsAll(filteredUserdocsForm.getUserdocFormList())
                    && filteredUserdocsForm.getUserdocFormList().containsAll(currentUserdocsList)){
                if(filteredUserdocsForm.getUserdocFormList().size() == 0){
                    esFlowBean.setSelectedUserdoc(null);
                }
            } else {
                esFlowBean.getFetchedUserdocs().clear();
                esFlowBean.setSelectedUserdoc(null);
                esFlowBean.setRelateRequestToObject(null);
                esFlowBean.addAll(filteredUserdocsForm.getUserdocFormList());
            }
            esFlowBean.setFetchedUserdocsError(false);
            esFlowBean.setUserdocRelationRestriction(filteredUserdocsForm.getUserdocRelationRestriction());

            if(filteredUserdocsForm.getUserdocRelationRestriction().equals("OBJECT_ONLY") ||
                    (filteredUserdocsForm.getUserdocRelationRestriction().equals("USERDOC_RELATION_OPTIONAL") && filteredUserdocsForm.getUserdocFormList().size() == 0)) {
                esFlowBean.setRelateRequestToObject(true);
                esFlowBean.setSelectedUserdoc(null);
            }
        } catch (Exception e){
            clearUserdocRelatedInformation(esFlowBean);
            esFlowBean.setFetchedUserdocsError(true);
        }
    }

    private void clearUserdocRelatedInformation(ESFlowBean esFlowBean){
        esFlowBean.getFetchedUserdocs().clear();
        esFlowBean.setUserdocRelationRestriction(null);
        esFlowBean.setSelectedUserdoc(null);
        esFlowBean.setRelateRequestToObject(null);
        esFlowBean.setUserdocMainObject(null);
    }

    public ESFlowBean changeGenericApplicationType(ESFlowBean flowBean){
        flowBean.getOtherAttachments().getStoredFiles().clear();
        flowBean.getOtherAttachments().setAttachment(false);
        return flowBean;
    }

    public void setUserRepresentatives(ESFlowBean flowBean, String flowModeId){
        UserPersonDetails userDetails = personService.getUserPersonDetails(flowModeId);
        if(userDetails != null && userDetails.getRepresentatives() != null && userDetails.getRepresentatives().size() > 0) {
            flowBean.setUserRepresentativesForm(Arrays.asList(representativeFactory.convertFrom(userDetails.getRepresentatives().get(0))));
        } else {
            flowBean.setUserRepresentativesForm(new ArrayList<>());
        }
    }

}
