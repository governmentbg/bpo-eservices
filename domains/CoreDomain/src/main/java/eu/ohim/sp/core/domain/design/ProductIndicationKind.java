package eu.ohim.sp.core.domain.design;

public enum ProductIndicationKind {
	
	SINGLE_PRODUCT("singleProduct"),
	SET_COMPOSITION("setComposition");
	
	private final String value;
	
	private ProductIndicationKind(final String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	 
}
