package eu.ohim.sp.common.ui.flow.section;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.payment.FeesForm;

public interface FeesFlowBean extends FlowBean{
	
    /**
     * Gets fees form
     * 
     * @return fees form
     */
    FeesForm getFeesForm();

    /**
     * 
     * @param feesForm
     */
    void setFeesForm(FeesForm feesForm);
    
    /**
     * Gets number of classes
     * 
     * @return int classes number
     */
    int getNumberofClasses();
}
