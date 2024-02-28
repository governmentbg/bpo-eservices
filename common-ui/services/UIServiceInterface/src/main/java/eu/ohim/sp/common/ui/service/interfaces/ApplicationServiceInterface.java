package eu.ohim.sp.common.ui.service.interfaces;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;


public interface ApplicationServiceInterface<T extends FlowBean> {
    /**
     * Provides provisional ID
     *
     * @return String provisional ID
     */
    String provideProvisionalID();

    /**
     * Persists a trade mark application
     *
     * @param flowBean required data for the flowBean
     * @param finalDraft if true application cannot be modified
     */
    void saveApplication(T flowBean, boolean finalDraft);

    /**
     * Saves a trade mark application locally
     *
     * @param flowBean
     *            required data for the flowBean
     */
    byte[] saveApplicationLocally(T flowBean);



    /**
     * 1. step of loadApplicationLocally
     * Saves on the server the application located on the pc,
     * this generates a new provisionalId
     */
    String loadApplicationLocally(byte[] data);

    /**
     * 2. step of loadApplicationLocally
     * Gets a loaded application and converts it to a flowbean
     */
    T loadApplicationLocally(String provisionalId);
    
    /**
     * Loads an external application 
     * @param draftId
     * @param finalDraft if true application cannot be modified
     * @return
     */
    T loadApplication(String draftId, boolean finalDraft);


    String storeSubmittedApplication(T flowBean);

    String fileApplication(T flowBean);
	
	/**
	 * Delegation service that calls the service validate depending on the
	 * object that it receives
	 * 
     * @param flowBean
     *            required data for the flowBean
	 * @param rulesInformation
	 *            rules relevant information
	 * @param flowModeId
	 *            the mode on which the application was filed  
	 */    
	ErrorList validateApplication(T flowBean, RulesInformation rulesInformation, String flowModeId);
	
	/**
	 * Delegation service that calls the service validate depending on the
	 * object that it receives
	 * 
     * @param flowBean
     *            required data for the flowBean
	 * @param rulesInformation
	 *            rules relevant information
	 * @param flowModeId
	 *            the mode on which the application was filed 
	 * @param stateId
	 *            the state (wizard-conf) on which the application is in that moment       
	 */
    ErrorList validateApplication(T flowBean, RulesInformation rulesInformation,String flowModeId,String stateId);		
	
}
