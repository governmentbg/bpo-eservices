/*******************************************************************************
 * * $Id:: FeesFactory.java 53086 2012-12-14 09:01:44Z virgida                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.payment.FeeLineForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeesFactory implements UIFactory<FeesForm, List<Fee>> {

	@Override
	public FeesForm convertTo(List<Fee> fees) {
		FeesForm feeForm = new FeesForm();
		if (fees == null)
			return feeForm;
		
		for(Fee fee : fees){
			FeeLineForm line = new FeeLineForm();
            line.setCode(fee.getFeeType().getCode());
			line.setNameKey(fee.getFeeType().getNameKey());
			line.setAmount(fee.getAmount());
			line.setStatus(fee.getStatus());
			line.setExpirationExtentNewDate(fee.getExpirationExtentNewDate());
			line.setExpirationExtentYearsFromEntitlement(fee.getExpirationExtentYearsFromEntitlement());

			feeForm.getFeeLineFormList().add(line);
		}

		return feeForm;
	}

	@Override
	public List<Fee> convertFrom(FeesForm feesForm) {
		List<Fee> feeList = new ArrayList<Fee>();
		if (feesForm == null)
			return feeList;
		
		for(FeeLineForm line: feesForm.getFeeLineFormList()){
			Fee fee = new Fee();
			FeeType feeType = new FeeType();
			feeType.setNameKey(line.getNameKey());
            feeType.setCode(line.getCode());
			fee.setFeeType(feeType);
			fee.setAmount(line.getAmount());
			fee.setStatus(line.getStatus());
			fee.setExpirationExtentNewDate(line.getExpirationExtentNewDate());
			fee.setExpirationExtentYearsFromEntitlement(line.getExpirationExtentYearsFromEntitlement());
			feeList.add(fee);
		}

		return feeList;
	}

}
