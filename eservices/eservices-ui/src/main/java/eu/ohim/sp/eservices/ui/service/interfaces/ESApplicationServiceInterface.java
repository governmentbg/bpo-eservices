package eu.ohim.sp.eservices.ui.service.interfaces;

import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

public interface ESApplicationServiceInterface  extends eu.ohim.sp.common.ui.service.interfaces.ApplicationServiceInterface<ESFlowBean>{

	String loadApplicationLocally(byte[] data);

	ESFlowBean loadApplicationLocally(String provisionalId);

	String fileApplication(ESFlowBean flowBean);

	String providePaymentID(String filingNumber);
	
    /**
     * Check if application filing exist.
     * @param value
     * @param flowModeId
     * @param applicationNumber
     * @param registrationNumber
     * @return
     */
	Boolean checkExistingApplication(String value, String flowModeId,
			String applicationNumber, String registrationNumber);	

}
