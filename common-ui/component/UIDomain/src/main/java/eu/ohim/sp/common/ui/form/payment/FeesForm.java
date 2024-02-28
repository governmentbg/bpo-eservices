/*******************************************************************************
 * * $Id:: FeesForm.java 53030 2012-12-13 15:15:13Z jaraful                      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.payment;

import java.util.ArrayList;
import java.util.List;


public class FeesForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List<FeeLineForm> feeLineFormList = new ArrayList<FeeLineForm>();
	
	public double getTotalAmount(){
		double amount = 0;
		for(FeeLineForm line: feeLineFormList){
			if (line.getAmount()!=null) {
				amount += line.getAmount();
			}
		}
		return amount;
	}	
	
	public List<FeeLineForm> getFeeLineFormList() {
		return feeLineFormList;
	}

	public void setFeeLineFormList(List<FeeLineForm> feeLineFormList) {
		this.feeLineFormList = feeLineFormList;
	}
	
}
