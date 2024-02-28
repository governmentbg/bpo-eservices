package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Raya
 * 20.05.2020
 */
@Controller
public class RegistrationCertificateController {

    @Autowired
    private TMFlowBean flowBean;

    @RequestMapping(value = "/changeCertificateRequestedIndicator.htm", method = RequestMethod.POST)
    @ResponseBody
    public String changeCertificateRequestedIndicator(@RequestParam Boolean indicatorValue){
        flowBean.setCertificateRequestedIndicator(indicatorValue);
        return "ok";
    }

}
