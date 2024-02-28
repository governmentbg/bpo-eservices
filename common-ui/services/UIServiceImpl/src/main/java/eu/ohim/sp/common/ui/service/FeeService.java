/*******************************************************************************
 * * $Id:: FeeService.java 53086 2012-12-14 09:01:44Z virgida                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import eu.ohim.sp.common.ui.adapter.FeesFactory;
import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.service.interfaces.FeeServiceInterface;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.fee.FeeCalculationService;

public abstract class FeeService<T extends FlowBean> implements FeeServiceInterface {

	@Autowired
	private FeeCalculationService feeManagementServiceInterface;

	@Autowired
	private FeesFactory feesFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.ohim.sp.common.ui.service.FeeServiceInterface#getFeesInformation
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void getFeesInformation(CommonSpFlowBean flowBean) {
		
		List<Fee> fees = feeManagementServiceInterface.calculateFees(getModule(), getFeesInputData((T) flowBean));
		
		FeesForm feeForm = feesFactory.convertTo(fees);
		
		flowBean.setFeesForm(feeForm);
	}
	
	protected abstract String getModule();
	protected abstract List<Object> getFeesInputData(T flowBean);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.ohim.sp.common.ui.service.FeeServiceInterface#updateFeesInformation
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateFeesInformation(CommonSpFlowBean flowBean){
		getFeesInformation(flowBean);
	}

	public FeesFactory getFeesFactory() {
		return feesFactory;
	}

	public void setFeesFactory(FeesFactory feesFactory) {
		this.feesFactory = feesFactory;
	}
	
	
}
