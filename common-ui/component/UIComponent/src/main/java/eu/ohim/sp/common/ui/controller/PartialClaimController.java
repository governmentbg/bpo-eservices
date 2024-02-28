/*******************************************************************************
 * * $Id:: PartialClaimController.java 56708 2013-02-12 16:28:11Z ionitdi        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;

/**
 * 
 * @author soriama
 * 
 * This controller manages the ajax calls related to the Partial Priority Claim
 */
@Controller
public class PartialClaimController {

	private static final Logger logger = Logger.getLogger(PartialClaimController.class);
	
	/**
	 * Custom Date Editor
	 */
	@Autowired
	private CustomDateEditor customDateEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, customDateEditor);
	}
	
	/**
	 * Adds a class into the partial priority section for the specific priority
	 * If the class is already in the list, it is replaced with an empty description
	 * 
	 * @param command
	 *            object that contains the Priority information.
	 * @param classId
	 * 			  classId to be either added or updated if it exists
	 * @param langId
	 * 			  id of the language of the description	
	 * 
	 * @return Priority object view with the new G&S added
	 */
	@RequestMapping(value = "addPartialGSClass", method = RequestMethod.POST)
	public ModelAndView addPartialGSClass(@ModelAttribute("priorityForm") PriorityForm command,
			@RequestParam(required = true, value = "classId") String classId,
			@RequestParam(required = true, value = "langId") String langId,
			@RequestParam(required = true, value = "desc") String desc) {
		
		ModelAndView modelAndView = new ModelAndView("claim/claim_priority_goodsservices");
		
		Iterator<GoodAndServiceForm> it = command.getGoodsServices().iterator();
		while(it.hasNext()) {
			GoodAndServiceForm gs = it.next();
			if(gs.getClassId() != null && gs.getClassId().equals(classId)) {
				gs.setDesc(desc);
				return modelAndView;
			}
		}
		
		//If it arrives here, the class doesn't exist in the list, so let's add it
		GoodAndServiceForm gs = new GoodAndServiceForm(langId, classId);
		gs.setDesc(desc);
		command.getGoodsServices().add(gs);
		return modelAndView;
	}
	
	/**
	 * Removes a class from the partial priority section for the specific priority	 
	 * 
	 * @param command
	 *            object that contains the Priority information.
	 * @param classId
	 * 			  classId to be removed	 
	 * 
	 * @return Priority object view with the remaining goods and services
	 */
	@RequestMapping(value = "removePartialGSClass", method = RequestMethod.POST)
	public ModelAndView removePartialGSClass(@ModelAttribute("priorityForm") PriorityForm command,
			@RequestParam(required = true, value = "classId") String classId) {
		
		ModelAndView modelAndView = new ModelAndView("claim/claim_priority_goodsservices");
		GoodAndServiceForm remove = null;
		
		Iterator<GoodAndServiceForm> it = command.getGoodsServices().iterator();
		while(it.hasNext()) {
			GoodAndServiceForm gs = it.next();
			if(gs.getClassId().equals(classId)) {
				remove = gs;
				break;
			}
		}
		
		//If remove is not null, remove it from the list
		if(remove != null) {
			command.getGoodsServices().remove(remove);
		}
		
		return modelAndView;
	}
	
	/**
	 * Removes all the classes of the partial priority claim
	 * 
	 * @param command
	 *            object that contains the Priority information.	
	 * @return Priority object view with the new G&S added
	 */
	@RequestMapping(value = "removeAllPartialGSClass", method = RequestMethod.POST)
	public ModelAndView onSubmitPriority(@ModelAttribute("priorityForm") PriorityForm command) {
		
		ModelAndView modelAndView = new ModelAndView("claim/claim_priority_goodsservices");
		
		command.setGoodsServices(LazyList.decorate(new ArrayList<GoodAndServiceForm>(), FactoryUtils.instantiateFactory(GoodAndServiceForm.class)));
		return modelAndView;
	}
}
