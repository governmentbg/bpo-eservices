package eu.ohim.sp.ptefiling.ui.service;

import eu.ohim.sp.common.ui.service.FeeService;
import eu.ohim.sp.ptefiling.ui.adapter.PTFlowBeanFactory;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 21.11.2018
 */
@Service(value="feeService")
public class PTFeeService extends FeeService<PTFlowBean> {
    @Autowired
    private FormUtil formUtil;

    @Autowired
    private PTFlowBeanFactory ptFlowBeanFactory;


    @Override
    protected String getModule() {
        return formUtil.getFeeModule();
    }

    @Override
    protected List<Object> getFeesInputData(PTFlowBean flowBean) {
        List<Object> inputData = new ArrayList<Object>();
        inputData.add(ptFlowBeanFactory.convertTo(flowBean));

        return inputData;
    }
}
