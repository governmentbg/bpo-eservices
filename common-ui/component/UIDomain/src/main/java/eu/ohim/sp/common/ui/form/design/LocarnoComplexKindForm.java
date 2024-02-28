package eu.ohim.sp.common.ui.form.design;

public enum LocarnoComplexKindForm {
	
	SINGLE_PRODUCT("singleProduct"),
	SET_COMPOSITION("setComposition");
	
	private final String text;
	
	private LocarnoComplexKindForm(final String text) {
		this.text = text;
	}
	
	public String getValue() {
		return text;
	}
	 
	public static LocarnoComplexKindForm fromValue(String value) {
		for (LocarnoComplexKindForm type: LocarnoComplexKindForm.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
	    throw new IllegalArgumentException(value);
	}
	
}

