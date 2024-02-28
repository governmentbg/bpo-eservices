package eu.ohim.sp.dsefiling.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.FeeService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

@Service(value="feeService")
public class DSFeeService extends FeeService<DSFlowBean> {

	@Override
	protected String getModule() {
		return "dsefiling";
	}
	
	@Autowired(required=false)
	private FlowScopeDetails flowScopeDetails;	

	@Autowired
    private DSFlowBeanFactory dsFlowBeanFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.ohim.sp.common.ui.service.FeeServiceInterface#updateFeesInformation
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateFeesInformation(CommonSpFlowBean commonFspFlowBean) {
		if (commonFspFlowBean.getFeesForm() == null) {
			getFeesInformation(commonFspFlowBean);
		}
	}

	@Override
	protected List<Object> getFeesInputData(DSFlowBean flowBean) {
		
		List<Object> inputData = new ArrayList<Object>();
		String flowMode = flowScopeDetails.getFlowModeId();
		
		if(flowMode.equals("wizard") || flowMode.equals("oneform")){			
			inputData.add(dsFlowBeanFactory.convertTo(flowBean));
		}
		
		return inputData;
	}
}
