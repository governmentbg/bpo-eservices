/*
 *  CoreDomain:: LetterSearchCriteria 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.letters;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.dossier.DossierKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterStatusKind;

/**
 * Available search criteria for TMBO Letter searching
 * 
 * @author masjose
 * 
 */
public class LetterSearchCriteria implements Serializable {

	private static final long serialVersionUID = 7741788702899799256L;

	private int idDossier;
	private LetterStatusKind status;
	private DossierKind dossierType;
	private String typeLetter;
	private String recipient;
	private Date creationDate;

	public Date getCreationDate() {
		return DateUtil.cloneDate(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtil.cloneDate(creationDate);
	}

	public int getIdDossier() {
		return idDossier;
	}

	public void setIdDossier(int idDossier) {
		this.idDossier = idDossier;
	}

	public LetterStatusKind getStatus() {
		return status;
	}

	public void setStatus(LetterStatusKind status) {
		this.status = status;
	}

	public DossierKind getDossierType() {
		return dossierType;
	}

	public void setDossierType(DossierKind dossierType) {
		this.dossierType = dossierType;
	}

	public String getTypeLetter() {
		return typeLetter;
	}

	public void setTypeLetter(String typeLetter) {
		this.typeLetter = typeLetter;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}