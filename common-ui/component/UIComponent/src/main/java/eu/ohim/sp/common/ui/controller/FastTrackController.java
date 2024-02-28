/*******************************************************************************
 * $Id:: FastTrackController.java 2020/06/30 18:02 jmunoze
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
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.flow.section.FastTrackBean;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.FastTrackServiceInterface;
import eu.ohim.sp.core.domain.fasttrack.FastTrackFail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@Controller
public class FastTrackController {

	@Autowired
	private FlowScopeDetails flowScopeDetails;

	@Autowired(required = false)
    private FastTrackBean fastTrackBean;

	@Autowired(required = false)
	private FastTrackServiceInterface fastTrackService;

	@RequestMapping(value = "getFastTrackFails")
	@ResponseBody
	public Collection<FastTrackFail> getFastTrackFails() {
		if(fastTrackBean != null) {
			Collection<FastTrackFail> fastTrackFails = fastTrackService.calculateFastTrackFails(fastTrackBean, flowScopeDetails.getFlowModeId(), flowScopeDetails.getStateId());
			fastTrackBean.setFastTrack(fastTrackFails == null || fastTrackFails.isEmpty());
			return fastTrackFails;
		}
		return null;
	}

	@RequestMapping(value = "setFastTrackStart")
	@ResponseStatus(HttpStatus.OK)
	public void setFastTrackStart() {
		if(fastTrackBean != null) {
			fastTrackBean.setFastTrackStarted(true);
		}
	}
}
