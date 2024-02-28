package eu.ohim.sp.core.domain.model;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

/**
 * The persistent class for the DVIENNA database table.
 * 
 */
public class Vienna extends Id implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	private String code;

	private boolean dominant;

	public Vienna() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDominant() {
		return dominant;
	}

	public void setDominant(boolean dominant) {
		this.dominant = dominant;
	}

}
