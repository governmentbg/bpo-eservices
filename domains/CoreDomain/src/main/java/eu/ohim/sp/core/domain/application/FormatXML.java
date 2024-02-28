package eu.ohim.sp.core.domain.application;

public enum FormatXML {
	APPLICATION_XML("APPLICATION_XML"),
	APPLICATION_CONTAINER("APPLICATION_CONTAINER"),
	APPLICATION_MIMETYPE("APPLICATION_MIMETYPE"),
	APPLICATION_EPUB("APPLICATION_EPUB"),
	APPLICATION_OTHER("APPLICATION_OTHER");
	
	private final String value;
	
	FormatXML(String value) {
		this.value = value;
	}
	
	public String value()
    {
        return value;
    }
}
