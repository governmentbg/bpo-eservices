package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya
 * 22.07.2019
 */
@Controller
public class PatentController {

    @Autowired
    private PTFlowBean ptFlowBean;

    @RequestMapping(value = "changeClaimsCount", method = RequestMethod.POST)
    public ModelAndView changeClaimsCount(@RequestParam(name = "count")String count ){
        ptFlowBean.getPatent().setClaimsCount(count);
        return null;
    }

    @RequestMapping(value = "changeIndependentClaimsCount", method = RequestMethod.POST)
    public ModelAndView changeIndependentClaimsCount(@RequestParam(name = "count")String count){
        ptFlowBean.getPatent().setIndependentClaimsCount(count);
        return null;
    }

    @RequestMapping(value = "changePagesCount", method = RequestMethod.POST)
    public ModelAndView changePagesCount(@RequestParam(name = "count")String count){
        ptFlowBean.getPatent().setPagesCount(count);
        return null;
    }
}
