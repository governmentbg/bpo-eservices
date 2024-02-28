package eu.ohim.sp.ptefiling.ui.service;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.RequestContextHolder;

@Service
public class FormUtil {

	@Autowired(required=false)
	private FlowScopeDetails flowScopeDetails;

	public String getType(){
		String flowMode = getFlowModeId();
		return flowMode.replaceAll("-", "_").toUpperCase();
	}
	

	public String getModule(){
		String flowMode = getFlowModeId();
		if (flowMode != null) {
			return flowMode.replaceAll("-", "");
		} else {
			return null;
		}

	}

	public String getFeeModule(){
		String mod = getModule();
		if(mod != null){
			return mod+"fee";
		} else {
			return null;
		}
	}

	
	public String getFlowModeId(){
		if(flowScopeDetails != null){
			return flowScopeDetails.getFlowModeId();
		}else{
			return RequestContextHolder.getRequestContext().getActiveFlow().getId();
		}
	}
}
