package eu.ohim.sp.dsefiling.ui.service.interfaces;

import java.util.List;
import java.util.Set;

import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.design.ContainsDesignsLinkForm;
import eu.ohim.sp.common.ui.form.design.ContainsLocarnoForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;


/**
 * Interface for manage the addition and removal of designs with the objects that contain them in the flowBean, like DesignerForm. 
 * @author serrajo
 */
public interface DSDesignsServiceInterface {
	
	/**
	 * 
	 * @param flowBean
	 */
	void addViewsNumber(DSFlowBean flowBean);

	/**
	 * Add a new design to all the lists that contain them in the flowBean.
	 * @param design The new design to add.
	 * @param flowBean The domain object.
	 */
	void addDesignToLists(DesignForm design, DSFlowBean flowBean);
	/**
	 * Add a design to all the linked lists that contain them in the flowBean.
	 * @param designForm The design to add.
	 * @param flowBean The domain object.
	 */
	void addDesignToLinkedLists(DesignForm designForm, DSFlowBean flowBean);
	/**
	 * 
	 * @param designerForm
	 * @param flowBean
	 */
	void addDesignerLinkedListToDesignersNotLinkedList(DesignerForm designerForm, DSFlowBean flowBean);
	/**
	 * 
	 * @param flowBean
	 * @return
	 */
	boolean isThereADesignerThatWaiver(DSFlowBean flowBean);
	/**
	 * 
	 * @param designForm
	 * @param flowBean
	 * @return
	 */
	boolean isDesignLinkedInSomeForm(DesignForm designForm, DSFlowBean flowBean);
	/**
	 * 
	 * @param designForm
	 * @param forms
	 * @return
	 */
	boolean isDesignLinkedInSomeFormInTheList(DesignForm designForm, List<? extends ContainsDesignsLinkForm> forms);
	/**
	 * 
	 * @param design
	 * @param flowBean
	 */
	void replaceDesignInLists(DesignForm design, DSFlowBean flowBean);
	/**
	 * Remove a design from all the lists that contain it.
	 * @param design The design to remove.
	 * @param flowBean The domain object.
	 */
	void removeDesignFromLists(DesignForm design, DSFlowBean flowBean);
	/**
	 * Fill the form lists of linked and not linked designs. 
	 * @param flowBean The domain object that contains the designs.
	 * @param form the form to fill.
	 */
	void fillDesignsListsFromToLink(DSFlowBean flowBean, ContainsDesignsLinkForm form);
	/**
	 * 
	 * @param flowBean
	 * @param form
	 */
	void fillDesignerDesignsListsFromToLink(DSFlowBean flowBean, DesignerForm designerForm);
	/**
	 * 
	 * @param allDesigners
	 * @return
	 */
	DesignerForm getDesignerWhoWaives(List<DesignerForm> allDesigners);
	/**
	 * Fill the locarno list in the flow from the locarno list in the form. 
	 * @param form The form.
	 * @param flowBean Domain object.
	 */
	void addFormLocarnoToFlow(ContainsLocarnoForm form, DSFlowBean flowBean);
	/**
	 * Fill the locarno list in the form from the locarno list in the flow. 
	 * @param flowBean Domain object.
 	 * @param form The form.
	 */
	void addFlowLocarnoToForm(DSFlowBean flowBean, ContainsLocarnoForm form);
	/**
	 * Clear the locarno list from the flow.
	 * @param flowBean Domain object.
	 */
	void clearLocarnoFlow(DSFlowBean flowBean);
	/**
	 * Clear the locarno list from the form.
	 * @param form The form.
	 */
	void clearLocarnoForm(ContainsLocarnoForm form);
	 /**
     * Gets a design by its id.
     * @param id design id
     * @return the design
     */
    DesignForm getDesign(DSFlowBean flowBean, String id);
	/**
	 * Fill the views list in the flow from the views list in the design form. 
	 * @param form The design form.
	 * @param flowBean Domain object.
	 */
	void addDesignViewsToFlow(DesignForm designForm, DSFlowBean flowBean);
	/**
	 * Fill the view list in the design form from the view list in the flow. 
	 * @param flowBean Domain object.
 	 * @param form The design form.
	 */
	void addFlowViewsToDesign(DSFlowBean flowBean, DesignForm designForm);
	/**
	 * Clear the view list from the flow.
	 * @param flowBean Domain object.
	 */
	void clearViewsFlow(DSFlowBean flowBean);
	/**
	 * Clear the view list from the design form.
	 * @param form The design form.
	 */
	void clearViewsDesignForm(DesignForm designForm);
	/**
	 * 
	 * @param module
	 * @param designForm
	 * @param rules
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
    ErrorList validateDesign(String module, DesignForm designForm, RulesInformation rules, Errors errors, String flowModeId);	
    /**
     * 
     * @param module
     * @param designViewForm
     * @param rules
     * @param errors
     * @param flowModeId
     * @return
     */
    ErrorList validateDesignView(String module, DesignViewForm designViewForm, RulesInformation rules, Errors errors, String flowModeId);	
   
    /**
     *     
    * @param module
    * @param dsExhPriorityForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */    
    ErrorList validateExhPriority(String module, DSExhPriorityForm dsExhPriorityForm, RulesInformation rules, Errors errors, String flowModeId);	
    
    /**
     *     
    * @param module
    * @param dsPriorityForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */    
    ErrorList validatePriority(String module, DSPriorityForm dsPriorityForm, RulesInformation rules, Errors errors, String flowModeId);	   
    
    /**
     *     
    * @param module
    * @param applicationCAForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */    
    ErrorList validateApplicationCA(String module, ApplicationCAForm applicationCAForm, RulesInformation rules, Errors errors, String flowModeId);	
    
    /**
     *     
    * @param module
    * @param dsDivisionalApplicationForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */    
    ErrorList validateDivisionalApplication(String module, DSDivisionalApplicationForm dsDivisionalApplicationForm, RulesInformation rules, Errors errors, String flowModeId);	     
    
    /**
     *     
    * @param module
    * @param locarnoForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */      
    ErrorList validateLocarno(String module, LocarnoForm locarnoForm, RulesInformation rules, Errors errors, String flowModeId) ;

    /**
     *     
    * @param module
    * @param locarnoSearchForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */      
   ErrorList validateLocarnoSearch(String module, LocarnoSearchForm locarnoSearchForm, RulesInformation rules, Errors errors, String flowModeId) ;

   /**
    *     
   * @param module
   * @param locarnoComplexForm
   * @param rules
   * @param errors
   * @param flowModeId
   * @return
   */      
  ErrorList validateLocarnoComplex(String module, LocarnoComplexForm locarnoComplexForm, RulesInformation rules, Errors errors, String flowModeId) ;

   /**
    * 
    * @param module
    * @param storedFile
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */
   ErrorList validateStoredFile(String module, StoredFile storedFile, RulesInformation rules, Errors errors, String flowModeId);
   
    /**
     * 
     * @param office
     * @param designId
     * @return
     */
	DesignForm getDesign(String designId);
	
	/**
	 * 
	 * @param designId
	 * @param lang
	 * @param flowBean
	 */
	DSFlowBean getDesignApplication(String designId);

	Set<String> getDesignApplicationsForDesignId(String designId);
	
	/**
	 * 
	 * @param text
	 * @param numberOfRows
	 * @return
	 */
	String getDesignAutocomplete(String text, int numberOfRows);
	
	/**
	 * 
	 * @param country
	 * @param text
	 * @param numberOfRows
	 * @return
	 */
	String getDesignAutocomplete(String country, String text, int numberOfRows);
}
