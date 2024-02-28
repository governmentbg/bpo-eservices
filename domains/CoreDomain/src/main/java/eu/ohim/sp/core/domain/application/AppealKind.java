package eu.ohim.sp.core.domain.application;

public enum AppealKind {
	APPEAL_AGAINST_REFUSAL("Against refusal of registration"), 
	APPEAL_AGAINST_TERMINATION("Against decision for termination of procedure"), 
	APPEAL_AGAINST_OPPOSITION_DECISION("Appeal against decision of opposition");
	
	private AppealKind(final String value)
    {
        this.value = value;

    }

    private final String value;

    
	public String value() {
		return value;
	}

}
