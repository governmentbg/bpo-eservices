/*
 *  CoreDomain:: TMeServiceApplication 03/09/13 09:33 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

/**
 * 
 */
package eu.ohim.sp.core.domain.design;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import eu.ohim.sp.common.util.CloneUtil;

import eu.ohim.sp.core.domain.application.EServiceApplication;

/**
 * The Class DSeServiceApplication.
 */
public class DSeServiceApplication extends EServiceApplication implements Serializable {

	private static final long serialVersionUID = -5516719536585670176L;
	
	private List<DesignApplication> designDetails;
	
	public List<DesignApplication> getDesignDetails() {
		return designDetails;
	}
	
	public void setDesignDetails(List<DesignApplication> designDetails) {
		this.designDetails = designDetails;
	}

	public void consolidateDesigns() {
		if(designDetails != null) {
			ArrayList<Design> designs = new ArrayList<>();
			designDetails.stream()
					.forEach(da -> {
						designs.add(da.getDesignDetails().get(0));
					});
			DesignApplication da = designDetails.get(0);
			da.setDesignDetails(designs);
			designDetails = new ArrayList<>();
			designDetails.add(da);
		}
	}

	public void divideDesigns() {
		if(designDetails != null) {
			ArrayList<DesignApplication> dalist = new ArrayList<>();
			DesignApplication da = designDetails.get(0);
			List<Design> designs = (List<Design>) CloneUtil.deepClone(da.getDesignDetails());
			da.setDesignDetails(new ArrayList<>());
			designs.stream()
					.forEach(d -> {
						ArrayList<Design> ds = new ArrayList<>();
						ds.add(d);
						DesignApplication daux = (DesignApplication) CloneUtil.deepClone(da);
						daux.setDesignDetails(ds);
						dalist.add(daux);
					});
			designDetails = dalist;
		}
	}

	public List<DesignApplication> getSelectedDesigns(){
		if(designDetails == null){
			return new ArrayList<>();
		}
		return designDetails
				.stream()
				.filter(ds -> ds.getDesignDetails() != null && ds.getDesignDetails().size() > 0 &&  ds.getDesignDetails().get(0) != null)
				.filter(ds -> ds.getDesignDetails().get(0).getSelected() != null && ds.getDesignDetails().get(0).getSelected()).collect(Collectors.toList());

	}
}
