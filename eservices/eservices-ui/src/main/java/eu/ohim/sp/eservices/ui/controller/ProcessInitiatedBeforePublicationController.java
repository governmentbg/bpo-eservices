package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Raya
 * 29.05.2020
 */
@Controller
public class ProcessInitiatedBeforePublicationController {

    @Autowired
    private ESFlowBean esFlowBean;

    @RequestMapping(value = "/processInitiatedBeforePublicationChange.htm", method = RequestMethod.POST)
    @ResponseBody
    public String processInitiatedBeforePublicationChange(@RequestParam boolean processInitiatedValue ){
        esFlowBean.setProcessInitiatedBeforePublication(processInitiatedValue);
        return "ok";
    }
}
