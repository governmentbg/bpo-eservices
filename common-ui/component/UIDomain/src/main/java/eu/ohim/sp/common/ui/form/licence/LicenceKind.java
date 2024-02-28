package eu.ohim.sp.common.ui.form.licence;

public enum LicenceKind {
	UNKNOWN("Unknown"),
	NONEXCLUSIVE("Nonexclusive"),
	EXCLUSIVE("Exclusive");
	
	private final String value;
	
	private LicenceKind(final String value){
		this.value = value;
	}
	
	public String value(){
		return value;
	}
	
	@Override
	public String toString(){
		return value;
	}
	
}
