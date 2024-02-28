/*******************************************************************************
 * * $Id:: UserPersonDetails.java 146843 2013-10-15 16:54:40Z serrajo            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;

public class UserPersonDetails extends Id implements Serializable {

	private static final long serialVersionUID = 7286757448927969392L;
	
	private List<Applicant> applicants;
	private List<Representative> representatives;
	private List<Designer> designers;
	
	public List<Applicant> getApplicants() {
		if (applicants==null) {
			applicants = new ArrayList<Applicant>();
		}
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}
	public List<Representative> getRepresentatives() {
		if (representatives==null) {
			representatives = new ArrayList<Representative>();
		}
		return representatives;
	}
	public void setRepresentatives(List<Representative> representatives) {
		this.representatives = representatives;
	}

	public List<Designer> getDesigners() {
		if (designers==null) {
			designers = new ArrayList<Designer>();
		}
		return designers;
	}

	public void setDesigners(List<Designer> designers) {
		this.designers = designers;
	}
}
