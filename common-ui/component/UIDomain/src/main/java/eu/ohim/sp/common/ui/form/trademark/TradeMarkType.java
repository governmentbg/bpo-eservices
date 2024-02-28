package eu.ohim.sp.common.ui.form.trademark;

public enum TradeMarkType {
	WORD("Word"),
	STYLIZED_CHARACTERS("Stylized characters"),
	FIGURATIVE("Figurative"),
	COMBINED("Combined"),
	THREE_D("3-D"),
	COLOUR("Colour"),
	SOUND("Sound"),
	HOLOGRAM("Hologram"),
	OLFACTORY("Olfactory"),
	MOTION("Motion"),
	MUNICIPAL("Municipal"),
	CHIMNEY("Chimney"),
	KENNFADEN("Kennfaden"),
	NUMBER("Number"),
	MARK_IN_SERIES("Mark in series"),
	OTHER("Other"),
	PATTERN("Pattern"),
	POSITION("Position"),
	MULTIMEDIA("Multimedia"),
	UNDEFINED("Undefined");

	private final String value;

	private TradeMarkType (final String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
