package eu.ohim.sp.common.ui.flow.section;

import java.util.List;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;

public interface TrademarkFlowBean extends FlowBean{
    /**
     * Retrieves all the similar marks
     * 
     * @return All the similar marks
     */
    List<SimilarMarkForm> getSimilarMarks();
    
    /**
     * @param similarMarks
     */
    void setSimilarMarks(List<SimilarMarkForm> similarMarks);
}
