package eu.ohim.sp.eservices.ui.util;

public class Result {
	private String value;
	
	public Result(String value) { 
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}