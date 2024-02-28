/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.service.constant.AppConstants;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MarkController {

	@Autowired
	private TMFlowBean flowBean;

	/**
	 * Changes the form of the type of mark
	 * 
	 * @param id
	 *            form that is going to be shown
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "changeMarkType", method = RequestMethod.GET)
	public ModelAndView changeMarkType(String id, String mode) {
		ModelAndView model = new ModelAndView("marks/"+mode+"/" + id);
		flowBean.getMainForm().clearInformation();
		flowBean.clearMarkViews();
		flowBean.getMainForm().setMarkType(id);
		return model;
	}
	
	@RequestMapping(value = "clearMarkType", method = RequestMethod.GET)
	public ModelAndView clearMarkType() {
		ModelAndView model = new ModelAndView("marks/empty_pattern");
		flowBean.getMainForm().clearInformation();
		flowBean.clearMarkViews();
		flowBean.getMainForm().setMarkType(AppConstants.MarkTypeSelect);
		return model;
	}
	
	/**
	 * It returns a message to the user when changes the collective mark
	 * 
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "changeCollective", method = RequestMethod.GET)
	public ModelAndView changeCollective() {
		ModelAndView modelAndView = new ModelAndView("errors/alertErrors");
		
		modelAndView.addObject("alertMessage", "mark.collective.tip");
		
		flowBean.getMainForm().setCollectiveMark(!flowBean.getMainForm().getCollectiveMark());
		flowBean.getMainForm().setCertificateMark(false);

		return modelAndView;
	}

	/**
	 * It returns a message to the user when changes the certificate mark
	 *
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "changeCertificate", method = RequestMethod.GET)
	public ModelAndView changeCertificate() {
		ModelAndView modelAndView = new ModelAndView("errors/alertErrors");

		modelAndView.addObject("alertMessage", "mark.certificate.tip");

		flowBean.getMainForm().setCertificateMark(!flowBean.getMainForm().getCertificateMark());
		flowBean.getMainForm().setCollectiveMark(false);

		return modelAndView;
	}
}
