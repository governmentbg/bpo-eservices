/*******************************************************************************
 * * $Id:: MarkController.java 14333 2012-10-29 13:23:34Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NationalSearchController {
    @Autowired
    private TMFlowBean flowBean;

    /**
     * It returns a message to the user when changes the national search mark
     * 
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "changeNationalSearch", method = RequestMethod.GET)
    public ModelAndView changeCollective(@RequestParam("value") boolean value) {
        ModelAndView modelAndView = new ModelAndView("errors/alertErrors");

        modelAndView.addObject("alertMessage", "nationalSearchReport.note");

        flowBean.getMainForm().setNationalSearchReport(value);

        return modelAndView;
    }
}
