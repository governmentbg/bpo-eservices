/*******************************************************************************
 * * $$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.flow.section;

import java.util.List;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.design.DesignForm;

public interface DesignFlowBean extends FlowBean {


    /**
     * Clear the designs
     */
    void clearDesigns();

    /**
     * Retrieves all the stored designs
     *
     * @return All the stored designs
     */
    List<DesignForm> getDesigns();

    /**
     * Method that sets the designs
     *
     * @param designs the designs to set
     */
    void setDesigns(List<DesignForm> designs);

    /**
     * Get the design count
     *
     * @return the design count
     */
    Integer getDesignCount();

}
