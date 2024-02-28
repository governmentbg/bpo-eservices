package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.domain.ESMainForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeclarationsController {

    @Autowired
    private ESFlowBean flowBean;

    @RequestMapping(value = "changeSmallCompanyDeclaration", method = RequestMethod.POST)
    public ModelAndView changeSmallCompanyDeclaration() {
        ESMainForm mainForm = (ESMainForm)flowBean.getMainForm();
        mainForm.setSmallCompany(mainForm.getSmallCompany() == null ? true: !mainForm.getSmallCompany());
        return null;
    }

    @RequestMapping(value = "changeLicenceAvailability", method = RequestMethod.POST)
    public ModelAndView changeLicenceAvailability() {
        ESMainForm mainForm = (ESMainForm)flowBean.getMainForm();
        mainForm.setLicenceAvailability(mainForm.getLicenceAvailability() == null ? true : !mainForm.getLicenceAvailability());
        return null;
    }

}
