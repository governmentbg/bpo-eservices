package eu.ohim.sp.eservices.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.RequestContextHolder;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;

@Service
public class FormUtil {

	@Autowired(required=false)
	private FlowScopeDetails flowScopeDetails;
	
	/**
	 * Distinguish if we are working with designs or trademarks or patents
	 */
	public enum FormMainType{TM, DS, PT, UM, EP, SV, SPC, OL}
	
	
	/**
	 * ApplicationType
	 */
	public String getType(){
		String flowMode = getFlowModeId();
		return flowMode.replaceAll("-", "_").toUpperCase();
	}
	
	/**
	 * Trademarks or Designs 
	 */
	public FormMainType getMainType(){
		String module = getModule().toUpperCase();
		if(module.startsWith("TM")){
			return FormMainType.TM;
		}else if(module.startsWith("DS")){
			return FormMainType.DS;
		} else if(module.startsWith("PT")){
			return FormMainType.PT;
		} else if(module.startsWith("UM")){
			return FormMainType.UM;
		}else if(module.startsWith("EP")){
			return FormMainType.EP;
		}else if(module.startsWith("SV")){
			return FormMainType.SV;
		} else if(module.startsWith("SPC")){
			return FormMainType.SPC;
		} else if(module.startsWith("OL")){
			return FormMainType.OL;
		} else {
			throw new UnsupportedOperationException("could not resolve form main type");
		}
	}

	/**
	 * Module used for configuration services
	 */
	public String getModule(){
		String flowMode = getFlowModeId();
		return flowMode.replaceAll("-", "");
	}
	
	private String getFlowModeId(){
		if(flowScopeDetails != null){
			return flowScopeDetails.getFlowModeId();
		}else{
			return RequestContextHolder.getRequestContext().getActiveFlow().getId();
		}
	}
}
