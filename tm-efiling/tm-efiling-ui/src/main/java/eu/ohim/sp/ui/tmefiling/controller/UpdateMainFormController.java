/*******************************************************************************
 * $Id:: UpdateMainFormController.java 2020/06/30 18:02 jmunoze
 *
 *        . * .
 *      * RRRR  *   Copyright (c) 2012-2020 EUIPO: European Intelectual
 *     .  RR  R  .  Property Organization (trademarks and designs).
 *     *  RRR    *
 *      . RR RR .   ALL RIGHTS RESERVED
 *       *. _ .*
 *
 *  The use and distribution of this software is under the restrictions exposed in 'license.txt'
 *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

@Controller
public class UpdateMainFormController {

	@Autowired
    private FlowBeanImpl flowBean;

	@RequestMapping(value = "updateMainForm", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateMainForm(HttpServletRequest request, FlowBeanImpl flowBeanImpl) {
		Enumeration<String> parameters = request.getParameterNames();
		PropertyUtilsBean utilBeans = new PropertyUtilsBean();
		while (parameters.hasMoreElements()) {
			String param = parameters.nextElement();
			try {
				utilBeans.setProperty(flowBean, param, utilBeans.getProperty(flowBeanImpl, param));
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// Do nothing
			}
		}
	}
}
