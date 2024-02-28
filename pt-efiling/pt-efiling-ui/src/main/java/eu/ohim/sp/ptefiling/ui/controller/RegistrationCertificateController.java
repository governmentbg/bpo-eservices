package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationCertificateController {

    @Autowired
    private PTFlowBean flowBean;

    @RequestMapping(value = "/changeCertificateRequestedIndicator.htm", method = RequestMethod.POST)
    @ResponseBody
    public String changeCertificateRequestedIndicator(@RequestParam Boolean indicatorValue){
        flowBean.setCertificateRequestedIndicator(indicatorValue);
        return "ok";
    }

}
