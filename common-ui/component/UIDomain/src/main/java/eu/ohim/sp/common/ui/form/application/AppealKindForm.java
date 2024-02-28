package eu.ohim.sp.common.ui.form.application;

public enum AppealKindForm {
	APPEAL_AGAINST_REFUSAL("Against refusal of registration", "appeal.against.refusal.registration"), 
	APPEAL_AGAINST_TERMINATION("Against decision for termination of procedure", "appeal.against.termination.procedure"), 
	APPEAL_AGAINST_OPPOSITION_DECISION("Appeal against decision of opposition", "appeal.against.decision.opposition");
	
	private AppealKindForm(final String value, final String labelCode)
    {
        this.value = value;
        this.labelCode = labelCode;
    }

    private final String value;
    private final String labelCode;
    
	public String getValue() {
		return value;
	}
	public String getLabelCode() {
		return labelCode;
	}

    
}
