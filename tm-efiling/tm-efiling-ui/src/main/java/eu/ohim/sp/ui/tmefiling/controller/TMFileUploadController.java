package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TMFileUploadController {

    @Autowired
    private TMFlowBean flowBean;

    @RequestMapping(value = "changeDocumentTrueIndicator", method = RequestMethod.POST)
    public ModelAndView changeDocumentTrueIndicator() {
        ModelAndView modelAndView = new ModelAndView("errors/alertErrors");

        modelAndView.addObject("alertMessage", "otherAttachments.declare.true.message");

        if(flowBean.getTrueDocumentsIndicator() == null || flowBean.getTrueDocumentsIndicator() == false){
            flowBean.setTrueDocumentsIndicator(true);
        } else {
            flowBean.setTrueDocumentsIndicator(false);
        }

        return modelAndView;
    }
}
