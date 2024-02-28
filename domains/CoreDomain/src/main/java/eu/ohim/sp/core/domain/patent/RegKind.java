package eu.ohim.sp.core.domain.patent;

public enum RegKind {
	MEDICINE("regkind.medicine", "Medicine"),
	PLANT("regkind.plant.protection", "Plant");

	private RegKind(final String code, final String name)
	{
		this.code = code;
		this.name = name;
	}

	private final String code;
	private final String name;

	public String getCode()
	{
		return code;
	}
	public String getName()
	{
		return name;
	}
}
