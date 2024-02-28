package eu.ohim.sp.ui.tmefiling.service;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.RequestContextHolder;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 04.05.2022
 * Time: 16:43
 */
@Service
public class FormUtil {

    @Autowired(required=false)
    private FlowScopeDetails flowScopeDetails;

    public String getType(){
        String flowMode = getFlowModeId();
        if(flowMode.equals("wizard")){
            return "TM";
        } else {
            return flowMode.replaceAll("-", "_").toUpperCase();
        }
    }

    public String getModule(){
        String flowMode = getFlowModeId();
        if(flowMode.equals("wizard")){
            return "tmefiling";
        } else {
            return flowMode.replaceAll("-", "");
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

    private String getFlowModeId(){
        if(flowScopeDetails != null){
            return flowScopeDetails.getFlowModeId();
        } else{
            return RequestContextHolder.getRequestContext().getActiveFlow().getId();
        }
    }
}
