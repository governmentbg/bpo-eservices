package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AddChangeAttachmentCountPagesController extends AddAbstractController {

    @Autowired
    private ESFlowBean flowBean;

    @RequestMapping(value = "addChangePagesCountAttachment", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void addChangePagesCountAttachment(@RequestParam(required = false) String pagesCountAttachment) {

        try {
            int pages = Integer.parseInt(pagesCountAttachment);
            flowBean.setPagesCountAttachment(pages);
        } catch (Exception nfe) {
            flowBean.setPagesCountAttachment(0);
        }
    }

}