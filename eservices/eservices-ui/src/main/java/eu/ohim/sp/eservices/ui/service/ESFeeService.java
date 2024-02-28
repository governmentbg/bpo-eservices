package eu.ohim.sp.eservices.ui.service;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.adapter.patent.ESPatentFactory;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.adapter.LimitedTrademarkFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.service.FeeService;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.FormUtil.FormMainType;

@Service(value="feeService")
public class ESFeeService extends FeeService<ESFlowBean>{
	
	@Autowired
	private FormUtil formUtil;
	
	@Autowired
    private LimitedTrademarkFactory limitedTrademarkFactory;
	
	@Autowired
	private ESDesignFactory designFactory;

	@Autowired
	private ESPatentFactory esPatentFactory;

    @Autowired
    private ESFlowBeanFactory flowBeanFactory;
	

	@Override
	protected String getModule() {
		return formUtil.getModule();
	}

	@Override
	protected List<Object> getFeesInputData(ESFlowBean flowBean) {
		List<Object> inputData = new ArrayList<Object>();

		if(formUtil.getMainType() == FormMainType.TM){
			for(TMDetailsForm form: flowBean.getTmsList()){
				inputData.add(limitedTrademarkFactory.convertTo(form));
			}
		}else if(formUtil.getMainType() == FormMainType.DS){
			for(ESDesignDetailsForm form: flowBean.getDssList()){
				inputData.add(designFactory.convertTo(form));
			}
		}else if(formUtil.getMainType() == FormMainType.PT || formUtil.getMainType() == FormMainType.UM || formUtil.getMainType() == FormMainType.EP){
			for(ESPatentDetailsForm form: flowBean.getPatentsList()){
				inputData.add(esPatentFactory.convertTo(form));
			}
		}

		inputData.add(flowBeanFactory.convertTo(flowBean));
			
		return inputData;
	}

	
}
