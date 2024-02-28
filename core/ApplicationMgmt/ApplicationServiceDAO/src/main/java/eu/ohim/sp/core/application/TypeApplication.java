package eu.ohim.sp.core.application;

import java.io.Serializable;

public class TypeApplication implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3836046955792832495L;
	private Long id;
	private String typeApplication;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeApplication() {
		return typeApplication;
	}
	public void setTypeApplication(String typeApplication) {
		this.typeApplication = typeApplication;
	}
}
