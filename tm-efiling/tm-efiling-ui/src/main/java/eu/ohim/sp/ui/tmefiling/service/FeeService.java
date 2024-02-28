package eu.ohim.sp.ui.tmefiling.service;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karalch
 */
@Service
public class FeeService extends eu.ohim.sp.common.ui.service.FeeService {

    @Autowired
    FlowBeanFactory flowBeanFactory;

    @Autowired
    private FormUtil formUtil;

    @Override
    protected String getModule() {
        return formUtil.getFeeModule();
    }

    @Override
    protected List<Object> getFeesInputData(FlowBean flowBean) {
        List<Object> inputData = new ArrayList<Object>();

        inputData.add(flowBeanFactory.convertTo((FlowBeanImpl) flowBean));
        inputData.add(new AbstractMap.SimpleEntry<>("certificateRequestedIndicator", ((FlowBeanImpl) flowBean).getCertificateRequestedIndicator()));

        return inputData;
    }
}
