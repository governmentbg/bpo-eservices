package eu.ohim.sp.eservices.ui.util;

import java.util.List;

import org.apache.log4j.Logger;

import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

public class AutomaticImportUtils {
	private static final Logger logger = Logger.getLogger(AutomaticImportUtils.class);

	public static void copyOwnersToApplicants(List<ApplicantForm> owners, ESFlowBean flowBean, PersonServiceInterface personService, FlowScopeDetails flowScopeDetails){
		if(owners == null) {
			return;
		}
		for(ApplicantForm own: owners) {
			try {
				if(own.getImported() == true && !flowBean.existsObject(ApplicantForm.class, own.getId())){
					ApplicantForm imported;
					if(flowBean.getUnpublishedAppImported()){
						imported = own.clone();
					} else {
						imported = personService.importApplicant(own.getId(), flowScopeDetails.getFlowModeId());
					}
					if(imported != null ) {
						flowBean.addObject(imported);
					}
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}

	}

    public static void copyApplicants(String st13, ESFlowBean flowBean, PersonServiceInterface personService, FlowScopeDetails flowScopeDetails) {
        try {
            ApplicantForm imported = personService.importApplicant(st13, flowScopeDetails.getFlowModeId());
            flowBean.addObject(imported);
        } catch (Exception e) {
            logger.error(e);
        }
    }

	public static void copyOwnersToHolders(List<ApplicantForm> owners, ESFlowBean flowBean, FlowScopeDetails flowScopeDetails, PersonServiceInterface personService,
										   HolderFactory holderFactory) {
		if(owners == null) {
			return;
		}
		for(ApplicantForm own: owners) {
			try {
				if(own.getImported() == true && !flowBean.existsObject(HolderForm.class, own.getId())){
					ApplicantForm imported;
					if(flowBean.getUnpublishedAppImported()){
						imported = own.clone();
					} else {
						imported = personService.importApplicant(own.getId(), flowScopeDetails.getFlowModeId());
					}
					HolderForm holder = holderFactory.convertFromApplicantForm(imported); 
					if(holder != null ) {
						flowBean.addObject(holder);
					}
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

    public static void copyHolders(String st13, ESFlowBean flowBean, PersonServiceInterface personService, FlowScopeDetails flowScopeDetails, HolderFactory holderFactory) {
        try {
            ApplicantForm imported = personService.importApplicant(st13, flowScopeDetails.getFlowModeId());
            HolderForm holder = holderFactory.convertFromApplicantForm(imported);
            flowBean.addObject(holder);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void copyRepresentatives(String st13, ESFlowBean flowBean, PersonServiceInterface personService, FlowScopeDetails flowScopeDetails){
        try {
            RepresentativeForm imported = personService.importRepresentative(st13, flowScopeDetails.getFlowModeId());
            flowBean.addObject(imported);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void copyAllRepresentatives(List<RepresentativeForm> representatives,  ESFlowBean flowBean, PersonServiceInterface personService, FlowScopeDetails flowScopeDetails){
		if(representatives == null){
			return;
		}

		for(RepresentativeForm representativeForm: representatives){
			try {
				if(representativeForm.getImported() == true && !flowBean.existsObject(RepresentativeForm.class, representativeForm.getId())){
					RepresentativeForm imported;
					if(flowBean.getUnpublishedAppImported()){
						imported = representativeForm.clone();
					} else {
						imported = personService.importRepresentative(representativeForm.getId(), flowScopeDetails.getFlowModeId());
					}
					if(imported != null ) {
						flowBean.addObject(imported);
					}
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
}
