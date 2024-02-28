package eu.ohim.sp.common.ui.form.resources;

public enum AttachmentDocumentType {
	
	POWER_OF_ATTORNEY("Power Of Attorney"),
	EXPLANATION_OF_GROUNDS("EXPLANATION_OF_GROUNDS"),
	PROPOSAL_AS_TO_DECIDE("PROPOSAL_AS_TO_DECIDE"),
	REPRESENTATION_OF_TRADEMARK("REPRESENTATION_OF_TRADEMARK"),
	OTHER_DOCUMENT("Other Document"),
	REPUTATION_CLAIM("REPUTATION_CLAIM"),
	GENERAL_POWER_OF_ATTORNEY("GENERAL_POWER_OF_ATTORNEY"),
	PRIORITY_CERTIFICATE("Priority Certificate"),
	PRIORITY_TRANSLATION("Priority Certificate Translation"),
	EXHIBITION_CERTIFICATE("Exhibition Priority Certificate"),
	EXHIBITION_TRANSLATION("Exhibition Priority Certificate Translation"),
	PCT_COPY("PCT Copy"),
	SMALL_COMPANY_DECLARATION("Small Company Declaration"),
	DECLARATION_FOR_UNIVERSITY("Declaration For University"),
	PARALLEL_APPLICATION_DECLARATION("Parallel Application Declaration"),
	REQUEST_FOR_EXAMINATION_DOCUMENT("Request For Examination Document"),
	PROOF_OF_PAYMENT("Proof of Payment"),
	EPO_BULLETIN_PUBLICATION_EXCERPT ("EPO Bulletin Publication Excerpt"),
	EPO_DECISION_COPY ("EPO Decision Copy"),
	EPO_DECISION_TRANSLATION ("EPO Decision Translation"),
	EPO_TRANSFER_CHANGE_FORM_COPY ("EPO Transfer Change Form Copy"),
	EPO_TRANSFER_CHANGE_FORM_TRANSLATION ("EPO Transfer Change Form Translation"),
	COMMERCIAL_REGISTER_EXCERPT("Commercial Register Excerpt"),
	COMMERCIAL_REGISTER_EXCERPT_TRANSLATION("Commercial Register Excerpt Translation"),
	CURRENT_STATUS_CERTIFICATE("Current Status Certificate"),
	TRANSFER_PROOF("Transfer Proof"),
	LICENCE_PROOF("Licence Proof"),
	LICENCE_CONTACT_ANNEX("Licence Contract Annex"),
	REM_PROOF("Rem Proof"),
	PREVIOUS_CREDITOR_CONSENT("Previous Creditor Consent"),
	PLEDGOR_CONSENT("Pledgor Consent"),
	DECLARATION_ZOZ("Declaration ZOZ"),
	DECLARATION_DOPK("Declaration DOPK"),
	CONFIRMATION_REGISTRATION_CROZ("Confirmation Registration CROZ"),
	SECURITY_PROOF("Security Proof"),
	BANKRUPTCY_PROOF("Bankruptcy Proof"),
	REQUEST("Request"),
	OBJECTION("Objection"),
	OFFICIAL_POSITION("Official Position"),
	RESPONSE("Response"),
	CORRECTION_LETTER("Correction Letter"),
	SURRENDER_DECLARATION("Surrender Declaration"),
	REQUEST_CHANGE_LEGAL_STATUS("Request Change Legal Status"),
	EVIDENCE("Evidence"),
	MARK_REGISTRATION_CERTIFICATE("Mark Registration Certificate"),
	INQUIRY("Inquiry"),
	TRANSLATION_CORRECTION("Translation Correction"),
	ACTION_CORRECTION("Action Correction"),
	EUIPO_COMMUNICATION_COPY("EUIPO Communication Copy"),
	PERMIT_MARKET_BG("Permit Market BG"),
	PERMIT_MARKET_EU("Permit Market EU"),
	AUTHORIZATION_MARKET_EU("Authorization Market EU"),
	BRIEF_PRODUCT_DESCRIPTION("Brief Product Description"),
	GI_REGISTRATION_DOCUMENT_TRANSLATION("GI Registration Document Translation"),
	GI_REGISTRATION_DOCUMENT("GI Registration Document"),
	GI_PRODUCTION_CERTIFICATE("GI Production Certificate"),
	GI_TRANSCRIPT_OF_ORDER("GI Transcript Of Order"),
	MAP("Map"),
	SKETCH("Sketch"),
	DESCRIPTION("Description"),
	CLAIM("Claim"),
	DRAWING("Drawing"),
	STANDPOINT_COPY("Standpoint Copy"),
	PROTECTION_CERTIFICATE_COPY("Protection Certificate Copy"),
	FIRST_USAGE_DECLARATION("First Usage Declaration"),
	TRADING_SECRET_GUIDES("Trading Secret Guides"),
	ENTITLEMENT_TO_APPLY_DECLARATION("Entitlement To Apply Declaration"),
	INTEGRAL_SCHEMA_SAMPLE("Integral Schema Sample"),
	LICENCE_AVAILABILITY_DECLARATION("Licence Availability Declaration"),
	PRIORITY_DECLARATION("Priority Declaration"),
	PEDIATRIC_COMPLIANCE("Pediatric Compliance"),
	AMENDING_ANNEX_1("Amending Annex 1"),
	APPROVED_PEDIATRIC_PLAN("Approved Pediatric Plan"),
	OFFICIAL_EXHIBITION_EVIDENCE("Official Exhibition Evidence"),
	NON_USE_EVIDENCE("Non Use Evidence"),
	LICENCE_USABILITY_EVIDENCE("Licence Usability Evidence")
	;


	private final String value;
	
	AttachmentDocumentType(String value) {
		this.value = value;
	}
	
	public String value() {
        return value;
    }
	
	public static AttachmentDocumentType fromValue(String value) {
		for (AttachmentDocumentType type: AttachmentDocumentType.values()) {
			if (type.value().equals(value)) {
				return type;
			}
		}
	    return null;
	}
	
}
