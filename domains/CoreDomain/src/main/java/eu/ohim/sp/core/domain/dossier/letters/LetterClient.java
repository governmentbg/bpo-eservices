/*
 *  CoreDomain:: LetterClient 04/10/13 12:20 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.letters;

import java.io.Serializable;

import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.domain.resources.Document;

/**
 * This class represents a physical letter after generation using a template
 * 
 * @author masjose
 * 
 */
public class LetterClient implements Serializable {

	private static final long serialVersionUID = 7741788702899799256L;

	// TODO Update the configuration xsd to support StandardText files
	// private List<StandardText> standardTexts;

	private String bodyLetter;
	private String sendStatus;
	private byte[] contentFile;
	private Document document;
	private LetterTypeSendMethod sendMethod;

	public String getBodyLetter() {
		return bodyLetter;
	}

	public void setBodyLetter(String bodyLetter) {
		this.bodyLetter = bodyLetter;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public byte[] getContentFile() {
		return (contentFile!=null?contentFile.clone():null);
	}

	public void setContentFile(byte[] contentFile) {
		this.contentFile = (contentFile!=null?contentFile.clone():null);
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public LetterTypeSendMethod getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(LetterTypeSendMethod sendMethod) {
		this.sendMethod = sendMethod;
	}

}