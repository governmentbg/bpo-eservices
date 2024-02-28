package eu.ohim.sp.common.ui.form.resources;

public class FileContent {
	private byte[] content;
	
	private String name;
	
	private String contentType;

	public byte[] getContent() {
		return (content!=null?content.clone():null);
	}

	public void setContent(byte[] content) {
		this.content = (content!=null?content.clone():null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
