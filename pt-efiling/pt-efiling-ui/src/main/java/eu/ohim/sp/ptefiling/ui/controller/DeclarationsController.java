package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya
 * 19.07.2019
 */
@Controller
public class DeclarationsController {

    @Autowired
    private PTFlowBean ptFlowBean;

    @RequestMapping(value = "changeSmallCompanyDeclaration", method = RequestMethod.POST)
    public ModelAndView changeSmallCompanyDeclaration(){
        ptFlowBean.getMainForm().setSmallCompany(!ptFlowBean.getMainForm().isSmallCompany());
        return null;
    }

    @RequestMapping(value = "changeLicenceAvailability", method = RequestMethod.POST)
    public ModelAndView changeLicenceAvailability(){
        ptFlowBean.getMainForm().setLicenceAvailability(!ptFlowBean.getMainForm().isLicenceAvailability());
        return null;
    }

    @RequestMapping(value = "changeAnticipationOfPublication", method = RequestMethod.POST)
    public ModelAndView changeAnticipationOfPublication(){
        ptFlowBean.getMainForm().setAnticipationOfPublication(!ptFlowBean.getMainForm().isAnticipationOfPublication());
        return null;
    }

    @RequestMapping(value = "changeExaminationRequested", method = RequestMethod.POST)
    public ModelAndView changeExaminationRequested(){
        ptFlowBean.getMainForm().setExaminationRequested(!ptFlowBean.getMainForm().isExaminationRequested());
        return null;
    }

}
