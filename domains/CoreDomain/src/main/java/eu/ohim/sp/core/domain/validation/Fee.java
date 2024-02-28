/*******************************************************************************
 * * $Id:: Fee.java 124871 2013-06-24 10:27:43Z karalch                          $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.validation;

import java.io.Serializable;

@Deprecated
public class Fee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2471580861620610488L;
	
	private int basicFee;
	private int collectiveFee;
	private int extraClassFeeBasic;
	private int extraClassFeeCollective;
	private int totalClasses;
	private int maxClasses;
	private int onlineAppDiscount;
	private int nationalSearchFee;		
	
	public Fee(){
		basicFee = 0;
		collectiveFee = 0;
		extraClassFeeBasic = 0;
		extraClassFeeCollective = 0;
		totalClasses = 0;
		maxClasses = 0;
		onlineAppDiscount = 0;
		nationalSearchFee = 0;
	}
	
	public int getOnlineAppDiscount() {
		return onlineAppDiscount;
	}
	public void setOnlineAppDiscount(int onlineAppDiscount) {
		this.onlineAppDiscount = onlineAppDiscount;
	}
	public int getMaxClasses() {
		return maxClasses;
	}
	public void setMaxClasses(int maxClasses) {
		this.maxClasses = maxClasses;
	}
	public int getBasicFee() {
		return basicFee;
	}
	public void setBasicFee(int basicFee) {
		this.basicFee = basicFee;
	}
	public int getCollectiveFee() {
		return collectiveFee;
	}
	public void setCollectiveFee(int collectiveFee) {
		this.collectiveFee = collectiveFee;
	}
	public int getExtraClassFeeBasic() {
		return extraClassFeeBasic;
	}
	public void setExtraClassFeeBasic(int extraClassFeeBasic) {
		this.extraClassFeeBasic = extraClassFeeBasic;
	}
	public int getExtraClassFeeCollective() {
		return extraClassFeeCollective;
	}
	public void setExtraClassFeeCollective(int extraClassFeeCollective) {
		this.extraClassFeeCollective = extraClassFeeCollective;
	}
	public int getTotalClasses() {
		return totalClasses;
	}
	public void setTotalClasses(int totalClasses) {
		this.totalClasses = totalClasses;
	}
	
	public int getNationalSearchFee() {
		return nationalSearchFee;
	}

	public void setNationalSearchFee(int nationalSearchFee) {
		this.nationalSearchFee = nationalSearchFee;
	}
}
