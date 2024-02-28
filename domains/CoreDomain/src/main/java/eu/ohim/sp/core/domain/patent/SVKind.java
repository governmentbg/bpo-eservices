package eu.ohim.sp.core.domain.patent;

public enum SVKind {
	SORT("svkind.sort", "Sort"),
	BREED("svkind.breed", "Breed");

	private SVKind(final String code, final String name)
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
