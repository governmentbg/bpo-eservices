package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.DraftSignStatus;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignXmlController {
    @Autowired
    private ApplicationService applicationService;
    @RequestMapping(value = "signXml", method = RequestMethod.GET)
    @ResponseBody
    public String signXml(@RequestParam("provisionalId") String provisionalId) {
        DraftApplication draftApplication = applicationService.getDraftApplication("BG", "tmefiling", provisionalId);
        DraftSignStatus ss = new DraftSignStatus(2l);
//        draftApplication.setCurrentStatus(null);
        draftApplication.setCurrentSignStatus(ss);
        applicationService.updateDraftApplication(draftApplication);
        return "done...";
    }
}
