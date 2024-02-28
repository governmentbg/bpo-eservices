package eu.ohim.sp.core.domain.design;

import java.io.Serializable;

import eu.ohim.sp.core.domain.resources.AttachedDocument;

public class DesignView implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -543475702889753147L;

	private AttachedDocument view;
	private AttachedDocument viewThumbnail;
	private int sequence;
	private String description;
	private boolean publishInColour;
	private boolean publishInBlackWhite;
	private String publicationSize;
	private String type;
	
	public AttachedDocument getView() {
		return view;
	}
	public void setView(AttachedDocument view) {
		this.view = view;
	}
	public AttachedDocument getViewThumbnail() {
		return viewThumbnail;
	}
	public void setViewThumbnail(AttachedDocument viewThumbnail) {
		this.viewThumbnail = viewThumbnail;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPublishInColour() {
		return publishInColour;
	}
	public void setPublishInColour(boolean publishInColour) {
		this.publishInColour = publishInColour;
	}
	public boolean isPublishInBlackWhite() {
		return publishInBlackWhite;
	}
	public void setPublishInBlackWhite(boolean publishInBlackWhite) {
		this.publishInBlackWhite = publishInBlackWhite;
	}
	public String getPublicationSize() {
		return publicationSize;
	}
	public void setPublicationSize(String publicationSize) {
		this.publicationSize = publicationSize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
