/*******************************************************************************
 * * $Id:: FlowScopeDetails.java 49264 2012-10-29 13:23:34Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.interceptors;
import java.io.Serializable;

public class FlowScopeDetails implements Serializable {
	private String flowModeId;
	private String stateId;
    private String lid;
    private static final long serialVersionUID = 7526472295622776147L;

	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getFlowModeId() {
		return flowModeId;
	}
	public void setFlowModeId(String flowModeId) {
		this.flowModeId = flowModeId;
	}

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }
}
