package eu.ohim.sp.dsefiling.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;



/**
 * Interface for manage the import of designs and design application. 
 * @author serrajo
 */
public interface DSImportDesignsServiceInterface {
	
	/**
	 * 
	 * @param designId
	 * @param flowBean
	 * @return
	 */
	DesignForm importDesign(String designId, DSFlowBean flowBean);
	
	/**
	 * 
	 * @param design
	 * @param flowBean
	 * @return
	 */
	DesignForm importDesign(Design design, DSFlowBean flowBean);
	
	/**
	 * 
	 * @param designId
	 * @param flowBean
	 */
	void importDesignApplication(String designId, String applicationId, DSFlowBean flowBean);

    public DSPriorityForm importPriority(String  designId, String office, DSFlowBean flowBean);
	
}
