package eu.ohim.sp.common.ui.flow.section;

import java.util.List;



import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.claim.ConversionForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;

public interface ClaimFlowBean extends FlowBean{
	 /**
     * Retrieves all the stored priorities
     * 
     * @return All the stored priorities
     */
    List<PriorityForm> getPriorities();

    /**
     * Retrieves all the stored seniorities
     * 
     * @return All the stored seniorities
     */
    List<SeniorityForm> getSeniorities();

    /**
     * Clear the seniorities
     */
    void clearSeniorities();

    /**
     * Clear the priorities
     */
    void clearPriorities();

    /**
     * Clear the exhibition priorities
     */
    void clearExhibitions();

    /**
     * Clear the transformations
     */
    void clearTransformations();
    
    /**
     * Clear the conversions
     */
    void clearConversions();
    
    /**
     * Retrieve exhibition priorities
     * 
     * @return exhibition priority forms list
     */
    List<ExhPriorityForm> getExhpriorities();

    /**
     * Retrieve transformations
     * 
     * @return transformation forms list
     */
    List<TransformationForm> getTransformations();
    
    /**
     * Retrieve conversions
     * 
     * @return conversion forms list
     */
    List<ConversionForm> getConversions();

    
}
