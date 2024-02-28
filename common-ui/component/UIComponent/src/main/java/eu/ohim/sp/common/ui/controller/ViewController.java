/*******************************************************************************
 * * $Id:: ViewController.java 49264 2012-10-29 13:23:34Z karalch                $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.ViewParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;

/**
 * Controller that manages the view of priority/seniority/applicant/representative ...
 *
 */
@Controller
public class ViewController extends ViewAbstractController {

	/**
	 * Logger for this class and subclasses
	 */
	private static final Logger logger = Logger.getLogger(ViewController.class);

	@Autowired
	private FlowBean flowBean;

	/**
	 * Custom Date Editor
	 */
	@Autowired
	private CustomDateEditor customDateEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, customDateEditor);
	}

	/* --------- PRIORITIES ---------- */
	/* ------------------------------- */

	/**
	 * It returns the selected Priority object by id
	 * 
	 * @param id
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "viewPriority", method = RequestMethod.GET)
	public ModelAndView handleViewPriority(@RequestParam(value = "id") String id) {

		return handle(id, flowBean, new ViewParameters(PriorityForm.class, "priorityForm", "claim/claim_priority_card",
				"claim/claim_priority_card"));
	}

	/* --------- SENIORITIES---------- */
	/* ------------------------------- */

	/**
	 * It returns the selected Seniority object by id
	 * 
	 * @param id
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "viewSeniority", method = RequestMethod.GET)
	public ModelAndView handleViewSeniority(@RequestParam(value = "id") String id) {

		return handle(id, flowBean, new ViewParameters(SeniorityForm.class, "seniorityForm",
				"claim/claim_seniority_card", "claim/claim_seniority_card"));
	}

	/* --- EXHIBITION PRIORITIES ----- */
	/* ------------------------------- */

	/**
	 * It returns the selected Exhibition Priority object by id
	 * 
	 * @param id
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "viewExhPriority", method = RequestMethod.GET)
	public ModelAndView handleViewExhPriority(@RequestParam(value = "id") String id) {

		return handle(id, flowBean, new ViewParameters(ExhPriorityForm.class, "exhPriorityForm",
				"claim/claim_exhibition_card", "claim/claim_exhibition_card"));
	}

	/* ------- IR TRANSFORMATIONS ---- */
	/* ------------------------------- */

	/**
	 * It returns the selected IR Transformation object by id
	 * 
	 * @param id
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "viewTransformation", method = RequestMethod.GET)
	public ModelAndView handleViewTransformation(@RequestParam(value = "id") String id) {

		return handle(id, flowBean, new ViewParameters(TransformationForm.class, "transformationForm",
				"claim/claim_transformation_card", "claim/claim_transformation_card"));
	}

	/**
	 * Views the of the applicant cards
	 * @return the model and view of applicant cards
	 */
	@RequestMapping(value = "viewApplicantCards", method = RequestMethod.GET)
	public ModelAndView viewApplicantCards() {
		return new ModelAndView("applicant/yourdata_applicant", "flowBean", flowBean);
	}

	/**
	 * Views the of the representative cards
	 * @return the model and view of representative cards
	 */
	@RequestMapping(value = "viewRepresentativeCards", method = RequestMethod.GET)
	public ModelAndView viewRepresentativeCards() {
		return new ModelAndView("representative/yourdata_representative", "flowBean", flowBean);
	}

	/**
	 * View of the applicant details
	 * @param id the id of the applicant
	 * @return the model and view of the requested applicant
	 */
	@RequestMapping(value = "viewApplicantDetails", method = RequestMethod.GET)
	public ModelAndView viewApplicantDetails(@RequestParam(value = "id") String id) {
		return handle(id, flowBean, new ViewParameters(ApplicantForm.class, "applicantForm",
				"applicant/applicant_moredetails", "applicant/applicant_failure"));
	}

	/**
	 * View of the representative details
	 * @param id the id of the representative
	 * @return the model and view of the requested representative
	 */
	@RequestMapping(value = "viewRepresentativeDetails", method = RequestMethod.GET)
	public ModelAndView viewRepresentativeDetails(@RequestParam(value = "id") String id) {
		return handle(id, flowBean, new ViewParameters(RepresentativeForm.class, "representativeForm",
				"representative/representative_moredetails", "representative/representative_failure"));
	}
}
